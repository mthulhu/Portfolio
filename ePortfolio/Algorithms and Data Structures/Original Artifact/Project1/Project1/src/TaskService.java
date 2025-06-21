import java.util.HashMap;

public class TaskService {
  private HashMap<String, Task> tasks = new HashMap<String,Task>();

  public void addTask(Task task) {
    if (tasks.containsKey(task.getId())) {
      throw new IllegalArgumentException("ID already exists");
    }
    tasks.put(task.getId(), task);
  }

  public void updateTask(Task task) {
    Task taskToUpdate = tasks.get(task.getId());
    if (taskToUpdate == null) {
      throw new IllegalArgumentException("Task does not exist");
    }
    tasks.put(task.getId(), task);
  }

  public Task getTask(String id) {
    return tasks.get(id);
  }

  public void deleteTask(String id) {
    if (tasks.get(id) == null) {
      throw new IllegalArgumentException("Task does not exist");
    }
    tasks.remove(id);
  }
}
