package filters;

import mock.MockTaskResult;
import mock.MockTasksResultsStorage;
import org.junit.Assert;
import org.junit.Test;
import task.ITaskResult;
import task.filters.TaskNameFiltered;
import task.storage.ITasksResultsStorage;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

public class TaskNameFilteredTest {
  @Test
  public void testNameFiltering(){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    ITasksResultsStorage tasksResultsStorage = new MockTasksResultsStorage();
    String desiredTaskName = "sun";
    tasksResultsStorage.addTaskResult( new MockTaskResult( taskResultSequenceNumber.getAndIncrement(), desiredTaskName ) );
    tasksResultsStorage.addTaskResult( new MockTaskResult( taskResultSequenceNumber.getAndIncrement() ) );
    tasksResultsStorage.addTaskResult( new MockTaskResult( taskResultSequenceNumber.getAndIncrement(), desiredTaskName ) );
    tasksResultsStorage.addTaskResult( new MockTaskResult( taskResultSequenceNumber.getAndIncrement() ) );
    int expectedFilteredSize = 2;

    Iterable<ITaskResult> filteredTasksResults = new TaskNameFiltered( desiredTaskName, tasksResultsStorage);
    Iterator<ITaskResult> iterator = filteredTasksResults.iterator();

    int counter = 0;
    while ( iterator.hasNext() ){
      ++counter;
      ITaskResult taskResult = iterator.next();
      Assert.assertTrue( "test taskName", taskResult.getTaskName().equals( desiredTaskName ) );
    }
    Assert.assertEquals( "taskName filtered size", expectedFilteredSize, counter );
  }
}
