import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppointmentServiceTest {
  private AppointmentService appointmentService;

  @BeforeEach
  void setUp() {
    appointmentService = new AppointmentService();
    appointmentService.addAppointment(new Appointment("1", new Date(), "some desc"));
  }

  @Test
  void testAddAppointment() {
    Appointment testAppointment = new Appointment("2", new Date(), "some desc");
    appointmentService.addAppointment(testAppointment);
    assertEquals(testAppointment, appointmentService.getAppointment("2"));
  }

  @Test
  void testAddAppointmentAlreadyExists() {
    Appointment testAppointment = new Appointment("1", new Date(), "some desc");
    assertThrows(IllegalArgumentException.class, () -> {
      appointmentService.addAppointment(testAppointment);
    });
  }

  @Test
  void testDeleteAppointment() {
    appointmentService.deleteAppointment("1");
    assertNull(appointmentService.getAppointment("1"));
  }

  @Test
  void testDeleteAppointmentNotExists() {
    assertThrows(IllegalArgumentException.class, () -> {
      appointmentService.deleteAppointment("999");
    });
  }
}
