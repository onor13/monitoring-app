package javafx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import org.springframework.stereotype.Component;


@Component
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "unchecked"})
public class ChartController {

  @FXML
  public LineChart<String, Double> chart;

  public ChartController() {
  }

  /***
   * <p>initalize some random data, just for test.</p>
   */
  @FXML
  public void initialize() {
    ObservableList<Data<String, Double>> seriesData1 = FXCollections.observableArrayList();
    seriesData1.add(new Data<>("Val 1", 1.0));
    seriesData1.add(new Data<>("Val 2", 2.0));
    Series series1 = new Series("Sym1", seriesData1);
    ObservableList<Data<String, Double>> seriesData2 = FXCollections.observableArrayList();
    seriesData1.add(new Data<>("Val 3", 3.0));
    seriesData1.add(new Data<>("Val 4", 4.0));
    Series series2 = new Series("Sym2", seriesData2);
    ObservableList<Series<String, Double>> data = FXCollections.observableArrayList();
    data.add(series1);
    data.add(series2);
    chart.setData(data);

  }

}
