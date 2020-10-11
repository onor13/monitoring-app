package criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import task.Application;
import task.JsonApplication;
import task.JsonTaskResult;
import task.criteria.FilterCriteria;
import task.criteria.TaskGroupNameFilterCriteria;

public class TaskGroupNameFilterCriteriaTest {
  final static String appName = "accounting";
  final static String expectedGroupName = "bill";
  final static String unexpected = "fake";

  @Test
  public void testAcceptedTaskResultTypeFilter(){
    Application app = new JsonApplication(appName, LocalDateTime.now());
    FilterCriteria criteria = new TaskGroupNameFilterCriteria(expectedGroupName);
    JsonTaskResult taskResult = new JsonTaskResult(app);
    taskResult.setTaskGroup(expectedGroupName);
    assertTrue(criteria.isAccepted(taskResult), "task group comparison");
  }

  @Test
  public void testRejectedTaskResultTypeFilter(){

    Application app = new JsonApplication(appName, LocalDateTime.now());
    FilterCriteria criteria = new TaskGroupNameFilterCriteria(expectedGroupName);
    JsonTaskResult taskResult = new JsonTaskResult(app);
    taskResult.setTaskGroup(unexpected);
    assertFalse(criteria.isAccepted(taskResult), "task group comparison");
  }
}
