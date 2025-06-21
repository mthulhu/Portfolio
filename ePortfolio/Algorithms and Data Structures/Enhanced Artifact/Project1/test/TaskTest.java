import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskTest {
  private Task testTask;
  @BeforeEach
  void setUp() {
    testTask = new Task("0", "Name with 20 chars..", "Some description that is 50 characters long.......");
  }

  @Test
  void testNullId() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Task(null, "some name", "some desc");
    });
  }

  @Test
  void testIdTooLong() {
   assertThrows(IllegalArgumentException.class, () -> {
      new Task("some11chars", "some name", "some desc");
    });  
  }

  @Test
  void testSetDescriptionNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      testTask.setDescription(null);
    });
  }

  @Test
  void testSetDescriptionTooLong() {
    assertThrows(IllegalArgumentException.class, () -> {
      testTask.setDescription("Some description that is 51 characters long........");
    });
  }

  @Test
  void testSetNameNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      testTask.setName(null);
    });
  }

  @Test
  void testSetNameTooLong() {
    assertThrows(IllegalArgumentException.class, () -> {
      testTask.setName("Name with 21 chars...");
    });
  }
}
