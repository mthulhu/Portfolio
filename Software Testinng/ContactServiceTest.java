import static org.junit.Assert.*;

import org.junit.Test;

public class ContactServiceTest {
  private Contact testContact = new Contact("1", "fname", "lname", "1234567890", "123 example st");

  @Test
  public void testAddContact() {
    ContactService contactService = new ContactService();
    contactService.addContact(testContact);
    assertEquals(testContact, contactService.getContact("1"));
    assertThrows(IllegalArgumentException.class, () -> {
      contactService.addContact(testContact);
    });

  }

  @Test
  public void testDeleteContact() {
    ContactService contactService = new ContactService();
    contactService.addContact(testContact);
    contactService.deleteContact("1");
    assertNull(contactService.getContact("1"));
  }

  @Test
  public void testUpdateContact() {
    ContactService contactService = new ContactService();
    contactService.addContact(testContact);
    Contact updateContact = new Contact("1", "ffname", "llname", "1234567899", "1234 example st");
    contactService.updateContact("1", "ffname", "llname", "1234567899", "1234 example st");
    Contact updatedContact = contactService.getContact("1");
    assertEquals("1", updatedContact.getId());
    assertEquals("ffname", updatedContact.getFirstName());
    assertEquals("llname", updatedContact.getLastName());
    assertEquals("1234567899", updatedContact.getPhone());
    assertEquals("1234 example st", updatedContact.getAddress());
    

    assertThrows(IllegalArgumentException.class, () -> {
      contactService.updateContact("2", "ffname", "llname", "1234567899", "1234 example st");
    });
  }
}
