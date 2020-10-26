package javafx.controllers;

import com.google.common.flogger.FluentLogger;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.listeners.TaskFilterChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.view.RemoveFilterViewListener;
import javafx.view.TaskExecutionDurationTypeView;
import javafx.view.TaskFilterTypeView;
import javafx.view.TaskFilterTypeViewPane;
import org.springframework.stereotype.Component;
import task.TaskResultType;
import task.criteria.FilterCriteriaType;

@Component
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "unchecked"})
public class TasksFiltersController implements Initializable {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private Collection<TaskFilterChangeListener> changeListeners = new ArrayList<>();

  @FXML
  ListView<TaskFilterTypeViewPane> tasksFilters;

  private final RemoveFilterViewListener removeFilterViewListener = new RemoveFilterViewListener() {
    @Override
    public void onRemoveFilterView(FilterCriteriaType taskFilterType) {
      Optional<TaskFilterTypeViewPane> paneToRemove = tasksFilters.getItems().stream()
          .filter(pane -> pane.getFilterType() == taskFilterType).findFirst();
      if (paneToRemove.isPresent()) {
        removePane(paneToRemove.get());
      }
    }
  };

  private final TaskFilterTypeViewPane applicationNameFilterView = new ApplicationNameFilterView();
  private final TaskFilterTypeViewPane taskGroupFilterView = new TaskGroupFilterView();
  private final TaskFilterTypeViewPane taskResultTypeView  = new TaskResultTypeView();
  private final TaskExecutionDurationTypeView taskExecutionDurationBelowTypeView =
      new TaskExecutionDurationBelowTypeView();

  private final TaskExecutionDurationTypeView taskExecutionDurationAboveTypeView =
      new TaskExecutionDurationAboveTypeView();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    logger.atInfo().log("initialize " + TasksFiltersController.class.getSimpleName());
    tasksFilters.setCellFactory(param -> new TaskFilterCell());
  }

  public void addTaskFilterChangeListener(TaskFilterChangeListener taskFilterChangeListener) {
    changeListeners.add(taskFilterChangeListener);
    /*taskStartTimeTypeView.addChangeListener(new DateTimeChangeListener() {
      @Override
      public void onDateTimeChange(LocalDateTime dateTime) {
        taskFilterChangeListener.onTaskStartTimeFilterChange(dateTime);
      }
    });*/
  }

  /***
   * <p>adds UI for the specified filterType.</p>
   * @param filterType filterType for tasks filtering
   */
  public void addFilter(FilterCriteriaType filterType) {
    TaskFilterTypeViewPane pane;
    if (filterType == FilterCriteriaType.ApplicationName) {
      pane = applicationNameFilterView;
    } else if (filterType == FilterCriteriaType.TaskGroup) {
      pane = taskGroupFilterView;
    } else if (filterType == FilterCriteriaType.ResultType) {
      pane = taskResultTypeView;
    } else if (filterType == FilterCriteriaType.ExecutionDurationBelow) {
      pane = taskExecutionDurationBelowTypeView;
    } else if (filterType == FilterCriteriaType.ExecutionDurationAbove) {
      pane = taskExecutionDurationAboveTypeView;
    } else {
      showError("Not implemented yet", String.format("Filter %s is not supported", filterType.toString()));
      return;
    }
    if (tasksFilters.getItems().contains(pane)) {
      showError("Duplicate filter", String.format("Filter %s was already added", filterType.toString()));
    } else {
      tasksFilters.getItems().add(pane);
    }
  }

  protected void showError(String title, String errorMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setContentText(errorMessage);
    alert.show();
  }


  class TaskFilterCell extends ListCell<TaskFilterTypeViewPane> {
    public TaskFilterCell() {
      super();
    }

    @Override
    protected void updateItem(TaskFilterTypeViewPane item, boolean empty) {
      super.updateItem(item, empty);
      setText(null);
      setGraphic(null);

      if (item != null && !empty) {
        item.updateLabel();
        setGraphic(item.getView());
      }
    }
  }

  protected final void removePane(TaskFilterTypeViewPane pane) {
    pane.resetView();
    tasksFilters.getItems().remove(pane);
    changeListeners.stream().forEach(cl -> cl.onFilterRemove(pane.getFilterType()));
  }

  abstract class TaskTextFilterTypeView extends TaskFilterTypeView {
    final TextField filterValue = new TextField();

    protected TaskTextFilterTypeView() {
      super(removeFilterViewListener);
      filterValue.textProperty().addListener((observable, oldValue, newValue) -> {
        changeListeners.stream().forEach(cl -> {
          switch (getFilterType()) {
            case TaskGroup:
              cl.onTaskGroupFilterChange(newValue);
              break;
            case ApplicationName:
              cl.onApplicationNameFilterChange(newValue);
              break;
            default:
              break;
          }
        });
      });
      addExtraNode(filterValue);
    }

    @Override
    public void resetView() {
      filterValue.textProperty().setValue("");
    }
  }

  class ApplicationNameFilterView extends TaskTextFilterTypeView {
    @Override
    public FilterCriteriaType getFilterType() {
      return FilterCriteriaType.ApplicationName;
    }
  }

  class TaskGroupFilterView extends TaskTextFilterTypeView {
    @Override
    public FilterCriteriaType getFilterType() {
      return FilterCriteriaType.TaskGroup;
    }
  }

  class TaskResultTypeView extends TaskFilterTypeView {
    ChoiceBox<String> filterValue = new ChoiceBox<>();
    Map<Number, TaskResultType> taskResultTypeIndex = new HashMap<>();

    protected TaskResultTypeView() {
      super(removeFilterViewListener);
      taskResultTypeIndex.put(1, TaskResultType.SUCCESS);
      taskResultTypeIndex.put(2, TaskResultType.ERROR);
      taskResultTypeIndex.put(3, TaskResultType.WARNING);
      filterValue.getItems().add("Any");
      filterValue.getItems().addAll(taskResultTypeIndex.values().stream().map(
          taskResultType -> taskResultType.toString()).collect(Collectors.toList()));
      resetSelection();
      filterValue.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
          if (newValue != null) {
            TaskResultType taskResultType = taskResultTypeIndex.get(newValue);
            if (taskResultType != null) {
              changeListeners.stream().forEach(cl -> cl.onTaskResultTypeFilterChange(taskResultType));
            }
          }
        }
      });
      addExtraNode(filterValue);
    }

    @Override
    public FilterCriteriaType getFilterType() {
      return FilterCriteriaType.ResultType;
    }

    private final void resetSelection() {
      filterValue.getSelectionModel().select(0);
    }

    @Override
    public void resetView() {
      resetSelection();
    }
  }

  /*class TaskStartTimeTypeView extends TaskFilterTypeView {
    final DateTimePicker dateTimePicker = new DateTimePicker();

    protected TaskStartTimeTypeView() {
      super(removeFilterViewListener);
      addExtraNode(dateTimePicker);
    }

    public void addChangeListener(DateTimeChangeListener changeListener) {
      dateTimePicker.addChangeListener(changeListener);
    }

    @Override
    public FilterCriteriaType getFilterType() {
      return FilterCriteriaType.TaskStartTime;
    }

    @Override
    public void resetView() {
      dateTimePicker.getEditor().clear();
    }
  }*/


  class TaskExecutionDurationBelowTypeView extends TaskExecutionDurationTypeView {
    protected TaskExecutionDurationBelowTypeView() {
      super(removeFilterViewListener);
    }

    @Override
    public FilterCriteriaType getFilterType() {
      return FilterCriteriaType.ExecutionDurationBelow;
    }

    @Override
    protected void onValueChange(Duration duration) {
      changeListeners.stream().forEach(cl -> cl.onExecutionDurationBelowFilterChange(duration));
    }
  }

  class TaskExecutionDurationAboveTypeView extends TaskExecutionDurationTypeView {
    protected TaskExecutionDurationAboveTypeView() {
      super(removeFilterViewListener);
    }

    @Override
    public FilterCriteriaType getFilterType() {
      return FilterCriteriaType.ExecutionDurationAbove;
    }

    @Override
    protected void onValueChange(Duration duration) {
      changeListeners.stream().forEach(cl -> cl.onExecutionDurationAboveFilterChange(duration));
    }
  }

}
