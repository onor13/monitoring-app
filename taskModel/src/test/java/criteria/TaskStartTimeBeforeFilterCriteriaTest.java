package criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import task.criteria.FilterCriteria;
import task.criteria.TaskStartTimeBeforeFilterCriteria;

public class TaskStartTimeBeforeFilterCriteriaTest extends TaskStartTimeFilterCriteriaTest {

  @Test
  public void testAcceptedTaskResultTypeFilter() {
    FilterCriteria criteria = new TaskStartTimeBeforeFilterCriteria(referenceTime);
    assertTrue(criteria.isAccepted(taskResultStartedBefore), "taskResult startTime comparison");
  }

  @Test
  public void testRejectedTaskResultTypeFilter() {
    FilterCriteria criteria = new TaskStartTimeBeforeFilterCriteria(referenceTime);
    assertFalse(criteria.isAccepted(taskResultStartedAfter), "taskResult startTime comparison");
  }

}
