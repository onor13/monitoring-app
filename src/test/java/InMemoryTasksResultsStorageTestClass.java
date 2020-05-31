
import org.hamcrest.MatcherAssert;
import org.junit.*;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.llorllale.cactoos.matchers.RunsInThreads;
import task.ITaskResult;
import task.TaskResult;
import task.TaskResultType;
import task.storage.*;

public class InMemoryTasksResultsStorageTestClass {

  final String applicationId = "Test" + InMemoryTasksResultsStorageTestClass.class.getSimpleName();

  @Test
  public void testAddOperation(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    InMemoryTasksResultStorage tasksResultsStorage = new InMemoryTasksResultStorage();
    MatcherAssert.assertThat(
        t -> {
          ITaskResult taskResult = generateTaskResult( taskResultSequenceNumber.getAndIncrement() );
          tasksResultsStorage.addTaskResult( taskResult );
          return tasksResultsStorage.contains( taskResult );
        },
        new RunsInThreads<>(new AtomicInteger(), 10)
    );
  }

  @Test
  public void testRemoveOperation(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    InMemoryTasksResultStorage tasksResultsStorage = new InMemoryTasksResultStorage();
    tasksResultsStorage.addTaskResult( generateTaskResult( taskResultSequenceNumber.getAndIncrement() ) );
    tasksResultsStorage.addTaskResult( generateTaskResult( taskResultSequenceNumber.getAndIncrement() ) );
    Assert.assertEquals( "Initial size", tasksResultsStorage.getAllTasksResults().size(), 2 );

    tasksResultsStorage.removeAllTaskResults();
    Assert.assertEquals( "Should be empty after remove", tasksResultsStorage.getAllTasksResults().size(), 0 );
  }

  @Test
  public void testContainsOperation(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    InMemoryTasksResultStorage tasksResultsStorage = new InMemoryTasksResultStorage();
    ITaskResult taskResult1 = generateTaskResult( taskResultSequenceNumber.getAndIncrement() );
    tasksResultsStorage.addTaskResult( taskResult1  );

    ITaskResult taskResult2 = generateTaskResult( taskResultSequenceNumber.getAndIncrement() );
    Assert.assertFalse( "contains test before adding", tasksResultsStorage.contains( taskResult2 )  );
    tasksResultsStorage.addTaskResult( taskResult2  );
    Assert.assertTrue( "contains test after adding", tasksResultsStorage.contains( taskResult2 )  );
  }

  @Test
  public void testRemoveTaskResultsOlderThan(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    InMemoryTasksResultStorage tasksResultsStorage = new InMemoryTasksResultStorage();

    Instant instantT1 = Instant.now();
    Instant instantT2 = instantT1.plusSeconds( 1 );
    Instant instantT3 = instantT1.plusSeconds( 2 );
    Instant instantT4 = instantT1.plusSeconds( 3 );

    ITaskResult taskResult1 = generateTaskResult( taskResultSequenceNumber.getAndIncrement(), instantT1 );
    ITaskResult taskResult2 = generateTaskResult( taskResultSequenceNumber.getAndIncrement(), instantT2 );
    ITaskResult taskResult3 = generateTaskResult( taskResultSequenceNumber.getAndIncrement(), instantT3 );
    ITaskResult taskResult4 = generateTaskResult( taskResultSequenceNumber.getAndIncrement(), instantT4 );
    tasksResultsStorage.addTaskResult( taskResult1 );
    tasksResultsStorage.addTaskResult( taskResult2 );
    tasksResultsStorage.addTaskResult( taskResult3 );
    tasksResultsStorage.addTaskResult( taskResult4 );

    tasksResultsStorage.removeTaskResultsOlderThan( instantT3 );

    Assert.assertFalse( tasksResultsStorage.contains( taskResult1 ) );
    Assert.assertFalse( tasksResultsStorage.contains( taskResult2 ) );
    Assert.assertTrue( tasksResultsStorage.contains( taskResult3 ) );
    Assert.assertTrue( tasksResultsStorage.contains( taskResult4 ) );

  }

  protected ITaskResult generateTaskResult( Long seqNumber, Instant instant ){
    return new TaskResult( seqNumber, applicationId, "dontNotCare" , null, TaskResultType.SUCCESS, instant, Duration.ofMinutes( (int)Math.random() ) );
  }

  protected ITaskResult generateTaskResult( Long seqNumber ){
    return generateTaskResult( seqNumber, Instant.now() );
  }
}
