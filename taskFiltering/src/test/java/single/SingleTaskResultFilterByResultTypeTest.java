package single;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fake.FakeApplication;
import fake.FakeTaskResult;
import filter.single.SingleTaskResultFilter;
import filter.single.SingleTaskResultFilterByResultType;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import task.Application;
import task.TaskResult;
import task.TaskResultType;

@SuppressWarnings({"PMD.JUnitTestContainsTooManyAsserts", "PMD.BeanMembersShouldSerialize"})
public class SingleTaskResultFilterByResultTypeTest extends SingleTaskFilterTest {

  private static final Application app1 = new FakeApplication("app1");
  private static TaskResult matchingTaskResult;
  private static TaskResult nonMatchingTaskResult;
  private static TaskResultType matchingResultType = TaskResultType.SUCCESS;

  protected static TaskResult createTaskResult(Application application, TaskResultType taskResultType) {
    return new FakeTaskResult(application, "any", "groupName",
        taskResultType, LocalDateTime.now(), Duration.ofMinutes(1));
  }

  @BeforeAll
  protected static void setUp(){
    matchingTaskResult = createTaskResult(app1, matchingResultType);
    nonMatchingTaskResult = createTaskResult(app1, TaskResultType.ERROR);
  }

  @Test
  public void testFilterMatching(){
    SingleTaskResultFilterByResultType filter = new SingleTaskResultFilterByResultType();
    filter.addAcceptedResultType(matchingResultType);
    assertTrue(filter.isAccepted(matchingTaskResult), "filter should accept");
  }

  @Test
  public void testFilterNonMatching(){
    SingleTaskResultFilterByResultType filter = new SingleTaskResultFilterByResultType();
    filter.addAcceptedResultType(matchingResultType);
    assertFalse(filter.isAccepted(nonMatchingTaskResult), "filter should not accept");
  }

  @Override
  public SingleTaskResultFilter createEmptyFilter() {
    return new SingleTaskResultFilterByResultType();
  }

  @Override
  public SingleTaskResultFilter createInitializedFilter() {
    SingleTaskResultFilterByResultType filter = new SingleTaskResultFilterByResultType();
    filter.addAcceptedResultType(matchingResultType);
    return filter;
  }
}
