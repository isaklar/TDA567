import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import java.util.Arrays;

public class Lab1BlackBox{
  private WorkSchedule schedule;

  /* ####################################################################
                            EXERCISE A
   #################################################################### */

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

  // when test goes through
  // TODO
  @Test
  public void testWhenSuccessA(){
    int size = 10;
  }

  /* ####################################################################
                            EXERCISE B
   #################################################################### */

  // this test should return an empty list
  // starttime <= endtime
  @Test
  public void testStartLessThanEnd() {
    int size = 10;
		schedule = new WorkSchedule(size);
		schedule.setRequiredNumber(1, size, size+1);
		schedule.addWorkingPeriod("employee", size, size+1);
    // but ends up still returning all entries within the interval
    // even though we set starttime > endtime which means WE HAVE A BUG LADIES AND GENTLEMEN
		assertTrue(schedule.workingEmployees(size+1, size).length == 0);
	}

  // when test goes through
  // TODO
  @Test
  public void testWhenSuccessB(){
    int size = 10;
  }
}
