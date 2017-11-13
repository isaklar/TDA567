import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;

public class Lab1BlackBox{
  private WorkSchedule schedule;

  // starttime < 0
  @Test
  public void testStartTimeZero(){
    int size = 10; // random size
    schedule = new WorkSchedule(size);
    schedule.setRequiredNumber(1, 0, 9);
    boolean result = schedule.addWorkingPeriod("employee", size-1, size);
    assertFalse(result);
  }

  // endtime >= size
  @Test
  public void testEndTimeSize(){
    int size = 10; // random size
    schedule = new WorkSchedule(size);
    schedule.setRequiredNumber(1, 0, 9);
    boolean result = schedule.addWorkingPeriod("employee", size, size+1);
    assertFalse(result);
  }

  // starttime > endtime
  @Test
  public void testEndTimeStartTime(){
    int size = 10; // random size
    schedule = new WorkSchedule(size);
    schedule.setRequiredNumber(1, 0, 9);
    boolean result = schedule.addWorkingPeriod("employee", size+1, size);
    assertFalse(result);
  }

  //  add workingEmployee > requiredNumber
  @Test
  public void testRequiredNumber(){
    int size = 10; // random size
    schedule = new WorkSchedule(size);
    schedule.setRequiredNumber(1, 0, 9);
    schedule.addWorkingPeriod("employee1", size, size);
    boolean result = schedule.addWorkingPeriod("employee2", size, size);
    assertFalse(result);
  }

  // any workingEmployee == employee
  @Test
  public void testEmployeeExist(){
    int size = 10; // random
    schedule = new WorkSchedule(size);
    schedule.setRequiredNumber(2, 0, 9);
    schedule. addWorkingPeriod("employee", size, size);
    boolean result = schedule.addWorkingPeriod("employee", size, size);
    assertFalse(result);
  }
}
