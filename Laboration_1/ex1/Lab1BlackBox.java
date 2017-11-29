import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import java.util.Arrays;

public class Lab1BlackBox{
  private WorkSchedule schedule;
  private WorkSchedule dummySchedule;

  /* ####################################################################
                            EXERCISE A
   #################################################################### */

  // starttime < 0
  @Test
  public void testStartTimeZero(){
    int size = 10; // random size
    schedule = new WorkSchedule(size);
    dummySchedule = new WorkSchedule(size);
    schedule.setRequiredNumber(1, 0, 9);
    dummySchedule.setRequiredNumber(1, 0, 9);

    boolean result = schedule.addWorkingPeriod("employee", -1, 7);
    assertFalse(result);
    compare(schedule, dummySchedule, size);
  }

  // endtime >= size
  @Test
  public void testEndTimeSize(){
    int size = 10; // random size
    schedule = new WorkSchedule(size);
    dummySchedule = new WorkSchedule(size);
    schedule.setRequiredNumber(1, 0, 9);
    dummySchedule.setRequiredNumber(1, 0, 9);

    boolean result = schedule.addWorkingPeriod("employee", 2, 10);
    assertFalse(result);
    compare(schedule, dummySchedule, size);
  }

  // starttime > endtime
  // expected: To no work = the test should go through
  @Test
  public void testEndTimeStartTime(){
    int size = 10; // random size
    schedule = new WorkSchedule(size);
    dummySchedule = new WorkSchedule(size);
    schedule.setRequiredNumber(1, 0, 9);
    dummySchedule.setRequiredNumber(1, 0, 9);

    boolean result = schedule.addWorkingPeriod("employee", 6, 4);
    assertFalse(result);
    compare(schedule, dummySchedule, size);
  }

  //  add workingEmployee > requiredNumber
  @Test
  public void testRequiredNumber(){
    int size = 10; // random size
    schedule = new WorkSchedule(size);
    dummySchedule = new WorkSchedule(size);
    schedule.setRequiredNumber(1, 0, 9);
    schedule.addWorkingPeriod("employee1", 5, 7);
    dummySchedule.setRequiredNumber(1, 0, 9);
    dummySchedule.addWorkingPeriod("employee1", 5, 7);

    boolean result = schedule.addWorkingPeriod("employee2", 5, 7);
    assertFalse(result);
    compare(schedule, dummySchedule, size);
  }

  // any workingEmployee == employee
  @Test
  public void testEmployeeExist(){
    int size = 10;
		schedule = new WorkSchedule(size);
		dummySchedule = new WorkSchedule(size);
		schedule.setRequiredNumber(2, 0, 9);
		schedule.addWorkingPeriod("employee", 5, 5);
		dummySchedule.setRequiredNumber(2, 0, 9);
		dummySchedule.addWorkingPeriod("employee", 5, 5);

		boolean result = schedule.addWorkingPeriod("employee", 2, 7);
		assertFalse(result);
		compare(schedule, dummySchedule, size);
  }

  // when test goes through
  // TODO
  @Test
  public void testWhenSuccessA(){
    int size = 10;
		schedule = new WorkSchedule(size);
		dummySchedule = new WorkSchedule(size);
		schedule.setRequiredNumber(2, 2, 7); // random hours
		schedule.addWorkingPeriod("employee1", 5, 5);
		dummySchedule.setRequiredNumber(2, 2, 7);
		dummySchedule.addWorkingPeriod("employee1", 5, 5);
    boolean result = schedule.addWorkingPeriod("employee2", 2, 7);
    assertTrue(result);

    for(int i = 0; i < size; i++) {
			WorkSchedule.Hour h1 = schedule.readSchedule(i);
			WorkSchedule.Hour h2 = dummySchedule.readSchedule(i);

			if(i >= 2 && i <= 7) {
				// workingEmployees contain a string equal to employee
				assertTrue(Arrays.asList(h1.workingEmployees).contains("employee2"));
			} else {
				// Checks if schedule is unchanged with dummySchedule
				assertTrue(h1.requiredNumber == h2.requiredNumber);
				assertTrue(Arrays.deepEquals(h1.workingEmployees, h2.workingEmployees));
			}
		}
  }

  private void compare(WorkSchedule s1, WorkSchedule s2, int size) {
		for(int i = 0; i < size; i++) {
			WorkSchedule.Hour h1 = s1.readSchedule(i);
			WorkSchedule.Hour h2 = s2.readSchedule(i);
			assertTrue(h1.requiredNumber == h2.requiredNumber);
			assertTrue(Arrays.deepEquals(h1.workingEmployees, h2.workingEmployees));
		}
	}

  /* ####################################################################
                            EXERCISE B
   #################################################################### */

  // starttime > endtime
  @Test
  public void testStartLessThanEnd() {
    int size = 10;
		schedule = new WorkSchedule(size);
    dummySchedule = new WorkSchedule(size);
		schedule.setRequiredNumber(1, 0, 9);
		schedule.addWorkingPeriod("employee", 0, 9);
    dummySchedule.setRequiredNumber(1, 0, 9);
    dummySchedule.addWorkingPeriod("employee", 0, 9);
    // tests what happends if we set starttime > Endtime
    // expect: to return empty
    // but it doesnt which means we have a BUG.
		assertTrue(schedule.workingEmployees(9, 0).length == 0);
    compare(schedule, dummySchedule, size);
	}

  // starttime <= endtime
  @Test
  public void testWhenSuccessB(){
    int size = 10;
    schedule = new WorkSchedule(size);
    dummySchedule = new WorkSchedule(size);
    schedule.setRequiredNumber(1, 0, 9);
    schedule.addWorkingPeriod("employee", 0, 9);
    dummySchedule.setRequiredNumber(1, 0, 9);
    dummySchedule.addWorkingPeriod("employee", 0, 9);
    
    String[] employees = schedule.workingEmployees(0,9);
    // returns an array with distinct strings -- a string appears in the return array if and only if
    // it appears in the workingEmployees of at least one hour in the interval starttime to endtime
    assertTrue(Arrays.asList(employees).contains("employee"));
    compare(schedule, dummySchedule, size);
  }
}
