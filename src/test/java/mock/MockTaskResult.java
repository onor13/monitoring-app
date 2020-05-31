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
    super( seqNumber, MockTaskResult.class.getSimpleName(), "dontNotCare" , null, TaskResultType.SUCCESS, creationTime, Duration.ofMinutes( (int)Math.random() ));
  }

}
