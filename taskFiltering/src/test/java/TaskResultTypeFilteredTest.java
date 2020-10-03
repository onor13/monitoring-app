import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import filter.TaskResultTypeFiltered;
import java.util.Iterator;
import mock.MockFakeTaskResult;
import mock.MockTasksResultsStorage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import storage.TasksResultsStorage;
import task.TaskResult;
import task.TaskResultType;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.JUnitTestContainsTooManyAsserts"})
public class TaskResultTypeFilteredTest {

  @ParameterizedTest
  @EnumSource(value = TaskResultType.class, names = {"SUCCESS", "ERROR"})
  public void testResultTypeFiltering(TaskResultType resultType){
    TasksResultsStorage tasksResultsStorage = new MockTasksResultsStorage();
    tasksResultsStorage.addTaskResult(new MockFakeTaskResult(resultType));
    tasksResultsStorage.addTaskResult(new MockFakeTaskResult(resultType));
    TaskResultType incorrectResultType = getOpposite(resultType);
    tasksResultsStorage.addTaskResult(new MockFakeTaskResult(incorrectResultType));
    tasksResultsStorage.addTaskResult(new MockFakeTaskResult(incorrectResultType));
    int expectedFilteredSize = 2;

    Iterable<TaskResult> filteredTasksResults = new TaskResultTypeFiltered(resultType, tasksResultsStorage);
    Iterator<TaskResult> iterator = filteredTasksResults.iterator();

    int counter = 0;
    while (iterator.hasNext()){
      ++counter;
      TaskResult taskResult = iterator.next();
      assertSame(taskResult.getTaskResultType(), resultType, "taskResultType");
    }
    assertEquals(expectedFilteredSize, counter, "taskResult counter");
  }

  private TaskResultType getOpposite(TaskResultType taskResultType){
    return taskResultType == TaskResultType.SUCCESS ? TaskResultType.ERROR : TaskResultType.SUCCESS;
  }
}
