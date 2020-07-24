package task;

import com.google.common.flogger.FluentLogger;
import javafx.controllers.MainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import storage.TasksResultsStorage;

import java.util.Queue;

@Component
public class TaskDataDistributor {
  @Autowired
  TaskDataConnector dataConnector;

  @Autowired
  TasksResultsStorage resultsStorage;

  @Autowired
  MainController uiController;

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  public TaskDataDistributor() {
    logger.atConfig().log("Bean " + TaskDataDistributor.class.getSimpleName() + " created");
  }

  public void setDataConnector(TaskDataConnector aDataConnector) {
    dataConnector = aDataConnector;
  }

  public void setResultsStorage(TasksResultsStorage aResultsStorage) {
    resultsStorage = aResultsStorage;
  }

  //@Async //TODO make it async
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
        uiController.addTaskResult(tr);
      }

    }
  }

  public Iterable<TaskResult> getAllTasksResults() {
    return this.resultsStorage;
  }

  public void disconnectFromUI() {
    dataConnector.disconnectFromUI();
  }

  public void enableUIupdates() {
    dataConnector.connectToUI();
  }
}
