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
import javafx.TaskFilterType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.listeners.TaskFilterChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import org.controlsfx.control.ToggleSwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.TaskResult;
import task.TaskResultType;
import view.Presenter;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class MainController
    implements Initializable, Presenter {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @FXML
  ToggleSwitch updatesOnOff;

  //For included controllers the name has to be fx:id from include followed by Controller
  //Example  <fx:include fx:id="tasksResults" -> taskResultsController
  @FXML
  TasksResultsViewController tasksResultsController;

  @FXML
  TasksFiltersController tasksFiltersController;

  @FXML
  ComboBox<TaskFilterType> filterTypeChoice;

  @Autowired
  TaskDataDistributor dataDistributor;

  ReadWriteLock lock = new ReentrantReadWriteLock();
  Lock writeLock = lock.writeLock();

  Set<TaskResult> alreadyAddedTaskResults = new HashSet<>();

  ObservableList<TaskFilterType> filterTypes = FXCollections.observableArrayList(
      TaskFilterType.ApplicationName, TaskFilterType.ResultType, TaskFilterType.TaskGroup);

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    filterTypeChoice.setItems(filterTypes);
    tasksFiltersController.addTaskFilterChangeListener(new TaskFilterChangeListener() {
      @Override
      public void onApplicationNameFilterChange(String applicationName) {
        showNotImplementedAlert("Filter on applicationName");
      }

      @Override
      public void onTaskGroupFilterChange(String taskGroupF) {
        showNotImplementedAlert("Filter on taskGroup");
      }

      @Override
      public void onTaskResultTypeFilterChange(TaskResultType taskResultType) {
        showNotImplementedAlert("Filter on taskResultType");
      }

      @Override
      public void onFilterRemove(TaskFilterType taskFilterType) {
        filterTypeChoice.getItems().add(taskFilterType);
        resetSelectionOnFilterTypes();
      }
    });
    resetSelectionOnFilterTypes();
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

  private void showNotImplementedAlert(String feature) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Not implemented yet");
    alert.setContentText(String.format("Feature %s is not supported", feature));
    alert.show();
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

  @FXML
  protected void handleAddFilterAction(ActionEvent event) {
    logger.atInfo().log("Add filter button clicked " + event.getSource());
    TaskFilterType selectedFilterType = filterTypeChoice.getValue();
    tasksFiltersController.addFilter(selectedFilterType);
    filterTypes.remove(selectedFilterType);
    resetSelectionOnFilterTypes();
  }

  private void resetSelectionOnFilterTypes() {
    if (filterTypes.size() > 0) {
      filterTypeChoice.getSelectionModel().select(0);
    }
  }

}
