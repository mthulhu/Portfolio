public class Contact {
  private String id;
  private String firstName;
  private String lastName;
  private String phone;
  private String address;

  public Contact(String id, String firstName, String lastName, String phone, String address) {
    if (id == null || id.length() > 10) {
      throw new IllegalArgumentException("Invalid ID");
    }
    this.id = id;
    setFirstName(firstName);
    setLastName(lastName);
    setPhone(phone);
    setAddress(address);
  }

  public String getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    if (firstName != null && firstName.length() <= 10) {
      this.firstName = firstName;
    } else {
      throw new IllegalArgumentException("Invalid first name");
    }
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    if (lastName != null && lastName.length() <= 10) {
      this.lastName = lastName;
    } else {
      throw new IllegalArgumentException("Invalid last name");
    }
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    if (phone != null && phone.matches("^\\d{10}$")) {
      this.phone = phone;
    } else {
      throw new IllegalArgumentException("Invalid phone number");
    }
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    if (address != null && address.length() <= 30) {
      this.address = address;
    } else {
      throw new IllegalArgumentException("Invalid address");
    }
  }
}