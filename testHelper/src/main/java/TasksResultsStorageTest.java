import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import mock.MockFakeTaskResult;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;
import storage.TasksResultsStorage;
import task.TaskResult;
import task.criteria.ApplicationNameFilterCriteria;
import task.criteria.FilterCriteria;
import task.criteria.TaskGroupNameFilterCriteria;

@SuppressWarnings("PMD")
public abstract class TasksResultsStorageTest {

  protected abstract TasksResultsStorage createStorage();

  @Test
  public void testAddOperation() {
    TasksResultsStorage tasksResultsStorage = createStorage();
    MatcherAssert.assertThat(t -> {
      TaskResult taskResult = new MockFakeTaskResult();
      tasksResultsStorage.addTaskResult(taskResult);
      return tasksResultsStorage.contains(taskResult); }, new RunsInThreads<>(new AtomicInteger(), 10)
    );
  }

  @Test
  public void testDuplicateAddOperation() {
    TasksResultsStorage tasksResultsStorage = createStorage();
    TaskResult taskResult = new MockFakeTaskResult();
    tasksResultsStorage.addTaskResult(taskResult);
    assertTrue(tasksResultsStorage.contains(taskResult), "contains");
    long sizeBeforeAddingDuplicate = tasksResultsStorage.size();

    //Add the same again
    tasksResultsStorage.addTaskResult(taskResult);
    long sizeAfterAddingDuplicate = tasksResultsStorage.size();
    assertEquals(sizeBeforeAddingDuplicate, sizeAfterAddingDuplicate, "size after adding duplicate");
  }

  @Test
  public void testRemoveOperation() {
    TasksResultsStorage tasksResultsStorage = createStorage();
    tasksResultsStorage.addTaskResult(new MockFakeTaskResult());
    tasksResultsStorage.addTaskResult(new MockFakeTaskResult());

    tasksResultsStorage.removeAll();
    assertEquals(tasksResultsStorage.size(), 0, "empty after remove");
  }

  @Test
  public void testContainsOperation() {
    TasksResultsStorage tasksResultsStorage = createStorage();
    TaskResult taskResult1 = new MockFakeTaskResult();
    tasksResultsStorage.addTaskResult(taskResult1);

    TaskResult taskResult2 = new MockFakeTaskResult();
    assertFalse(tasksResultsStorage.contains(taskResult2), "contains");
    tasksResultsStorage.addTaskResult(taskResult2);
    assertTrue(tasksResultsStorage.contains(taskResult2), "contains");
  }

  @Test
  public void testRemoveTaskResultsOlderThan() {
    TasksResultsStorage tasksResultsStorage = createStorage();

    LocalDateTime instantT1 = LocalDateTime.now().withNano(0);
    LocalDateTime instantT2 = instantT1.plusSeconds(1);
    LocalDateTime instantT3 = instantT1.plusSeconds(2);
    LocalDateTime instantT4 = instantT1.plusSeconds(3);

    TaskResult taskResult1 = new MockFakeTaskResult(instantT1);
    TaskResult taskResult2 = new MockFakeTaskResult(instantT2);
    TaskResult taskResult3 = new MockFakeTaskResult(instantT3);
    TaskResult taskResult4 = new MockFakeTaskResult(instantT4);
    tasksResultsStorage.addTaskResult(taskResult1);
    tasksResultsStorage.addTaskResult(taskResult2);
    tasksResultsStorage.addTaskResult(taskResult3);
    tasksResultsStorage.addTaskResult(taskResult4);

    tasksResultsStorage.removeOlderThan(instantT3);

    assertFalse(tasksResultsStorage.contains(taskResult1), "contains");
    assertFalse(tasksResultsStorage.contains(taskResult2), "contains");
    assertTrue(tasksResultsStorage.contains(taskResult3), "contains");
    assertTrue(tasksResultsStorage.contains(taskResult4), "contains");
  }

  @Test
  public void testAcceptedSingleFilterCriteria() {
    TasksResultsStorage tasksResultsStorage = createStorage();
    final String expectedTaskGroup = "expectedTaskGroupWow";
    MockFakeTaskResult expectedTaskResult = new MockFakeTaskResult("");
    expectedTaskResult.setTaskGroup(expectedTaskGroup);
    tasksResultsStorage.addTaskResult(expectedTaskResult);
    Collection<FilterCriteria> criteria = new ArrayList<>();
    criteria.add(new TaskGroupNameFilterCriteria(expectedTaskGroup));
    Collection<TaskResult> filteredTaskResults = tasksResultsStorage.filter(criteria);
    assertEquals(filteredTaskResults.size(), 1, "filtered by taskGroupName size");
    assertEquals(expectedTaskGroup, filteredTaskResults.iterator().next().getTaskGroup(), "filter by taskGroupName");
  }

  @Test
  public void testAcceptedRejectedFilterCriteria() {
    TasksResultsStorage tasksResultsStorage = createStorage();
    final String unexpectedTaskGroup = "should not exist";
    Collection<FilterCriteria> criteria = new ArrayList<>();
    criteria.add(new TaskGroupNameFilterCriteria(unexpectedTaskGroup));
    Collection<TaskResult> filteredTaskResults = tasksResultsStorage.filter(criteria);
    assertEquals(filteredTaskResults.size(), 0, "filtered by taskGroupName size");
  }

  @Test
  public void testAcceptedMultipleFilterCriteria() {
    TasksResultsStorage tasksResultsStorage = createStorage();
    final String expectedTaskGroup = "expectedTaskGroupWow";
    final String expectedApplicationName = "expectAppNameWow";
    MockFakeTaskResult expectedTaskResult = new MockFakeTaskResult("");
    expectedTaskResult.setTaskGroup(expectedTaskGroup);
    expectedTaskResult.setApplicationName(expectedApplicationName);
    tasksResultsStorage.addTaskResult(expectedTaskResult);

    Collection<FilterCriteria> criteria = new ArrayList<>();
    criteria.add(new TaskGroupNameFilterCriteria(expectedTaskGroup));
    criteria.add(new ApplicationNameFilterCriteria(expectedApplicationName));
    Collection<TaskResult> filteredTaskResults = tasksResultsStorage.filter(criteria);
    assertEquals(filteredTaskResults.size(), 1,
        "filtered by taskGroupName and applicationName size");
    assertEquals(expectedApplicationName, filteredTaskResults.iterator().next().getApplicationName(),
        "filter by applicationName");
    assertEquals(expectedTaskGroup, filteredTaskResults.iterator().next().getTaskGroup(),
        "filter by taskGroupName");
  }

  @Test
  public void testRejectedMultipleFilterCriteria() {
    TasksResultsStorage tasksResultsStorage = createStorage();
    final String expectedTaskGroup = "expectedTaskGroupWow";
    final String applicationName = "appName";
    MockFakeTaskResult expectedTaskResult = new MockFakeTaskResult("");
    expectedTaskResult.setTaskGroup(expectedTaskGroup);
    expectedTaskResult.setApplicationName(applicationName);
    tasksResultsStorage.addTaskResult(expectedTaskResult);

    Collection<FilterCriteria> criteria = new ArrayList<>();
    criteria.add(new TaskGroupNameFilterCriteria(expectedTaskGroup));
    criteria.add(new ApplicationNameFilterCriteria("should not match anything"));
    Collection<TaskResult> filteredTaskResults = tasksResultsStorage.filter(criteria);
    assertEquals(filteredTaskResults.size(), 0,
        "filtered by taskGroupName and applicationName size");
  }

}
