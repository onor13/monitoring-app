package javafx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.dataModel.TaskResultModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import task.TaskResult;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class TaskResultsTableController implements Initializable {

  @FXML TableView<TaskResultModel> tableView;
  @FXML TableColumn applicationId;
  @FXML TableColumn taskName;
  @FXML TableColumn taskGroup;
  @FXML TableColumn taskResult;
  @FXML TableColumn taskStartTime;
  @FXML TableColumn taskExecutionDuration;
  ObservableList<TaskResultModel> tasksResults;

  @Override public void initialize( URL location, ResourceBundle resources ) {
    tasksResults = FXCollections.observableArrayList();
    tableView.setItems( tasksResults );
  }

  public void addTaskResult( TaskResult taskResult ){
    tasksResults.add( new TaskResultModel( taskResult ) );
  }

}
