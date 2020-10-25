package criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fake.FakeApplication;
import fake.FakeTaskResult;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import task.Application;
import task.TaskResult;
import task.TaskResultType;
import task.criteria.FilterCriteria;
import task.criteria.TaskResultTypeFilterCriteria;

public class TaskResultTypeFilterCriteriaTest {

  final static String appName = "accounting";
  final static TaskResultType expectedTaskResultType = TaskResultType.ERROR;
  final static TaskResultType unexpected = TaskResultType.SUCCESS;

  @Test
  public void testAcceptedTaskResultTypeFilter(){
    Application app = new FakeApplication(appName);
    FilterCriteria criteria = new TaskResultTypeFilterCriteria(expectedTaskResultType);
    TaskResult taskResult = createTaskResult(app, expectedTaskResultType);
    assertTrue(criteria.isAccepted(taskResult), "taskResultType comparison");
  }

  @Test
  public void testRejectedTaskResultTypeFilter(){
    Application app = new FakeApplication(appName);
    FilterCriteria criteria = new TaskResultTypeFilterCriteria(expectedTaskResultType);
    TaskResult taskResult = createTaskResult(app, unexpected);
    assertFalse(criteria.isAccepted(taskResult), "taskResultType comparison");
  }

  private TaskResult createTaskResult(Application app, TaskResultType taskResultType) {
    return new FakeTaskResult(app, "any", "any",
        taskResultType, LocalDateTime.now(), null);
  }
}
