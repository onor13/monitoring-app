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
import task.criteria.ApplicationNameFilterCriteria;
import task.criteria.FilterCriteria;

public class ApplicationNameFilterCriteriaTest {

  final static String expectedAppName = "accounting";

  @Test
  public void testAcceptedApplicationNameFilter(){
    Application app = new FakeApplication(expectedAppName);
    FilterCriteria criteria = new ApplicationNameFilterCriteria(expectedAppName);
    TaskResult taskResult = createTaskResult(app);
    assertTrue(criteria.isAccepted(taskResult), "application name comparison");
  }

  @Test
  public void testRejectedApplicationNameFilter(){
    Application app = new FakeApplication(expectedAppName);
    FilterCriteria criteria = new ApplicationNameFilterCriteria("fakeValue");
    TaskResult taskResult = createTaskResult(app);
    assertFalse(criteria.isAccepted(taskResult), "application name comparison");
  }

  private TaskResult createTaskResult(Application app) {
    return new FakeTaskResult(app, "any", "any",
        TaskResultType.SUCCESS, LocalDateTime.now(), null);
  }
}
