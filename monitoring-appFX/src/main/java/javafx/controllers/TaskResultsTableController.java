package javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
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
public class TaskResultsTableController implements Initializable {

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

  ReadWriteLock lock = new ReentrantReadWriteLock();
  Lock writeLock = lock.writeLock();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tableView.setItems(tasksResults);
  }

  public void addTaskResult(TaskResult taskResult) {
    try {
      writeLock.lock();
      if (!tasksResults.contains(taskResult)) {
        tasksResults.add(new TaskResultModel(taskResult));
      }
    } finally {
      writeLock.unlock();
    }
  }

  public void reloadFrom(Iterable<TaskResult> newTasksResults) {
    try {
      writeLock.lock();
      this.tasksResults.clear();
      newTasksResults.forEach(tr -> tasksResults.add(new TaskResultModel(tr)));
    } finally {
      writeLock.unlock();
    }
  }
}
