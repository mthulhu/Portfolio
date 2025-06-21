import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class ContactService {
  private HashMap<String, Contact> contacts = new HashMap<String, Contact>();

  /**
   * Sorts the given list of contacts by any property using the Quick Sort algorithm.
   *
   * @param contacts the list of contacts to sort
   * @param low the starting index
   * @param high the ending index
   * @param getCompareValueFunc the getter for the property used in comparison
   * @return the sorted list of contacts
   */
  public ArrayList<Contact> quickSort(ArrayList<Contact> contacts, int low, int high, Function<Contact, String> getCompareValueFunc) {
    // Only sort if the sublist has more than one element
    if (low < high) {
      // Partition the list and get the pivot index
      int pi = partition(contacts, low, high, getCompareValueFunc);
      // Recursively sort elements before and after partition
      quickSort(contacts, low, pi - 1, getCompareValueFunc);
      quickSort(contacts, pi + 1, high, getCompareValueFunc);
    }
    return contacts;
  }

  /**
   * Partitions the list of contacts in half around a pivot 
   *
   * @param contacts the list of contacts to partition
   * @param low the starting index
   * @param high the ending index (pivot)
   * @param getCompareValueFunc the getter for the property used in comparison
   * @return the index of the pivot after partitioning
   */
  private int partition(ArrayList<Contact> contacts, int low, int high, Function<Contact, String> getCompareValueFunc) {
    // Choose the last element as the pivot
    Contact pivot = contacts.get(high);
    String pivotValue = getCompareValueFunc.apply(pivot).toLowerCase();
    int i = (low - 1); // Index of smaller element
    for (int j = low; j < high; j++) {
      String currentValue = getCompareValueFunc.apply(contacts.get(j)).toLowerCase();
      // If current contact's property value is less than the pivot's property value
      if (currentValue.compareTo(pivotValue) < 0) {
        i++;
        // Swap contacts at i and j
        Contact temp = contacts.get(i);
        contacts.set(i, contacts.get(j));
        contacts.set(j, temp);
      }
    }
    // Swap the pivot contact to its correct position
    Contact temp = contacts.get(i + 1);
    contacts.set(i + 1, contacts.get(high));
    contacts.set(high, temp);
    return i + 1; // Return the pivot index
  }

  public void addContact(Contact contact) {
    if (contacts.containsKey(contact.getId())) {
      throw new IllegalArgumentException("ID must be unique");
    }
    contacts.put(contact.getId(), contact);
  }

  public void deleteContact(String id) {
    contacts.remove(id);
  }

  public Contact getContact(String id) {
    return contacts.get(id);
  }

  public void updateContact(String id, String firstName, String lastName, String phone, String address) {
    Contact contact = contacts.get(id);
    if (contact == null) {
      throw new IllegalArgumentException("Contact not found");
    }
    contact.setFirstName(firstName);
    contact.setLastName(lastName);
    contact.setPhone(phone);
    contact.setAddress(address);
  }

  /**
   * Searches for contacts by first name.
   * If the query ends with '*', performs a prefix search.
   * Otherwise, searches for an exact match using binary search.
   *
   * @param query the first name or prefix to search for
   * @return matching contacts
   * @throws IllegalArgumentException if the query is null or empty
   */
  public ArrayList<Contact> searchContactsByFirstName(String query) {
    if (query == null || query.isEmpty()) {
      throw new IllegalArgumentException("Query cannot be null or empty");
    }
    // Search by prefix if query ends in *
    if (query.endsWith("*")) {
      String prefix = query.substring(0, query.length() - 1).toLowerCase();
      return searchContactsByPrefix(prefix, Contact::getFirstName);
    } else {
      // Search for exact match using binary search
      return searchContactsByExactMatch(query, Contact::getFirstName);
    }
  }

  /**
   * Searches for contacts by last name.
   * If the query ends with '*', performs a prefix search.
   * Otherwise, searches for an exact match using binary search.
   *
   * @param query the last name or prefix to search for
   * @return matching contacts
   * @throws IllegalArgumentException if the query is null or empty
   */
  public ArrayList<Contact> searchContactsByLastName(String query) {
    if (query == null || query.isEmpty()) {
      throw new IllegalArgumentException("Query cannot be null or empty");
    }
    if (query.endsWith("*")) {
      String prefix = query.substring(0, query.length() - 1).toLowerCase();
      return searchContactsByPrefix(prefix, Contact::getLastName);
    } else {
      return searchContactsByExactMatch(query, Contact::getLastName);
    }
  }

  /**
   * Searches for contacts by phone number.
   * If the query ends with '*', performs a prefix search.
   * Otherwise, searches for an exact match using binary search.
   *
   * @param query the phone number or prefix to search for
   * @return matching contacts
   * @throws IllegalArgumentException if the query is null or empty
   */
  public ArrayList<Contact> searchContactsByPhone(String query) {
    if (query == null || query.isEmpty()) {
      throw new IllegalArgumentException("Query cannot be null or empty");
    }
    if (query.endsWith("*")) {
      String prefix = query.substring(0, query.length() - 1).toLowerCase();
      return searchContactsByPrefix(prefix, Contact::getPhone);
    } else {
      return searchContactsByExactMatch(query, Contact::getPhone);
    }
  }

  /**
   * Searches for contacts by address.
   * If the query ends with '*', performs a prefix search.
   * Otherwise, searches for an exact match using binary search.
   *
   * @param query the address or prefix to search for
   * @return matching contacts
   * @throws IllegalArgumentException if the query is null or empty
   */
  public ArrayList<Contact> searchContactsByAddress(String query) {
    if (query == null || query.isEmpty()) {
      throw new IllegalArgumentException("Query cannot be null or empty");
    }
    if (query.endsWith("*")) {
      String prefix = query.substring(0, query.length() - 1).toLowerCase();
      return searchContactsByPrefix(prefix, Contact::getAddress);
    } else {
      return searchContactsByExactMatch(query, Contact::getAddress);
    }
  }

  /**
   * Searches for contacts by a prefix on any property using binary search.
   * The getCompareValueFunc function extracts the property to search on.
   * Returns a list of contacts whose property starts with the given prefix.
   *
   * @param prefix the prefix to search for
   * @param getCompareValueFunc the getter for the property used in comparison
   * @return matching contacts
   */
  public ArrayList<Contact> searchContactsByPrefix(String prefix, Function<Contact, String> getCompareValueFunc) {
    ArrayList<Contact> allContacts = new ArrayList<>(contacts.values());
    // Sort contacts by the chosen property
    allContacts.sort((contact1, contact2) -> getCompareValueFunc.apply(contact1).compareToIgnoreCase(getCompareValueFunc.apply(contact2)));
    prefix = prefix.toLowerCase();

    int low = 0, high = allContacts.size() - 1;
    int foundIndex = -1;

    // Binary search for any contact whose property starts with the prefix
    while (low <= high) {
      int mid = (low + high) / 2;
      String contactValue = getCompareValueFunc.apply(allContacts.get(mid)).toLowerCase();
      if (contactValue.startsWith(prefix)) {
        foundIndex = mid;
        break;
      } else if (contactValue.compareTo(prefix) < 0) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }

    if (foundIndex == -1) {
      return new ArrayList<>();
    }

    // Expand to find all contacts with the same prefix
    int left = foundIndex;
    int right = foundIndex;
    while (left - 1 >= 0 && getCompareValueFunc.apply(allContacts.get(left - 1)).toLowerCase().startsWith(prefix)) {
      left--;
    }
    while (right + 1 < allContacts.size() && getCompareValueFunc.apply(allContacts.get(right + 1)).toLowerCase().startsWith(prefix)) {
      right++;
    }

    ArrayList<Contact> result = new ArrayList<>();
    for (int i = left; i <= right; i++) {
      result.add(allContacts.get(i));
    }
    return result;
  }

  /**
   * Searches for contacts by exact match on any property using binary search for O (log n) performance
   * The getCompareValueFunc function extracts the property to search on.
   * Returns a list of all matching contacts.
   *
   * @param value the exact value to search for
   * @param getCompareValueFunc the getter for the property used in comparison
   * @return matching contacts, or an empty list if none found
   */
  public ArrayList<Contact> searchContactsByExactMatch(String value, Function<Contact, String> getCompareValueFunc) {
    ArrayList<Contact> allContacts = new ArrayList<>(contacts.values());
    // Sort contacts by the chosen property 
    allContacts.sort((contact1, contact2) -> getCompareValueFunc.apply(contact1).compareToIgnoreCase(getCompareValueFunc.apply(contact2)));
    value = value.toLowerCase();

    int low = 0, high = allContacts.size() - 1;
    int foundIndex = -1;

    // Binary search for any contact whose property matches the value
    while (low <= high) {
      int mid = (low + high) / 2;
      String contactValue = getCompareValueFunc.apply(allContacts.get(mid)).toLowerCase();
      int comparison = contactValue.compareTo(value);
      if (comparison == 0) {
        foundIndex = mid;
        break;
      } else if (comparison < 0) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }

    if (foundIndex == -1) {
      return new ArrayList<>();
    }

    // Expand to find all contacts with the same exact value
    int left = foundIndex;
    int right = foundIndex;
    while (left - 1 >= 0 && getCompareValueFunc.apply(allContacts.get(left - 1)).equalsIgnoreCase(value)) {
      left--;
    }
    while (right + 1 < allContacts.size() && getCompareValueFunc.apply(allContacts.get(right + 1)).equalsIgnoreCase(value)) {
      right++;
    }

    ArrayList<Contact> result = new ArrayList<>();
    for (int i = left; i <= right; i++) {
      result.add(allContacts.get(i));
    }
    return result;
  }
}
