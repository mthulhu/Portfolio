import static org.junit.Assert.*;

import org.junit.Test;

public class ContactTest {
  @Test
  public void testCreateContact() {
    Contact contact = new Contact("id12345678", "fname", "lname", "1234567890", "123 example st");
    assertNotNull(contact);
  }

  @Test
  public void testGetters() {
    Contact contact = new Contact("id12345678", "fname", "lname", "1234567890", "123 example st");
    assertEquals("id12345678", contact.getId());
    assertEquals("fname", contact.getFirstName());
    assertEquals("lname", contact.getLastName());
    assertEquals("1234567890", contact.getPhone());
    assertEquals("123 example st", contact.getAddress());
  }

  @Test
    public void testInavlidContactId() {
      // Too long
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("id12345678901", "fname", "lname", "1234567890", "123 example st");
        });
        // null
                assertThrows(IllegalArgumentException.class, () -> {
            new Contact(null, "fname", "lname", "1234567890", "123 example st");
        });
    }

    @Test
    public void testInvalidFirstName() {
      // Too long
      assertThrows(IllegalArgumentException.class, () -> {
            new Contact("ID12345678", "fname123456", "lname", "1234567890", "123 example st");
        });
      // null
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("ID12345678", null, "lname", "1234567890", "123 example st");
        });
    }

        @Test
    public void testInvalidLastName() {
      // Too long
      assertThrows(IllegalArgumentException.class, () -> {
            new Contact("ID12345678", "fname", "lname123456", "1234567890", "123 example st");
        });
      // null
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("ID12345678", "fname", null, "1234567890", "123 example st");
        });
    }


        @Test
    public void testInvalidPhone() {
      // Too long
      assertThrows(IllegalArgumentException.class, () -> {
            new Contact("ID12345678", "fname", "lname", "12345678900", "123 example st");
        });
              // Too short
      assertThrows(IllegalArgumentException.class, () -> {
            new Contact("ID12345678", "fname", "lname", "123456789", "123 example st");
        });
      // null
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("ID12345678", "fname", "lname", null, "123 example st");
        });
              // Contains a letter
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("ID12345678", "fname", "lname", "123456789n", "123 example st");
        });
    }

           @Test
    public void testInvalidAddress() {
      // Too long
      assertThrows(IllegalArgumentException.class, () -> {
            new Contact("ID12345678", "fname", "lname", "1234567890", "1234567890123456789012345678901");
        });
      // null
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("ID12345678", "fname", "lname", "1234567890", null);
        });
    }

}
