package storage;

import mock.MockTaskResult;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;
import task.ITaskResult;
import task.storage.ITasksResultsStorage;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class TasksResultsStorageTest {

  protected abstract ITasksResultsStorage createStorage();

  @Test
  public void testAddOperation(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    ITasksResultsStorage tasksResultsStorage = createStorage();
    MatcherAssert.assertThat(
        t -> {
          ITaskResult taskResult = new MockTaskResult( taskResultSequenceNumber.getAndIncrement() );
          tasksResultsStorage.addTaskResult( taskResult );
          return tasksResultsStorage.contains( taskResult );
        },
        new RunsInThreads<>(new AtomicInteger(), 10)
    );
  }

  @Test
  public void testRemoveOperation(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    ITasksResultsStorage tasksResultsStorage = createStorage();
    tasksResultsStorage.addTaskResult( new MockTaskResult( taskResultSequenceNumber.getAndIncrement() ) );
    tasksResultsStorage.addTaskResult( new MockTaskResult( taskResultSequenceNumber.getAndIncrement() ) );
    Assert.assertEquals( "Initial size", tasksResultsStorage.size(), 2 );

    tasksResultsStorage.removeAll();
    Assert.assertEquals( "Should be empty after remove", tasksResultsStorage.size(), 0 );
  }

  @Test
  public void testContainsOperation(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    ITasksResultsStorage tasksResultsStorage = createStorage();
    ITaskResult taskResult1 = new MockTaskResult( taskResultSequenceNumber.getAndIncrement() );
    tasksResultsStorage.addTaskResult( taskResult1  );

    ITaskResult taskResult2 = new MockTaskResult( taskResultSequenceNumber.getAndIncrement() );
    Assert.assertFalse( "contains test before adding", tasksResultsStorage.contains( taskResult2 )  );
    tasksResultsStorage.addTaskResult( taskResult2  );
    Assert.assertTrue( "contains test after adding", tasksResultsStorage.contains( taskResult2 )  );
  }

  @Test
  public void testRemoveTaskResultsOlderThan(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    ITasksResultsStorage tasksResultsStorage = createStorage();

    Instant instantT1 = Instant.now();
    Instant instantT2 = instantT1.plusSeconds( 1 );
    Instant instantT3 = instantT1.plusSeconds( 2 );
    Instant instantT4 = instantT1.plusSeconds( 3 );

    ITaskResult taskResult1 = new MockTaskResult( taskResultSequenceNumber.getAndIncrement(), instantT1 );
    ITaskResult taskResult2 = new MockTaskResult( taskResultSequenceNumber.getAndIncrement(), instantT2 );
    ITaskResult taskResult3 = new MockTaskResult( taskResultSequenceNumber.getAndIncrement(), instantT3 );
    ITaskResult taskResult4 = new MockTaskResult( taskResultSequenceNumber.getAndIncrement(), instantT4 );
    tasksResultsStorage.addTaskResult( taskResult1 );
    tasksResultsStorage.addTaskResult( taskResult2 );
    tasksResultsStorage.addTaskResult( taskResult3 );
    tasksResultsStorage.addTaskResult( taskResult4 );

    tasksResultsStorage.removeOlderThan( instantT3 );

    Assert.assertFalse( tasksResultsStorage.contains( taskResult1 ) );
    Assert.assertFalse( tasksResultsStorage.contains( taskResult2 ) );
    Assert.assertTrue( tasksResultsStorage.contains( taskResult3 ) );
    Assert.assertTrue( tasksResultsStorage.contains( taskResult4 ) );

  }

}
