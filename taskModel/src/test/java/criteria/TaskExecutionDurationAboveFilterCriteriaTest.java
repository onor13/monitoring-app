package criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import task.criteria.FilterCriteria;
import task.criteria.TaskExecutionDurationAboveFilterCriteria;

public class TaskExecutionDurationAboveFilterCriteriaTest extends TaskExecutionDurationFilterCriteriaTest {

  @Test
  public void testAcceptedTaskResultTypeFilter(){
    FilterCriteria criteria = new TaskExecutionDurationAboveFilterCriteria(referenceDuration);
    assertTrue(criteria.isAccepted(taskResultDurationAbove), "taskResult execution duration comparison");
  }

  @Test
  public void testRejectedTaskResultTypeFilter(){
    FilterCriteria criteria = new TaskExecutionDurationAboveFilterCriteria(referenceDuration);
    assertFalse(criteria.isAccepted(taskResultDurationBelow), "taskResult execution duration comparison");
  }
}
