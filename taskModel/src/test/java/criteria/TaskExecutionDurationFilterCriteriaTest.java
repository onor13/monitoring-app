package criteria;

import fake.FakeApplication;
import fake.FakeTaskResult;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import task.Application;
import task.TaskResult;
import task.TaskResultType;

public class TaskExecutionDurationFilterCriteriaTest {
  protected static final Application app1 = new FakeApplication("app1");
  protected static TaskResult taskResultDurationAbove;
  protected static TaskResult taskResultDurationBelow;

  protected static final Duration referenceDuration = Duration.ofMinutes(1);
  protected static final Duration durationBelow = referenceDuration.minusMinutes(10);
  protected static final Duration durationAbove = referenceDuration.plusMinutes(10);

  @BeforeAll
  protected static void setUp() {
    taskResultDurationBelow = createTaskResult(app1, durationBelow);
    taskResultDurationAbove = createTaskResult(app1, durationAbove);
  }

  protected static TaskResult createTaskResult(Application application, Duration duration) {
    return new FakeTaskResult(application, "any", "any group",
        TaskResultType.SUCCESS, LocalDateTime.now(), duration);
  }
}
