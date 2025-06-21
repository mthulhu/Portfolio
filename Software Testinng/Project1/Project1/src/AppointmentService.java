import java.util.HashMap;

public class AppointmentService {
  private HashMap<String, Appointment> appointments = new HashMap<String, Appointment>();
  
  public void deleteAppointment(String id) {
    if (appointments.get(id) == null) {
      throw new IllegalArgumentException("Appointment does not exist");
    }
    else {
      appointments.remove(id);
    }
  } 

  public void addAppointment(Appointment appointment) {
    if (appointments.get(appointment.getId()) != null) {
      throw new IllegalArgumentException("Appointment already exists");
    } else {
      appointments.put(appointment.getId(), appointment);
    }
  }

  public Appointment getAppointment(String id) {
    return appointments.get(id);
  }
}
