package javafx.controllers;

import com.google.common.flogger.FluentLogger;
import distributors.TaskDataDistributor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import org.controlsfx.control.ToggleSwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.TaskResult;
import view.Presenter;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class MainController
    implements Initializable, Presenter {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @FXML
  ToggleSwitch updatesOnOff;

  @FXML
  ComboBox<ViewType> viewSelector;

  @FXML
  TaskResultsTableController tableViewController;

  @Autowired
  TaskDataDistributor dataDistributor;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
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

    viewSelector.getItems().addAll(ViewType.values());
    viewSelector.valueProperty().setValue(ViewType.Table);
    viewSelector.valueProperty().addListener(new ChangeListener<ViewType>() {
      @Override
      public void changed(ObservableValue<? extends ViewType> observable, ViewType oldValue, ViewType newValue) {
        logger.atInfo().log(String.format("Changing view from %s to %s", oldValue.type, newValue.type));
        showNotSupportedFeature("Changing view type");
      }
    });
  }

  @Override
  public void addTaskResult(TaskResult taskResult) {
    tableViewController.addTaskResult(taskResult);
  }

  protected void reloadTasksResults() {
    tableViewController.reloadFrom(dataDistributor.getAllTasksResults());
  }

  protected void showNotSupportedFeature(String featureName){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Not supported");
    alert.setHeaderText(featureName + " is not implemented yet");
    alert.showAndWait();
  }

  public enum ViewType{
    Table("Table"), Chart("Chart");
    private String type;

    ViewType(String type){
      this.type = type;
    }
  }
}
