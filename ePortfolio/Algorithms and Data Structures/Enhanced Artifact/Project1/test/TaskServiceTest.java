import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskServiceTest {
  private TaskService taskService;

  @BeforeEach
  void setUp() {
    taskService = new TaskService();
    taskService.addTask(new Task("1", "name1", "description1"));
  }


  @Test
  void testAddTask() {
    Task newTask = new Task("2", "name2", "description2");
    taskService.addTask(newTask);
    assertEquals(newTask, taskService.getTask("2"));
  }

  @Test
  void testAddTaskAlreadyExistingId() {
    assertThrows(IllegalArgumentException.class, () -> {
      taskService.addTask(new Task("1", "name1", "description"));
    });
  }

  @Test
  void testUpdateTask() {
    Task updateTask = new Task("1", "updateName", "updateDescription");
    taskService.updateTask(updateTask);
    assertEquals(updateTask, taskService.getTask("1"));
  }

  @Test
  void updateTaskNotExists() {
    assertThrows(IllegalArgumentException.class, () -> {
      taskService.updateTask(new Task("2", "f", "f"));
    });
  }

  @Test
  void testDeleteTask() {
    taskService.deleteTask("1");
    assertEquals(null, taskService.getTask("1"));
  }

  @Test
  void testDeleteAppointmentNotExists() {
    assertThrows(IllegalArgumentException.class, () -> {
      taskService.deleteTask("2");
    });
  }
}
