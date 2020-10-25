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
public abstract class SingleTaskFilterStartTime extends SingleTaskFilterTest {
  protected static final Application app1 = new FakeApplication("app1");
  protected static TaskResult taskResultStartedBeforeReferenceTime;
  protected static TaskResult taskResultStartedAfterReferenceTime;

  protected static final LocalDateTime referenceTime = LocalDateTime.now();
  protected static final LocalDateTime beforeReferenceTime = referenceTime.minusMinutes(10);
  protected static final LocalDateTime afterReferenceTime = referenceTime.plusMinutes(10);

  protected static TaskResult createTaskResult(Application application, LocalDateTime startTime) {
    return new FakeTaskResult(application, "any", "any group",
        TaskResultType.SUCCESS, startTime, Duration.ofMinutes(1));
  }

  @BeforeAll
  protected static void setUp() {
    taskResultStartedBeforeReferenceTime = createTaskResult(app1, beforeReferenceTime);
    taskResultStartedAfterReferenceTime = createTaskResult(app1, afterReferenceTime);
  }

  @Test
  @Override
  public void testFilterDefaultEmpty(){
    SingleTaskResultFilter filter = createEmptyFilter();
    assertTrue(filter.isEmptyFilter(), "filter was never set, so should be empty");
    assertTrue(filter.isAccepted(taskResultStartedBeforeReferenceTime), "empty filter should always accept");
    assertTrue(filter.isAccepted(taskResultStartedAfterReferenceTime), "empty filter should always accept");
  }

}
