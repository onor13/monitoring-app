package single;

import static org.junit.jupiter.api.Assertions.assertTrue;

import fake.FakeApplication;
import fake.FakeTaskResult;
import filter.single.SingleTaskResultFilter;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import task.Application;
import task.TaskResult;
import task.TaskResultType;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.JUnitTestContainsTooManyAsserts"})
public abstract class SingleTaskFilterExecutionDurationTest extends SingleTaskFilterTest{

  protected static final Application app1 = new FakeApplication("app1");
  protected static TaskResult taskResultDurationAbove;
  protected static TaskResult taskResultDurationBelow;

  protected static final Duration referenceDuration = Duration.ofMinutes(1);
  protected static final Duration durationBelow = referenceDuration.minusMinutes(10);
  protected static final Duration durationAbove = referenceDuration.plusMinutes(10);

  protected static TaskResult createTaskResult(Application application, Duration duration) {
    return new FakeTaskResult(application, "any", "any group",
        TaskResultType.SUCCESS, LocalDateTime.now(), duration);
  }

  @BeforeAll
  protected static void setUp() {
    taskResultDurationBelow = createTaskResult(app1, durationBelow);
    taskResultDurationAbove = createTaskResult(app1, durationAbove);
  }

  @Test
  @Override
  public void testFilterDefaultEmpty(){
    SingleTaskResultFilter filter = createEmptyFilter();
    assertTrue(filter.isEmptyFilter(), "filter was never set, so should be empty");
    assertTrue(filter.isAccepted(taskResultDurationAbove), "empty filter should always accept");
    assertTrue(filter.isAccepted(taskResultDurationBelow), "empty filter should always accept");
  }
}
