package criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import task.criteria.FilterCriteria;
import task.criteria.TaskStartTimeAfterFilterCriteria;

public class TaskStartTimeAfterFilterCriteriaTest extends TaskStartTimeFilterCriteriaTest {

  @Test
  public void testAcceptedTaskResultTypeFilter() {
    FilterCriteria criteria = new TaskStartTimeAfterFilterCriteria(referenceTime);
    assertTrue(criteria.isAccepted(taskResultStartedAfter), "taskResult startTime comparison");
  }

  @Test
  public void testRejectedTaskResultTypeFilter() {
    FilterCriteria criteria = new TaskStartTimeAfterFilterCriteria(referenceTime);
    assertFalse(criteria.isAccepted(taskResultStartedBefore), "taskResult startTime comparison");
  }
}
