package single;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fake.FakeApplication;
import fake.FakeTaskResult;
import filter.single.SingeTaskResultFilterByAppName;
import filter.single.SingleTaskResultFilter;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import task.Application;
import task.TaskResult;
import task.TaskResultType;

@SuppressWarnings({"PMD.JUnitTestContainsTooManyAsserts", "PMD.BeanMembersShouldSerialize"})
public class SingleTaskResultFilterByAppNameTest extends SingleTaskFilterTest {

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
    SingleTaskResultFilter filterByAppName = createInitializedFilter();
    assertTrue(filterByAppName.isAccepted(taskResultApp1), "filter should accept");
  }

  @Test
  public void testFilterNonMatching(){
    SingleTaskResultFilter filterByAppName = createInitializedFilter();
    assertFalse(filterByAppName.isAccepted(nonMatchingTaskResult), "filter should not accept");
  }


  @Override
  public SingleTaskResultFilter createEmptyFilter() {
    return new SingeTaskResultFilterByAppName();
  }

  @Override
  public SingleTaskResultFilter createInitializedFilter() {
    SingeTaskResultFilterByAppName filterByAppName = new SingeTaskResultFilterByAppName();
    filterByAppName.setApplicationNameFilter(app1Name);
    return filterByAppName;
  }

}
