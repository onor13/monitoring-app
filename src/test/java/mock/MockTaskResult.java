package mock;

import task.TaskResult;
import task.TaskResultType;

import java.time.Duration;
import java.time.Instant;

public class MockTaskResult
    extends TaskResult {

  public MockTaskResult( Long seqNumber ){
    this( seqNumber, Instant.now() );
  }

  public MockTaskResult( Long seqNumber, Instant creationTime ){
    this( seqNumber, "dontCare" , creationTime, TaskResultType.SUCCESS);
  }

  public MockTaskResult( Long seqNumber, String taskName ){
    this( seqNumber, taskName , Instant.now(), TaskResultType.SUCCESS);
  }

  public MockTaskResult( Long seqNumber, TaskResultType taskResultType ){
    this( seqNumber, "dontCare" , Instant.now(), taskResultType );
  }

  public MockTaskResult( Long seqNumber, String taskName, Instant creationTime ){
    this( seqNumber, taskName , creationTime, TaskResultType.SUCCESS);
  }

  public MockTaskResult( Long seqNumber, String taskName, Instant creationTime, TaskResultType taskResultType ){
    super( seqNumber, MockTaskResult.class.getSimpleName(), taskName , null, taskResultType, creationTime, Duration.ofMinutes( (int)Math.random() ));
  }

}
