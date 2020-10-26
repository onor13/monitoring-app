package javafx.controllers;

import com.google.common.flogger.FluentLogger;
import converters.LocalDateTimeConverter;
import distributors.TaskDataDistributor;
import filter.TaskResultFilteringSystem;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
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
import task.criteria.FilterCriteriaType;
import view.Presenter;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class MainController
    implements Initializable, Presenter {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private static final LocalDateTimeConverter formatter = new LocalDateTimeConverter();

  @FXML
  ToggleSwitch updatesOnOff;

  //For included controllers the name has to be fx:id from include followed by Controller
  //Example  <fx:include fx:id="tasksResults" -> taskResultsController
  @FXML
  TasksResultsViewController tasksResultsController;

  @FXML
  TasksFiltersController tasksFiltersController;

  @FXML
  ComboBox<FilterCriteriaType> filterTypeChoice;

  @Autowired
  TaskDataDistributor dataDistributor;

  @Autowired
  TaskResultFilteringSystem filteringSystem;

  ReadWriteLock lock = new ReentrantReadWriteLock();
  Lock writeLock = lock.writeLock();

  Set<TaskResult> alreadyAddedTaskResults = new HashSet<>();

  ObservableList<FilterCriteriaType> filterTypes = FXCollections.observableArrayList(
      FilterCriteriaType.ApplicationName,
      FilterCriteriaType.ResultType,
      FilterCriteriaType.TaskGroup,
      FilterCriteriaType.ExecutionDurationBelow,
      FilterCriteriaType.ExecutionDurationAbove);

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    filteringSystem.addChangeListener(changedCriteriaType -> reloadTasksResults());
    filterTypeChoice.setItems(filterTypes);
    tasksFiltersController.addTaskFilterChangeListener(new TaskFilterChangeListener() {
      @Override
      public void onApplicationNameFilterChange(String applicationName) {
        logger.atInfo().log("New applicationName filter %s", applicationName);
        filteringSystem.updateFilterByAppName(applicationName);
      }

      @Override
      public void onTaskGroupFilterChange(String taskGroup) {
        logger.atInfo().log("New taskGroup filter %s", taskGroup);
        filteringSystem.updateFilterByTaskGroupName(taskGroup);
      }

      @Override
      public void onTaskResultTypeFilterChange(TaskResultType taskResultType) {
        logger.atInfo().log("New taskResultType filter %s", taskResultType.toString());
        filteringSystem.updateFilterByResultType(taskResultType);
      }

      @Override
      public void onTaskStartTimeFilterChange(LocalDateTime dateTime) {
        logger.atInfo().log("New dateTime filter %s", formatter.format(dateTime));
        showNotImplementedAlert("Filter on taskStartTime");
      }

      @Override
      public void onExecutionDurationBelowFilterChange(Duration duration) {
        logger.atInfo().log("New ExecutionDuration Below filter %s", duration.toString());
        filteringSystem.updateFilterByExecutionDurationBelow(duration);
      }

      @Override
      public void onExecutionDurationAboveFilterChange(Duration duration) {
        logger.atInfo().log("New ExecutionDuration Above filter %s", duration.toString());
        filteringSystem.updateFilterByExecutionDurationAbove(duration);
      }

      @Override
      public void onFilterRemove(FilterCriteriaType taskFilterType) {
        filterTypeChoice.getItems().add(taskFilterType);
        resetSelectionOnFilterTypes();
        removeFilter(taskFilterType);
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

  private void removeFilter(FilterCriteriaType taskFilterType) {
    if (taskFilterType == FilterCriteriaType.ApplicationName) {
      filteringSystem.removeApplicationNameFilter();
    } else if (taskFilterType == FilterCriteriaType.ResultType) {
      filteringSystem.removeTaskResultTypeFilter();
    } else if (taskFilterType == FilterCriteriaType.TaskGroup) {
      filteringSystem.removeTaskGroupNameFilter();
    } else if (taskFilterType == FilterCriteriaType.ExecutionDurationBelow) {
      filteringSystem.removeTaskExecutionDurationBelowFilter();
    } else if (taskFilterType == FilterCriteriaType.ExecutionDurationAbove) {
      filteringSystem.removeTaskExecutionDurationAboveFilter();
    } else {
      showNotImplementedAlert(String.format("removing filter %s is not supported"));
    }
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
      dataDistributor.getFilteredTasksResults(filteringSystem.getFilterCriteria())
          .forEach(tr -> alreadyAddedTaskResults.add(tr));
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
        if (filteringSystem.isAcceptedByAllFilters(taskResult)) {
          tasksResultsController.addTaskResult(taskResult);
        }
      }
    } finally {
      writeLock.unlock();
    }
  }

  @FXML
  protected void handleAddFilterAction(ActionEvent event) {
    logger.atInfo().log("Add filter button clicked " + event.getSource());
    FilterCriteriaType selectedFilterType = filterTypeChoice.getValue();
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
