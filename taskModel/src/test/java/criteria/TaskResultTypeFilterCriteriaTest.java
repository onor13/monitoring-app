package criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import task.Application;
import task.JsonApplication;
import task.JsonTaskResult;
import task.TaskResultType;
import task.criteria.FilterCriteria;
import task.criteria.TaskResultTypeFilterCriteria;

public class TaskResultTypeFilterCriteriaTest {

  final static String appName = "accounting";
  final static TaskResultType expectedTaskResultType = TaskResultType.ERROR;
  final static TaskResultType unexpected = TaskResultType.SUCCESS;

  @Test
  public void testAcceptedTaskResultTypeFilter(){
    Application app = new JsonApplication(appName, LocalDateTime.now());
    FilterCriteria criteria = new TaskResultTypeFilterCriteria(expectedTaskResultType);
    JsonTaskResult taskResult = new JsonTaskResult(app);
    taskResult.setTaskResultType(expectedTaskResultType);
    assertTrue(criteria.isAccepted(taskResult), "taskResultType comparison");
  }

  @Test
  public void testRejectedTaskResultTypeFilter(){

    Application app = new JsonApplication(appName, LocalDateTime.now());
    FilterCriteria criteria = new TaskResultTypeFilterCriteria(expectedTaskResultType);
    JsonTaskResult taskResult = new JsonTaskResult(app);
    taskResult.setTaskResultType(unexpected);
    assertFalse(criteria.isAccepted(taskResult), "taskResultType comparison");
  }
}
