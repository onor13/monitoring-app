package filters;

import mock.MockTaskResult;
import mock.MockTasksResultsStorage;
import org.junit.Assert;
import org.junit.Test;
import task.ITaskResult;
import task.TaskResultType;
import task.filters.TaskResultTypeFiltered;
import task.storage.ITasksResultsStorage;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

public class TaskResultTypeFilteredTest {
  @Test
  public void testResultTypeFiltering(){
    testResultTypeFiltering( TaskResultType.SUCCESS, TaskResultType.WARNING );
    testResultTypeFiltering( TaskResultType.ERROR, TaskResultType.WARNING );
  }

  private void testResultTypeFiltering( TaskResultType correctResultType, TaskResultType incorrectResultType ){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    ITasksResultsStorage tasksResultsStorage = new MockTasksResultsStorage();
    tasksResultsStorage.addTaskResult( new MockTaskResult( taskResultSequenceNumber.getAndIncrement(), correctResultType ) );
    tasksResultsStorage.addTaskResult( new MockTaskResult( taskResultSequenceNumber.getAndIncrement(), correctResultType ) );
    tasksResultsStorage.addTaskResult( new MockTaskResult( taskResultSequenceNumber.getAndIncrement(), incorrectResultType ) );
    tasksResultsStorage.addTaskResult( new MockTaskResult( taskResultSequenceNumber.getAndIncrement(), incorrectResultType ) );
    int expectedFilteredSize = 2;

    Iterable<ITaskResult> filteredTasksResults = new TaskResultTypeFiltered( correctResultType, tasksResultsStorage);
    Iterator<ITaskResult> iterator = filteredTasksResults.iterator();

    int counter = 0;
    while ( iterator.hasNext() ){
      ++counter;
      ITaskResult taskResult = iterator.next();
      Assert.assertTrue( "test taskName", taskResult.getTaskResultType() == correctResultType );
    }
    Assert.assertEquals( "taskName filtered size", expectedFilteredSize, counter );
  }
}
