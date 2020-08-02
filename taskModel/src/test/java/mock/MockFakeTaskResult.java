package mock;

import fake.FakeApplication;
import fake.FakeTaskResult;
import task.Application;
import task.TaskResultType;

import java.time.Duration;
import java.time.LocalDateTime;

@SuppressWarnings("PMD")
public class MockFakeTaskResult
    extends FakeTaskResult {

  private static Application app = new FakeApplication();

  public MockFakeTaskResult( Long seqNumber ){
    this( seqNumber, LocalDateTime.now());
  }

  public MockFakeTaskResult( Long seqNumber, LocalDateTime creationTime ){
    this( seqNumber, "dontCare" + seqNumber , creationTime, TaskResultType.SUCCESS);
  }

  public MockFakeTaskResult( Long seqNumber, String taskName ){
    this( seqNumber, taskName , LocalDateTime.now(), TaskResultType.SUCCESS);
  }

  public MockFakeTaskResult( Long seqNumber, TaskResultType taskResultType ){
    this( seqNumber, "dontCare" + seqNumber , LocalDateTime.now(), taskResultType );
  }

  public MockFakeTaskResult( Long seqNumber, String taskName, LocalDateTime creationTime ){
    this( seqNumber, taskName , creationTime, TaskResultType.SUCCESS);
  }

  public MockFakeTaskResult( Long seqNumber, String taskName, LocalDateTime creationTime, TaskResultType taskResultType ){
    //When we use JsonSerialization we never store millisenconds, so reset it here too
    super( app, taskName , null, taskResultType, creationTime.withNano(0), Duration.ofMinutes( (int)Math.random() ));
  }
}
