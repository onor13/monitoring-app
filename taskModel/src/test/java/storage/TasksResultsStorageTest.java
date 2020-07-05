package storage;

import mock.MockFakeTaskResult;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;
import task.TaskResult;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class TasksResultsStorageTest {

  protected abstract TasksResultsStorage createStorage();

  @Test
  public void testAddOperation(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    TasksResultsStorage tasksResultsStorage = createStorage();
    MatcherAssert.assertThat(
        t -> {
          TaskResult taskResult = new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement() );
          tasksResultsStorage.addTaskResult( taskResult );
          return tasksResultsStorage.contains( taskResult );
        },
        new RunsInThreads<>(new AtomicInteger(), 10)
    );
  }

  @Test
  public void testRemoveOperation(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    TasksResultsStorage tasksResultsStorage = createStorage();
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement() ) );
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement() ) );
    Assert.assertEquals( "Initial size", tasksResultsStorage.size(), 2 );

    tasksResultsStorage.removeAll();
    Assert.assertEquals( "Should be empty after remove", tasksResultsStorage.size(), 0 );
  }

  @Test
  public void testContainsOperation(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    TasksResultsStorage tasksResultsStorage = createStorage();
    TaskResult taskResult1 = new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement() );
    tasksResultsStorage.addTaskResult( taskResult1  );

    TaskResult taskResult2 = new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement() );
    Assert.assertFalse( "contains test before adding", tasksResultsStorage.contains( taskResult2 )  );
    tasksResultsStorage.addTaskResult( taskResult2  );
    Assert.assertTrue( "contains test after adding", tasksResultsStorage.contains( taskResult2 )  );
  }

  @Test
  public void testRemoveTaskResultsOlderThan(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    TasksResultsStorage tasksResultsStorage = createStorage();

    LocalDateTime instantT1 = LocalDateTime.now();
    LocalDateTime instantT2 = instantT1.plusSeconds( 1 );
    LocalDateTime instantT3 = instantT1.plusSeconds( 2 );
    LocalDateTime instantT4 = instantT1.plusSeconds( 3 );

    TaskResult taskResult1 = new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement(), instantT1 );
    TaskResult taskResult2 = new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement(), instantT2 );
    TaskResult taskResult3 = new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement(), instantT3 );
    TaskResult taskResult4 = new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement(), instantT4 );
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
