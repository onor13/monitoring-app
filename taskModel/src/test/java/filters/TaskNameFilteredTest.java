package filters;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import mock.MockFakeTaskResult;
import mock.MockTasksResultsStorage;
import org.junit.Assert;
import org.junit.Test;
import storage.TasksResultsStorage;
import task.TaskResult;

@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
public class TaskNameFilteredTest {
  @Test
  public void testNameFiltering(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    TasksResultsStorage tasksResultsStorage = new MockTasksResultsStorage();
    String desiredTaskName = "sun";
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement(), desiredTaskName ) );
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement() ) );
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement(), desiredTaskName ) );
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement() ) );
    int expectedFilteredSize = 2;

    Iterable<TaskResult> filteredTasksResults = new TaskNameFiltered( desiredTaskName, tasksResultsStorage);
    Iterator<TaskResult> iterator = filteredTasksResults.iterator();

    int counter = 0;
    while ( iterator.hasNext() ){
      ++counter;
      TaskResult taskResult = iterator.next();
      Assert.assertTrue( "test taskName", taskResult.getTaskName().equals( desiredTaskName ) );
    }
    Assert.assertEquals( "taskName filtered size", expectedFilteredSize, counter );
  }
}
