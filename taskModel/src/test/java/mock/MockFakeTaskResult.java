package mock;

import fake.FakeApplication;
import fake.FakeTaskResult;
import task.Application;
import task.TaskResultType;

import java.time.Duration;
import java.time.LocalDateTime;

public class MockFakeTaskResult
    extends FakeTaskResult {

  private static Application app = new FakeApplication();

  public MockFakeTaskResult( Long seqNumber ){
    this( seqNumber, LocalDateTime.now() );
  }

  public MockFakeTaskResult( Long seqNumber, LocalDateTime creationTime ){
    this( seqNumber, "dontCare" , creationTime, TaskResultType.SUCCESS);
  }

  public MockFakeTaskResult( Long seqNumber, String taskName ){
    this( seqNumber, taskName , LocalDateTime.now(), TaskResultType.SUCCESS);
  }

  public MockFakeTaskResult( Long seqNumber, TaskResultType taskResultType ){
    this( seqNumber, "dontCare" , LocalDateTime.now(), taskResultType );
  }

  public MockFakeTaskResult( Long seqNumber, String taskName, LocalDateTime creationTime ){
    this( seqNumber, taskName , creationTime, TaskResultType.SUCCESS);
  }

  public MockFakeTaskResult( Long seqNumber, String taskName, LocalDateTime creationTime, TaskResultType taskResultType ){
    super( app, taskName , null, taskResultType, creationTime, Duration.ofMinutes( (int)Math.random() ));
  }

}
