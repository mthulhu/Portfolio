public class Task {
  private String id;
  private String name;
  private String description;
  
  public Task(String id, String name, String description) {
    if (id == null || id.length() > 10) {
      throw new IllegalArgumentException("Invalid ID");
    }
    this.id = id;
    setName(name);
    setDescription(description);
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name == null || name.length() > 20) {
      throw new IllegalArgumentException("Invalid name");
    }
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    if (description == null || description.length() > 50) {
      throw new IllegalArgumentException("Invalid description");
    }
    this.description = description;
  }

  
}