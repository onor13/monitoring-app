package javafx.controllers;

import com.google.common.flogger.FluentLogger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.TaskFilterType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.listeners.TaskFilterChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;
import task.TaskResultType;

@Component
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "unchecked"})
public class TasksFiltersController implements Initializable {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private Collection<TaskFilterChangeListener> changeListeners = new ArrayList<>();

  @FXML
  ListView<TaskFilterTypeViewPane> tasksFilters;

  private final TaskFilterTypeViewPane applicationNameFilterView = new ApplicationNameFilterView();
  private final TaskFilterTypeViewPane taskGroupFilterView = new TaskGroupFilterView();
  private final TaskFilterTypeViewPane taskResultTypeView  = new TaskResultTypeView();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    logger.atInfo().log("initialize " + TasksFiltersController.class.getSimpleName());
    tasksFilters.setCellFactory(param -> new TaskFilterCell());
  }

  public void addTaskFilterChangeListener(TaskFilterChangeListener taskFilterChangeListener) {
    changeListeners.add(taskFilterChangeListener);
  }

  /***
   * <p>adds UI for the specified filterType.</p>
   * @param filterType filterType for tasks filtering
   */
  public void addFilter(TaskFilterType filterType) {
    TaskFilterTypeViewPane pane;
    if (filterType == TaskFilterType.ApplicationName) {
      pane = applicationNameFilterView;
    } else if (filterType == TaskFilterType.TaskGroup) {
      pane = taskGroupFilterView;
    } else if (filterType == TaskFilterType.ResultType) {
      pane = taskResultTypeView;
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

  interface TaskFilterTypeViewPane {
    Pane getView();

    void updateLabel();

    TaskFilterType getFilterType();
  }

  abstract class TaskFilterTypeView implements TaskFilterTypeViewPane {
    final HBox hbox = new HBox();
    final Label label = new Label("");
    final Button button = new Button("Delete");

    protected TaskFilterTypeView() {
      hbox.setSpacing(20);
      label.setPadding(new Insets(5, 0, -10, 0));
      button.setOnAction(event -> {
        Optional<TaskFilterTypeViewPane> paneToRemove = tasksFilters.getItems().stream()
            .filter(pane -> pane.getFilterType() == getFilterType()).findFirst();
        if (paneToRemove.isPresent()) {
          tasksFilters.getItems().remove(paneToRemove.get());
          changeListeners.stream().forEach(cl -> cl.onFilterRemove(paneToRemove.get().getFilterType()));
        }
      });
      hbox.getChildren().addAll(button, label);
    }

    protected void addExtraNode(Node extraNode) {
      hbox.getChildren().add(extraNode);
    }

    @Override
    public Pane getView() {
      return hbox;
    }

    @Override
    public void updateLabel() {
      this.label.setText(getFilterType().toString());
    }
  }

  abstract class TaskTextFilterTypeView extends TaskFilterTypeView {
    final TextField filterValue = new TextField();

    protected TaskTextFilterTypeView() {
      super();
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
  }

  class ApplicationNameFilterView extends TaskTextFilterTypeView {
    @Override
    public TaskFilterType getFilterType() {
      return TaskFilterType.ApplicationName;
    }
  }

  class TaskGroupFilterView extends TaskTextFilterTypeView {
    @Override
    public TaskFilterType getFilterType() {
      return TaskFilterType.TaskGroup;
    }
  }

  class TaskResultTypeView extends TaskFilterTypeView {
    ChoiceBox<String> filterValue = new ChoiceBox<>();
    Map<Number, TaskResultType> taskResultTypeIndex = new HashMap<>();

    protected TaskResultTypeView() {
      super();
      taskResultTypeIndex.put(1, TaskResultType.SUCCESS);
      taskResultTypeIndex.put(2, TaskResultType.ERROR);
      taskResultTypeIndex.put(3, TaskResultType.WARNING);
      filterValue.getItems().addAll(taskResultTypeIndex.values().stream().map(
          taskResultType -> taskResultType.toString()).collect(Collectors.toList()));
      filterValue.getSelectionModel().select(0);
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
    public TaskFilterType getFilterType() {
      return TaskFilterType.ResultType;
    }
  }

}
