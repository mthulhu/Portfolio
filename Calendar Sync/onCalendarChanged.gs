const org_FTO_CALENDAR_ID = ''
const FTO_REGEX = new RegExp('(^|[^a-zA-Z])[fF][tT][oO]')


/**
 * Propegates events containing 'FTO' (not case sensitive) in the summary onto the FTO calendar.
 * This function is called through the calendar changed trigger, which watches a personal calendar for event changes.
 * Note that throughout this code, 'event' refers to a calendar event. While 'e' is the change event that triggered this code.
 * 'ftoCalendareEvent' is used to distinguish an event on the FTO calendar.
 * Just 'event' usually refers to an event on a personal calendar.
 * @param {e} calendar changed event object sent by the registered trigger.
 */
function onCalendarChanged(e) {
  const calendarId = e.calendarId // e.g. 'matt.krueger@org.com'
  Logger.log(`Running sync on ${calendarId}`)

  // check for sync token
  let syncToken = ScriptProperties.getProperty(`CalendarSyncToken-${calendarId}`)

  // process changed events on calendar
  let events = getEventsChanged(calendarId, syncToken)
  for (const event of events) {
    if (event.summary && FTO_REGEX.test(event.summary)) {
      if (event.status !== 'cancelled') {
        // FTO event found, make sure its persisted to FTO calendar
        handleFtoEvent(calendarId, event)
      } else {
        // FTO event deleted, make sure its removed from FTO calendar
        handleFtoEventDeleted(event)
      }
    }
  }
}

/**
 * Returns the events on the calendar. 
 * Uses a sync token to try and filter the events to only those changed since last execution.
 * Uses a page token to iterate through pages of events. 
 * Will only look back 10 days when scanning the calendar.
 * @param {calendarId} the personal calendar the event was triggered for
 * @param {syncToken} the sync token used to limit the event scan to only events modified since the execution
 * @return {eventsResponse} Google Calendar Events API response
 */
function getEventsChanged(calendarId, syncToken) {
  let events = []
  let pageToken
  let nextSyncToken
  do {
    try {
      const apiParams = {
        pageToken: pageToken,
        showDeleted: true
      }
      if (syncToken) {
        apiParams.syncToken = syncToken
      } else {
        apiParams.timeMin = getRelativeDate(-10, 0).toISOString()
      }
      const eventsResponse = Calendar.Events.list(calendarId, apiParams)
      events = events.concat(eventsResponse.items)
      nextSyncToken = eventsResponse.nextSyncToken
      pageToken = eventsResponse.nextPageToken
    } catch (err) {
      // Check to see if the sync token was invalidated by the server
      // if so, perform a full sync instead.
      if (err.message && err.message.includes('Sync token is no longer valid, a full sync is required.')) {
        Logger.log('Calendar sync token was invalidated')
        ScriptProperties.deleteProperty(`CalendarSyncToken-${calendarId}`)
        return getEventsChanged(calendarId, undefined)
      }
      throw new Error(err.message)
    }
  } while (pageToken)
  // save sync token to save time next execution
  ScriptProperties.setProperty(`CalendarSyncToken-${calendarId}`, nextSyncToken)
  return events
}

/**
 * Handler function for an active FTO event found on the watched personal calendar.
 * Does nothing if event is already propegated to the FTO calendar.
 * Otherwise, propegate events and event moves to FTO calendar
 * @param {calendarId} the personal calendar the event was triggered for
 * @param {event} fto event found on personal calendar
 */
function handleFtoEvent(calendarId, event) {

  // No matching FTO calendar event, save new event in the FTO calendar
  let ftoCalendarEvent = buildFtoCalendarEvent(event)
  try {
    ftoCalendarEvent = Calendar.Events.insert(ftoCalendarEvent, org_FTO_CALENDAR_ID)
    Logger.log(`Propegated FTO event creation`)
    // Check if there is a duplicate event created (workaround for when the trigger activates twice at the same time for a single event creation)
    dedupFtoEvents(calendarId, event)
  } catch (err) {
    Logger.log(err)
  }
}


/**
 * Removes any duplicate FTO calendar events that exist for a given event.
 * Duplication of FTO events can occur when Google triggers the script twice at the same time for a new calendar event creation.
 * @param {calendarId} calendar id of the event
 * @param {event} event to deduplicate fto events for
 */
function dedupFtoEvents(calendarId, event) {
  const matchingFtoEvents = findMatchingFtoEvents(event)
  while (matchingFtoEvents.length > 1) {
    duplicateFtoEvent = matchingFtoEvents[1]
    Calendar.Events.remove(org_FTO_CALENDAR_ID, duplicateFtoEvent.id)
    matchingFtoEvents.splice(1, 1) // removes 1 item at index 1 from array
    Logger.log('Deduplicated FTO event')
  }
}



/**
 * Helper function to create a new event for the FTO calendar provided an existing event from the personal calendar.
 * Saves the correlated personal calendar event ID in the FTO calendar event metadata.
 * @param {event} Event from the personal calendar.
 * @return {event} New Event to be inserted into the FTO calendar
 */
function buildFtoCalendarEvent(event) {
  const ftoEvent =  {
    summary: event.summary,
    description: event.description,
    start: event.start,
    end: event.end,
    recurrence: event.recurrence,
  }
  ftoEvent.extendedProperties = ftoEvent.extendedProperties? ftoEvent.extendedProperties : {}
  ftoEvent.extendedProperties.private = ftoEvent.extendedProperties.private? ftoEvent.extendedProperties.private : {}
  ftoEvent.extendedProperties.private.personalCalendarEventId = event.id
  return ftoEvent
}

/**
 * Helper function to get a new Date object relative to a given date. 
 * If fromDate is not provided, use current date.
 * @param {number} daysOffset The number of days in the future for the new date.
 * @param {number} hour The hour of the day for the new date, in the time zone
 *     of the script.
 * @param {Date} fromDate The given date to offset from.
 * @return {Date} The new date.
 */
function getRelativeDate(daysOffset, hour, fromDate) {
  const date = fromDate ? fromDate : new Date()
  date.setDate(date.getDate() + daysOffset)
  date.setHours(hour)
  date.setMinutes(0)
  date.setSeconds(0)
  date.setMilliseconds(0)
  return date
}