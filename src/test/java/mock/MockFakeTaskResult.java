package mock;

import fake.FakeTaskResult;
import task.TaskResultType;

import java.time.Duration;
import java.time.Instant;

public class MockFakeTaskResult
    extends FakeTaskResult {

  public MockFakeTaskResult( Long seqNumber ){
    this( seqNumber, Instant.now() );
  }

  public MockFakeTaskResult( Long seqNumber, Instant creationTime ){
    this( seqNumber, "dontCare" , creationTime, TaskResultType.SUCCESS);
  }

  public MockFakeTaskResult( Long seqNumber, String taskName ){
    this( seqNumber, taskName , Instant.now(), TaskResultType.SUCCESS);
  }

  public MockFakeTaskResult( Long seqNumber, TaskResultType taskResultType ){
    this( seqNumber, "dontCare" , Instant.now(), taskResultType );
  }

  public MockFakeTaskResult( Long seqNumber, String taskName, Instant creationTime ){
    this( seqNumber, taskName , creationTime, TaskResultType.SUCCESS);
  }

  public MockFakeTaskResult( Long seqNumber, String taskName, Instant creationTime, TaskResultType taskResultType ){
    super( seqNumber, MockFakeTaskResult.class.getSimpleName(), taskName , null, taskResultType, creationTime, Duration.ofMinutes( (int)Math.random() ));
  }

}
