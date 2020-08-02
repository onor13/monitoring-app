package javafx.controllers;

import com.google.common.flogger.FluentLogger;
import distributors.TaskDataDistributor;
import java.net.URL;
import java.util.ResourceBundle;
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
    tasksResultsController.addTaskResult(taskResult);
  }

  protected void reloadTasksResults() {
    tasksResultsController.reloadFrom(dataDistributor.getAllTasksResults());
  }
}
