import filter.TaskNameFiltered;
import java.util.Iterator;
import mock.MockFakeTaskResult;
import mock.MockTasksResultsStorage;
import org.junit.jupiter.api.Test;
import storage.TasksResultsStorage;
import task.TaskResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
public class TaskNameFilteredTest {
  @Test
  public void testNameFiltering(){
    TasksResultsStorage tasksResultsStorage = new MockTasksResultsStorage();
    String desiredTaskName = "sun";
    tasksResultsStorage.addTaskResult(new MockFakeTaskResult(desiredTaskName));
    tasksResultsStorage.addTaskResult(new MockFakeTaskResult());
    tasksResultsStorage.addTaskResult(new MockFakeTaskResult(desiredTaskName));
    tasksResultsStorage.addTaskResult(new MockFakeTaskResult());
    int expectedFilteredSize = 2;

    Iterable<TaskResult> filteredTasksResults = new TaskNameFiltered(desiredTaskName, tasksResultsStorage);
    Iterator<TaskResult> iterator = filteredTasksResults.iterator();

    int counter = 0;
    while ( iterator.hasNext() ){
      ++counter;
      TaskResult taskResult = iterator.next();
      assertTrue(taskResult.getTaskName().equals(desiredTaskName), "taskName");
    }
    assertEquals(expectedFilteredSize, counter, "taskResult count");
  }
}
