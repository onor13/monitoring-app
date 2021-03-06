package javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.TasksResultsPresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.datamodel.TaskResultModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;
import task.TaskResult;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class TasksResultsTableController implements Initializable, TasksResultsPresenter {

  @FXML
  TableView<TaskResultModel> tableView;
  @FXML
  TableColumn applicationId;
  @FXML
  TableColumn taskName;
  @FXML
  TableColumn taskGroup;
  @FXML
  TableColumn taskResult;
  @FXML
  TableColumn taskStartTime;
  @FXML
  TableColumn taskExecutionDuration;
  ObservableList<TaskResultModel> tasksResults = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tableView.setItems(tasksResults);
  }

  @Override
  public void addTaskResult(TaskResult taskResult) {
    tasksResults.add(new TaskResultModel(taskResult));
  }

  @Override
  public void reloadFrom(Iterable<TaskResult> newTasksResults) {
    tasksResults.clear();
    newTasksResults.forEach(tr -> tasksResults.add(new TaskResultModel(tr)));
  }
}
