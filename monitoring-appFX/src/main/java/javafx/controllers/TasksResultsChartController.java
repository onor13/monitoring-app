package javafx.controllers;

import java.util.HashMap;
import java.util.Map;
import javafx.TasksResultsPresenter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import org.springframework.stereotype.Component;
import task.TaskResult;
import task.TaskResultType;


@Component
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "unchecked"})
public class TasksResultsChartController implements TasksResultsPresenter {

  @FXML
  public StackedBarChart<String, Integer> chart;

  final CategoryAxis axisX = new CategoryAxis();

  final XYChart.Series<String, Integer> succeededResultsSeries = new XYChart.Series<>();
  final XYChart.Series<String, Integer> failedResultsSeries = new XYChart.Series<>();
  final XYChart.Series<String, Integer> warningResultsSeries = new XYChart.Series<>();

  private static final String succeededDataSeriesName = "Succeeded";
  private static final String errorDataSeriesName = "Error";
  private static final String warningDataSeriesName = "Warning";

  private static final String unknownGroup = "Unknown";

  public TasksResultsChartController() {
  }

  /*** initialize the controllers data structures.
   */
  @FXML
  public void initialize() {
    succeededResultsSeries.setName(succeededDataSeriesName);
    failedResultsSeries.setName(errorDataSeriesName);
    warningResultsSeries.setName(warningDataSeriesName);
    axisX.getCategories().addAll(succeededDataSeriesName, errorDataSeriesName, warningDataSeriesName);

    //the order is important, because by default the colors are index 0:red, 1:orange, 2:green
    chart.getData().add(failedResultsSeries);
    chart.getData().add(warningResultsSeries);
    chart.getData().add(succeededResultsSeries);
  }

  @Override
  public void addTaskResult(TaskResult taskResult) {
    addTaskResultToChart(taskResult);
  }

  @Override
  public void reloadFrom(Iterable<TaskResult> newTasksResults) {
    Map<String, Integer> numberOfSucceedTasksResultsByGroup = new HashMap<>();
    Map<String, Integer> numberOfErrorTasksResultsByGroup = new HashMap<>();
    Map<String, Integer> numberOfWarningTasksResultsByGroup = new HashMap<>();

    newTasksResults.forEach(tr -> {
      if (tr.getTaskResultType() == TaskResultType.ERROR) {
        updateNumberOfTasksResultsByGroup(tr, numberOfErrorTasksResultsByGroup);
      } else if (tr.getTaskResultType() == TaskResultType.SUCCESS) {
        updateNumberOfTasksResultsByGroup(tr, numberOfSucceedTasksResultsByGroup);
      } else if (tr.getTaskResultType() == TaskResultType.WARNING) {
        updateNumberOfTasksResultsByGroup(tr, numberOfWarningTasksResultsByGroup);
      }
    });
    updateDataSeries(numberOfSucceedTasksResultsByGroup, succeededResultsSeries);
    updateDataSeries(numberOfErrorTasksResultsByGroup, failedResultsSeries);
    updateDataSeries(numberOfWarningTasksResultsByGroup, warningResultsSeries);
  }

  private void updateNumberOfTasksResultsByGroup(
      TaskResult taskResult,
      Map<String, Integer> numberOfTasksResultsByGroup) {
    if (numberOfTasksResultsByGroup.containsKey(taskResult.getTaskGroup())) {
      int previousAmount = numberOfTasksResultsByGroup.get(taskResult.getTaskGroup());
      numberOfTasksResultsByGroup.put(taskResult.getTaskGroup(), previousAmount + 1);
    } else {
      numberOfTasksResultsByGroup.put(taskResult.getTaskGroup(), 1);
    }
  }

  private void updateDataSeries(
      Map<String, Integer> numberOfTasksResultsByGroup,
      XYChart.Series<String, Integer> dataSeries) {
    for (Map.Entry<String, Integer> entry : numberOfTasksResultsByGroup.entrySet()) {
      Platform.runLater(
          () -> {
            dataSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
          }
      );
    }
  }

  void addTaskResultToChart(TaskResult taskResult) {
    XYChart.Series<String, Integer> dataSeries = getDataSeries(taskResult);
    String groupName = (taskResult.getTaskGroup() != null && !taskResult.getTaskGroup().isEmpty())
        ? taskResult.getTaskGroup() : unknownGroup;
    Platform.runLater(
        () -> {
          dataSeries.getData().add(new XYChart.Data<>(groupName, 1));
        }
    );

  }

  XYChart.Series<String, Integer> getDataSeries(TaskResult taskResult) {
    if (taskResult.getTaskResultType() == TaskResultType.ERROR) {
      return failedResultsSeries;
    }
    if (taskResult.getTaskResultType() == TaskResultType.SUCCESS) {
      return succeededResultsSeries;
    }
    if (taskResult.getTaskResultType() == TaskResultType.WARNING) {
      return warningResultsSeries;
    }
    throw new RuntimeException("unknown task result type" + taskResult.getTaskResultType());
  }
}
