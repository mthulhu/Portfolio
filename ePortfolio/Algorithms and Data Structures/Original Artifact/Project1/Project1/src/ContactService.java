import java.util.HashMap;

public class ContactService {
  private HashMap<String, Contact> contacts = new HashMap<String, Contact>();

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
}
