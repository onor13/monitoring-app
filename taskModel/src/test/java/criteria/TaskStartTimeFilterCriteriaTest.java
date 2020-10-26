package criteria;

import fake.FakeApplication;
import fake.FakeTaskResult;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import task.Application;
import task.TaskResult;
import task.TaskResultType;

public class TaskStartTimeFilterCriteriaTest {

  protected static final Application app1 = new FakeApplication("app1");
  protected static TaskResult taskResultStartedAfter;
  protected static TaskResult taskResultStartedBefore;

  protected static final LocalDateTime referenceTime = LocalDateTime.now();
  protected static final LocalDateTime startedBefore = referenceTime.minusMinutes(10);
  protected static final LocalDateTime startedAfter = referenceTime.plusMinutes(10);

  @BeforeAll
  protected static void setUp() {
    taskResultStartedBefore = createTaskResult(app1, startedBefore);
    taskResultStartedAfter = createTaskResult(app1, startedAfter);
  }

  protected static TaskResult createTaskResult(Application application, LocalDateTime startTime) {
    return new FakeTaskResult(application, "any", "any group",
        TaskResultType.SUCCESS, startTime, Duration.ofMinutes(1));
  }

}
