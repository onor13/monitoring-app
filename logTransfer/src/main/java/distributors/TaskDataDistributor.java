package distributors;

import com.google.common.flogger.FluentLogger;
import java.util.Collection;
import java.util.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import storage.TasksResultsStorage;
import task.TaskDataConnector;
import task.TaskResult;
import task.criteria.FilterCriteria;
import view.Presenter;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class TaskDataDistributor {
  @Autowired
  TaskDataConnector dataConnector;

  @Autowired
  TasksResultsStorage resultsStorage;

  @Autowired
  Presenter presenter;

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public TaskDataDistributor() {
    logger.atConfig().log("Bean " + TaskDataDistributor.class.getSimpleName() + " created");
  }

  public void setTasksDataConnector(TaskDataConnector dataConnector) {
    this.dataConnector = dataConnector;
  }

  public void setTasksResultsStorage(TasksResultsStorage storage) {
    resultsStorage = storage;
  }

  /***
   * <p>Retrieves tasksResults from the queue at regular time interval and sends them to storage
   * and UI if active.</p>
   */
  @Scheduled(fixedDelay = 1000)
  public void scheduleUpdate() {
    logger.atFine().log("scheduling update ");
    //List<TaskResult> l_taskResults =
    // StreamSupport.stream(dataConnector.pollNewData().spliterator(), false).collect( Collectors.toList() );
    //TODO use batch to add multiple tasks results at the same time to avoid creating extra queries
    Queue<TaskResult> tasksResults = dataConnector.pollNewData();
    while (!tasksResults.isEmpty()) {
      TaskResult tr = tasksResults.poll();
      logger.atFine().log("Adding taskResult " + tr.getTaskName());
      resultsStorage.addTaskResult(tr);
      if (dataConnector.isConnectedToUI()) {
        presenter.addTaskResult(tr);
      }

    }
  }

  public Iterable<TaskResult> getAllTasksResults() {
    return resultsStorage;
  }

  public Collection<TaskResult> getFilteredTasksResults(Collection<FilterCriteria> criteria) {
    return resultsStorage.filter(criteria);
  }

  public void disconnectFromUI() {
    dataConnector.disconnectFromUI();
  }

  public void enableUIupdates() {
    dataConnector.connectToUI();
  }
}
