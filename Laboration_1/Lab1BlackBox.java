import org.junit.Test;

public class Lab1BlackBox{
  private WorkSchedule schedule;

  // starttime < 0
  @Test
  public void startTimeZero(){
    int size = 10; // random size
    schedule = new WorkSchedule(size);
    schedule.setRequiredNumber(1, 0, 9);
    boolean result = schedule.addWorkingPeriod("employee", size-1, size);
    assertFalse(result);
  }

  // endtime >= size
  @Test
  public void endTimeSize(){
    int size = 10; // random size
    schedule = new WorkSchedule(size);
    schedule.setRequiredNumber(1, 0, 9);
    boolean result = schedule.addWorkingPeriod("employee", size, size+1);
    assertFalse(result);
  }

}
