package javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.TasksResultsPresenter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Component;
import task.TaskResult;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class TasksResultsViewController implements Initializable, TasksResultsPresenter {

  @FXML
  TasksResultsTableController tableViewController;

  @FXML
  TasksResultsChartController chartViewController;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  @Override
  public void addTaskResult(TaskResult taskResult) {
    tableViewController.addTaskResult(taskResult);
    chartViewController.addTaskResult(taskResult);
  }

  @Override
  public void reloadFrom(Iterable<TaskResult> newTasksResults) {
    tableViewController.reloadFrom(newTasksResults);
    chartViewController.reloadFrom(newTasksResults);
  }
}
