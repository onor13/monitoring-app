package javafx.controllers;

import com.google.common.flogger.FluentLogger;
import distributors.TaskDataDistributor;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.ToggleSwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.TaskResult;
import view.Presenter;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class MainController
    implements Initializable, Presenter {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @FXML
  ToggleSwitch updatesOnOff;

  @FXML
  TasksResultsViewController tasksResultsController;

  @Autowired
  TaskDataDistributor dataDistributor;

  ReadWriteLock lock = new ReentrantReadWriteLock();
  Lock writeLock = lock.writeLock();

  Set<TaskResult> alreadyAddedTaskResults = new HashSet<>();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    updatesOnOff.setSelected(true);
    updatesOnOff.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == Boolean.TRUE) {
        if (oldValue == Boolean.FALSE) {
          reloadTasksResults();
          logger.atInfo().log("UI updates are now enabled");
          dataDistributor.enableUIupdates();
        }
      } else {
        logger.atInfo().log("UI updates are now disabled");
        dataDistributor.disconnectFromUI();
      }
    });
  }

  @Override
  public void addTaskResult(TaskResult taskResult) {
    try {
      writeLock.lock();
      if (!alreadyAddedTaskResults.contains(taskResult)) {
        alreadyAddedTaskResults.add(taskResult);
        tasksResultsController.addTaskResult(taskResult);
      }
    } finally {
      writeLock.unlock();
    }
  }

  protected void reloadTasksResults() {
    try {
      writeLock.lock();
      alreadyAddedTaskResults.clear();
      dataDistributor.getAllTasksResults().forEach(tr -> alreadyAddedTaskResults.add(tr));
      tasksResultsController.reloadFrom(alreadyAddedTaskResults);
    } finally {
      writeLock.unlock();
    }
  }
}
