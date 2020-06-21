package filters;

import mock.MockFakeTaskResult;
import mock.MockTasksResultsStorage;
import org.junit.Assert;
import org.junit.Test;
import task.TaskResult;
import task.TaskResultType;
import task.filters.TaskResultTypeFiltered;
import task.storage.TasksResultsStorage;

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
    TasksResultsStorage tasksResultsStorage = new MockTasksResultsStorage();
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement(), correctResultType ) );
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement(), correctResultType ) );
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement(), incorrectResultType ) );
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult( taskResultSequenceNumber.getAndIncrement(), incorrectResultType ) );
    int expectedFilteredSize = 2;

    Iterable<TaskResult> filteredTasksResults = new TaskResultTypeFiltered( correctResultType, tasksResultsStorage);
    Iterator<TaskResult> iterator = filteredTasksResults.iterator();

    int counter = 0;
    while ( iterator.hasNext() ){
      ++counter;
      TaskResult taskResult = iterator.next();
      Assert.assertTrue( "test taskName", taskResult.getTaskResultType() == correctResultType );
    }
    Assert.assertEquals( "taskName filtered size", expectedFilteredSize, counter );
  }
}
