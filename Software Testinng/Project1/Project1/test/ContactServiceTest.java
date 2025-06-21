import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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

  @Test
  public void testQuickSortEmptyList() {
    ContactService contactService = new ContactService();
    ArrayList<Contact> contacts = new ArrayList<>();
    ArrayList<Contact> sorted = contactService.quickSort(contacts, 0, contacts.size() - 1, Contact::getLastName);
    assertTrue(sorted.isEmpty());
  }

  @Test
  public void testQuickSortSingleElement() {
    ContactService contactService = new ContactService();
    Contact c1 = new Contact("1", "fname", "lname", "1234567890", "123 example st");
    ArrayList<Contact> contacts = new ArrayList<>(Arrays.asList(c1));
    ArrayList<Contact> sorted = contactService.quickSort(contacts, 0, contacts.size() - 1, Contact::getLastName);
    assertEquals(1, sorted.size());
    assertEquals(c1, sorted.get(0));
  }

  @Test
  public void testQuickSortMultipleElements() {
    ContactService contactService = new ContactService();
    Contact c1 = new Contact("1", "fname1", "lname1", "1234567890", "123 example st");
    Contact c2 = new Contact("2", "fname2", "lname2", "1234567890", "1234 example st");
    Contact c3 = new Contact("3", "fname3", "lname3", "1234567890", "1235 example st");
    ArrayList<Contact> contacts = new ArrayList<>(Arrays.asList(c1, c2, c3));
    // Unsorted order: Smith, Anderson, Brown
    contactService.quickSort(contacts, 0, contacts.size() - 1, Contact::getLastName);
    // Sorted order should be: Anderson, Brown, Smith
    assertEquals("lname1", contacts.get(0).getLastName());
    assertEquals("lname2", contacts.get(1).getLastName());
    assertEquals("lname3", contacts.get(2).getLastName());
  }

  @Test
  public void testQuickSortWithDuplicates() {
    ContactService contactService = new ContactService();
    Contact c1 = new Contact("1", "fname1", "lname1", "1234567890", "123 example st");
    Contact c2 = new Contact("2", "fname2", "lname2", "1234567890", "1234 example st");
    Contact c3 = new Contact("3", "fname3", "lname3", "1234567890", "1235 example st");
    ArrayList<Contact> contacts = new ArrayList<>(Arrays.asList(c1, c2, c3));
    contactService.quickSort(contacts, 0, contacts.size() - 1, Contact::getLastName);
    // Sorted order should be: Anderson, Smith, Smith
    assertEquals("lname1", contacts.get(0).getLastName());
    assertEquals("lname2", contacts.get(1).getLastName());
    assertEquals("lname3", contacts.get(2).getLastName());
  }

  @Test
  public void testSearchContactsByExactMatch_FirstName() {
    ContactService contactService = new ContactService();
    Contact c1 = new Contact("1", "fname1", "lname1", "1234567890", "123 example st");
    Contact c2 = new Contact("2", "fname1", "lname2", "1234567890", "1234 example st");
    Contact c3 = new Contact("3", "fname2", "lname3", "1234567890", "1235 example st");
    contactService.addContact(c1);
    contactService.addContact(c2);
    contactService.addContact(c3);

    ArrayList<Contact> result = contactService.searchContactsByExactMatch("fname1", Contact::getFirstName);
    assertEquals(2, result.size());
    assertTrue(result.contains(c1));
    assertTrue(result.contains(c2));
  }

  @Test
  public void testSearchContactsByExactMatch_LastName() {
    ContactService contactService = new ContactService();
    Contact c1 = new Contact("1", "fname1", "lname1", "1234567890", "123 example st");
    Contact c2 = new Contact("2", "fname2", "lname1", "1234567890", "1234 example st");
    Contact c3 = new Contact("3", "fname3", "lname3", "1234567890", "1235 example st");
    contactService.addContact(c1);
    contactService.addContact(c2);
    contactService.addContact(c3);

    ArrayList<Contact> result = contactService.searchContactsByExactMatch("lname1", Contact::getLastName);
    assertEquals(2, result.size());
    assertTrue(result.contains(c1));
    assertTrue(result.contains(c2));
  }

  @Test
  public void testSearchContactsByExactMatch_NoMatch() {
    ContactService contactService = new ContactService();
    Contact c1 = new Contact("1", "fname", "lname", "1234567890", "123 example st");
    contactService.addContact(c1);

    ArrayList<Contact> result = contactService.searchContactsByExactMatch("", Contact::getFirstName);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testSearchContactsByExactMatch_CaseInsensitive() {
    ContactService contactService = new ContactService();
    Contact c1 = new Contact("1", "FNAME", "lname1", "1234567890", "123 example st");
    Contact c2 = new Contact("2", "fname", "lname2", "1234567890", "1234 example st");
    contactService.addContact(c1);
    contactService.addContact(c2);

    ArrayList<Contact> result = contactService.searchContactsByExactMatch("fname", Contact::getFirstName);
    assertEquals(2, result.size());
    assertTrue(result.contains(c1));
    assertTrue(result.contains(c2));
  }

  @Test
  public void testSearchContactsByPrefix_FirstName() {
    ContactService contactService = new ContactService();
    Contact c1 = new Contact("1", "fname", "lname", "1234567890", "123 example st");
    Contact c2 = new Contact("2", "fnameeee", "lname", "1234567890", "1234 example st");
    Contact c3 = new Contact("3", "fnm", "lname", "1234567890", "1235 example st");
    contactService.addContact(c1);
    contactService.addContact(c2);
    contactService.addContact(c3);

    ArrayList<Contact> result = contactService.searchContactsByPrefix("fna", Contact::getFirstName);
    assertEquals(2, result.size());
    assertTrue(result.contains(c1));
    assertTrue(result.contains(c2));
  }

  @Test
  public void testSearchContactsByPrefix_NoMatch() {
    ContactService contactService = new ContactService();
    Contact c1 = new Contact("1", "fname", "lname", "1234567890", "123 example st");
    contactService.addContact(c1);

    ArrayList<Contact> result = contactService.searchContactsByPrefix("Z", Contact::getFirstName);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testSearchContactsByFirstName_ExactMatch() {
    ContactService contactService = new ContactService();
    Contact c1 = new Contact("1", "fname", "lname", "1234567890", "123 example st");
    Contact c2 = new Contact("2", "fname", "lname", "1234567890", "1234 example st");
    Contact c3 = new Contact("3", "fnameee", "lname", "1234567890", "1235 example st");
    contactService.addContact(c1);
    contactService.addContact(c2);
    contactService.addContact(c3);

    ArrayList<Contact> result = contactService.searchContactsByFirstName("fname");
    assertEquals(2, result.size());
    assertTrue(result.contains(c1));
    assertTrue(result.contains(c2));
  }

  @Test
  public void performanceTest_searchContactsByFirstName_small() {
    ContactService contactService = new ContactService();
    int numContacts = 100000; // 100 thousand contacts
    String targetFirstName = "fname";
    Random random = new Random();

    // Add many contacts with random and some matching first names
    for (int i = 0; i < numContacts; i++) {
      String firstName = (i % 100 == 0) ? targetFirstName : "fname" + random.nextInt(10000);
      Contact c = new Contact(
          String.valueOf(i),
          firstName,
          "lname" + random.nextInt(10000),
          String.valueOf(1000000000 + random.nextInt(900000000)),
          "addr" + random.nextInt(10000));
      contactService.addContact(c);
    }

    long start = System.nanoTime();
    ArrayList<Contact> result = contactService.searchContactsByFirstName(targetFirstName);
    long end = System.nanoTime();

    System.out.println("Found " + result.size() + " contacts with first name '" + targetFirstName + "'");
    System.out.println("Search took " + (end - start) / 1_000_000.0 + " ms");
  }

  @Test
  public void performanceTest_searchContactsByFirstName_large() {
    ContactService contactService = new ContactService();
    int numContacts = 1000000; // 1 million contacts
    String targetFirstName = "fname";
    Random random = new Random();

    // Add many contacts with random and some matching first names
    for (int i = 0; i < numContacts; i++) {
      String firstName = (i % 100 == 0) ? targetFirstName : "fname" + random.nextInt(10000);
      Contact c = new Contact(
          String.valueOf(i),
          firstName,
          "lname" + random.nextInt(10000),
          String.valueOf(1000000000 + random.nextInt(900000000)),
          "addr" + random.nextInt(10000));
      contactService.addContact(c);
    }

    long start = System.nanoTime();
    ArrayList<Contact> result = contactService.searchContactsByFirstName(targetFirstName);
    long end = System.nanoTime();

    System.out.println("Found " + result.size() + " contacts with first name '" + targetFirstName + "'");
    System.out.println("Search took " + (end - start) / 1_000_000.0 + " ms");
  }

}