package mock;

import fake.FakeApplication;
import fake.FakeTaskResult;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import task.TaskResultType;

@SuppressWarnings("PMD")
public class MockFakeTaskResult
    extends FakeTaskResult {

  private static AtomicInteger seqNumber = new AtomicInteger();

  public MockFakeTaskResult() {
    this(LocalDateTime.now());
  }

  public MockFakeTaskResult(LocalDateTime creationTime) {
    this("dontCare" + seqNumber.incrementAndGet(), creationTime, TaskResultType.SUCCESS);
  }

  public MockFakeTaskResult(String taskName) {
    this(taskName, LocalDateTime.now(), TaskResultType.SUCCESS);
  }

  public MockFakeTaskResult(TaskResultType taskResultType) {
    this("dontCare" + seqNumber.incrementAndGet(), LocalDateTime.now(), taskResultType);
  }

  public MockFakeTaskResult(String taskName, LocalDateTime creationTime, TaskResultType taskResultType) {
    //When we use JsonSerialization we never store millisenconds, so reset it here too
    super(new FakeApplication("doctor"), taskName, null, taskResultType,
        creationTime.withNano(0), Duration.ofMinutes((int) Math.random()));
  }

  public void setTaskGroup(String taskGroup) {
    this.taskGroup = taskGroup;
  }

  public void setApplicationName(String applicationName) {
    this.app = new FakeApplication(applicationName);
  }
}
