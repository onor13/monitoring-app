package single;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fake.FakeApplication;
import fake.FakeTaskResult;
import filter.single.SingeTaskResultFilterByAppName;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import task.Application;
import task.TaskResult;
import task.TaskResultType;

@SuppressWarnings({"PMD.JUnitTestContainsTooManyAsserts", "PMD.BeanMembersShouldSerialize"})
public class SingleTaskResultFilterByAppNameTest {

  private static final String app1Name = "app1";
  private static final Application app1 = new FakeApplication(app1Name);
  private static TaskResult taskResultApp1;

  private static final String app2Name = "app2";
  private static final Application app2 = new FakeApplication(app2Name);
  private static TaskResult nonMatchingTaskResult;

  protected static TaskResult createTaskResult(Application application) {
    return new FakeTaskResult(application, "any", "any",
        TaskResultType.SUCCESS, LocalDateTime.now(), Duration.ofMinutes(1));
  }

  @BeforeAll
  protected static void setUp(){
    taskResultApp1 = createTaskResult(app1);
    nonMatchingTaskResult = createTaskResult(app2);
  }

  @Test
  public void testFilterMatching(){
    SingeTaskResultFilterByAppName filterByAppName = new SingeTaskResultFilterByAppName();
    filterByAppName.setApplicationNameFilter(app1Name);
    assertTrue(filterByAppName.isAccepted(taskResultApp1), "filter should accept");
  }

  @Test
  public void testFilterNonMatching(){
    SingeTaskResultFilterByAppName filterByAppName = new SingeTaskResultFilterByAppName();
    filterByAppName.setApplicationNameFilter(app1Name);
    assertFalse(filterByAppName.isAccepted(nonMatchingTaskResult), "filter should not accept");
  }

  @Test
  public void testFilterDefaultEmpty(){
    SingeTaskResultFilterByAppName filterByAppName = new SingeTaskResultFilterByAppName();
    assertTrue(filterByAppName.isEmptyFilter(), "filter was never set, so should be empty");
  }

  @Test
  public void testFilterNonEmpty(){
    SingeTaskResultFilterByAppName filterByAppName = new SingeTaskResultFilterByAppName();
    filterByAppName.setApplicationNameFilter(app1Name);
    assertFalse(filterByAppName.isEmptyFilter(), "filter should not be empty");
  }

  @Test
  public void testFilterReset(){
    SingeTaskResultFilterByAppName filterByAppName = new SingeTaskResultFilterByAppName();
    filterByAppName.setApplicationNameFilter(app1Name);
    assertFalse(filterByAppName.isEmptyFilter(), "filter should not be empty");
    filterByAppName.resetFilter();
    assertTrue(filterByAppName.isEmptyFilter(), "after reset filter should be empty");
  }
}
