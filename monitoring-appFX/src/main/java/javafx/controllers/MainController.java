package javafx.controllers;

import com.google.common.flogger.FluentLogger;
import distributors.TaskDataDistributor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
  TaskResultsTableController tableViewController;

  @Autowired
  TaskDataDistributor dataDistributor;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    updatesOnOff.setStyle("-fx-font-weight: bold;");
    updatesOnOff.setSelected(true);
    updatesOnOff.selectedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
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
      }
    });
  }

  @Override
  public void addTaskResult(TaskResult taskResult) {
    tableViewController.addTaskResult(taskResult);
  }

  protected void reloadTasksResults() {
    tableViewController.reloadFrom(dataDistributor.getAllTasksResults());
  }
}
