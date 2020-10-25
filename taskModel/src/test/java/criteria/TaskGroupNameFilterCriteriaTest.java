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
import task.criteria.TaskGroupNameFilterCriteria;

public class TaskGroupNameFilterCriteriaTest {
  final static String appName = "accounting";
  final static String expectedGroupName = "bill";
  final static String unexpected = "fake";

  @Test
  public void testAcceptedTaskResultTypeFilter(){
    Application app = new FakeApplication(appName);
    FilterCriteria criteria = new TaskGroupNameFilterCriteria(expectedGroupName);
    TaskResult taskResult = createTaskResult(app, expectedGroupName);
    assertTrue(criteria.isAccepted(taskResult), "task group comparison");
  }

  @Test
  public void testRejectedTaskResultTypeFilter(){

    Application app = new FakeApplication(appName);
    FilterCriteria criteria = new TaskGroupNameFilterCriteria(expectedGroupName);
    TaskResult taskResult = createTaskResult(app, unexpected);
    assertFalse(criteria.isAccepted(taskResult), "task group comparison");
  }

  private TaskResult createTaskResult(Application app, String groupName) {
    return new FakeTaskResult(app, "any", groupName,
        TaskResultType.SUCCESS, LocalDateTime.now(), null);
  }
}
