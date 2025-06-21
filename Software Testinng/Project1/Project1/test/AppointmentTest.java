import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppointmentTest {
  private Appointment testAppointment;
  private Date testDate;
  
  @BeforeEach
  void setup() {
    testDate = new Date();
    testAppointment = new Appointment("1", testDate, "some description");
  }

  @Test
  void testGetDate() {
    assertEquals(testDate, testAppointment.getDate());
  }

  @Test
  void testGetDescription() {
    assertEquals("some description", testAppointment.getDescription());
  }

  @Test
  void testGetId() {
    assertEquals("1", testAppointment.getId());
  }

  @Test
  void testSetDateBeforeToday() {
    LocalDateTime pastDateTime = LocalDateTime.ofInstant(testDate.toInstant(), ZoneId.systemDefault());
    Date pastDate = Date.from(pastDateTime.atZone(ZoneId.systemDefault()).toInstant());
;
    assertThrows(IllegalArgumentException.class, () -> {
      testAppointment.setDate(pastDate);
    });
  }

  @Test
  void testSetDateNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      testAppointment.setDate(null);
    });
  }

  @Test
  void testSetDescriptionNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      testAppointment.setDescription(null);
    });
  }

  @Test
  void testSetDescriptionTooLong() {
    assertThrows(IllegalArgumentException.class, () -> {
      testAppointment.setDescription("Some description that is very long. 51 characters!!");
    });
  }

  @Test
  void testIdNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Appointment(null, new Date(), "some description");
    });
  }

  @Test
  void testIdTooLong() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Appointment("12345678901", new Date(), "some description");
    });
  }

}
