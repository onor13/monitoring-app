package criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import task.criteria.FilterCriteria;
import task.criteria.TaskExecutionDurationBelowFilterCriteria;

public class TaskExecutionDurationBelowFilterCriteriaTest extends TaskExecutionDurationFilterCriteriaTest {

  @Test
  public void testAcceptedTaskResultTypeFilter(){
    FilterCriteria criteria = new TaskExecutionDurationBelowFilterCriteria(referenceDuration);
    assertTrue(criteria.isAccepted(taskResultDurationBelow), "taskResult execution duration comparison");
  }

  @Test
  public void testRejectedTaskResultTypeFilter(){
    FilterCriteria criteria = new TaskExecutionDurationBelowFilterCriteria(referenceDuration);
    assertFalse(criteria.isAccepted(taskResultDurationAbove), "taskResult execution duration comparison");
  }
}
