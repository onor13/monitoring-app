package filters;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import mock.MockFakeTaskResult;
import mock.MockTasksResultsStorage;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import storage.TasksResultsStorage;
import task.TaskResult;
import task.TaskResultType;

public class TaskResultTypeFilteredTest {

  @ParameterizedTest
  @EnumSource(value = TaskResultType.class, names = {"SUCCESS, ERROR"})
  public void testResultTypeFiltering(TaskResultType resultType){
    AtomicLong taskResultSequenceNumber = new AtomicLong();
    TasksResultsStorage tasksResultsStorage = new MockTasksResultsStorage();
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult(taskResultSequenceNumber.getAndIncrement(), resultType) );
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult(taskResultSequenceNumber.getAndIncrement(), resultType) );
    TaskResultType incorrectResultType = getOpposite(resultType);
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult(taskResultSequenceNumber.getAndIncrement(), incorrectResultType) );
    tasksResultsStorage.addTaskResult( new MockFakeTaskResult(taskResultSequenceNumber.getAndIncrement(), incorrectResultType) );
    int expectedFilteredSize = 2;

    Iterable<TaskResult> filteredTasksResults = new TaskResultTypeFiltered( resultType, tasksResultsStorage);
    Iterator<TaskResult> iterator = filteredTasksResults.iterator();

    int counter = 0;
    while (iterator.hasNext()){
      ++counter;
      TaskResult taskResult = iterator.next();
      Assert.assertTrue( "test taskName", taskResult.getTaskResultType() == resultType );
    }
    Assert.assertEquals( "taskName filtered size", expectedFilteredSize, counter );
  }

  private TaskResultType getOpposite(TaskResultType taskResultType){
    return taskResultType == TaskResultType.SUCCESS ? TaskResultType.ERROR : TaskResultType.SUCCESS;
  }
}
