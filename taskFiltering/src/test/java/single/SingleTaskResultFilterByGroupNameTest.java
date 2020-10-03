package single;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fake.FakeApplication;
import fake.FakeTaskResult;
import filter.single.SingleTaskResultFilterByGroupName;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import task.Application;
import task.TaskResult;
import task.TaskResultType;

@SuppressWarnings({"PMD.JUnitTestContainsTooManyAsserts", "PMD.BeanMembersShouldSerialize"})
public class SingleTaskResultFilterByGroupNameTest {

  private static final String matchingGroupName = "groupName";
  private static final Application app1 = new FakeApplication("app1");
  private static TaskResult matchingTaskResult;

  private static TaskResult nonMatchingTaskResult;

  protected static TaskResult createTaskResult(Application application, String groupName) {
    return new FakeTaskResult(application, "any", groupName,
        TaskResultType.SUCCESS, LocalDateTime.now(), Duration.ofMinutes(1));
  }

  @BeforeAll
  protected static void setUp(){
    matchingTaskResult = createTaskResult(app1, matchingGroupName);
    nonMatchingTaskResult = createTaskResult(app1, "any");
  }

  @Test
  public void testFilterMatching(){
    SingleTaskResultFilterByGroupName filterByAppName = new SingleTaskResultFilterByGroupName();
    filterByAppName.setTaskGroupFilter(matchingGroupName);
    assertTrue(filterByAppName.isAccepted(matchingTaskResult), "filter should accept");
  }

  @Test
  public void testFilterNonMatching(){
    SingleTaskResultFilterByGroupName filter = new SingleTaskResultFilterByGroupName();
    filter.setTaskGroupFilter(matchingGroupName);
    assertFalse(filter.isAccepted(nonMatchingTaskResult), "filter should not accept");
  }

  @Test
  public void testFilterDefaultEmpty(){
    SingleTaskResultFilterByGroupName filter = new SingleTaskResultFilterByGroupName();
    assertTrue(filter.isEmptyFilter(), "filter was never set, so should be empty");
  }

  @Test
  public void testFilterNonEmpty(){
    SingleTaskResultFilterByGroupName filter = new SingleTaskResultFilterByGroupName();
    filter.setTaskGroupFilter(matchingGroupName);
    assertFalse(filter.isEmptyFilter(), "filter should not be empty");
  }

  @Test
  public void testFilterReset(){
    SingleTaskResultFilterByGroupName filter = new SingleTaskResultFilterByGroupName();
    filter.setTaskGroupFilter(matchingGroupName);
    assertFalse(filter.isEmptyFilter(), "filter should not be empty");
    filter.resetFilter();
    assertTrue(filter.isEmptyFilter(), "after reset filter should be empty");
  }
}
