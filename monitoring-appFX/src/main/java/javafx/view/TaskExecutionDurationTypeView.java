package javafx.view;

import java.time.Duration;
import java.time.LocalTime;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "unchecked"})
public abstract class TaskExecutionDurationTypeView extends TaskFilterTypeView {
  private final String pattern = "HH:mm:ss";
  final TextField filterValue = new TextField();
  final Label validityLabel = new Label();

  protected TaskExecutionDurationTypeView(RemoveFilterViewListener removeFilterViewListener) {
    super(removeFilterViewListener);
    filterValue.textProperty().addListener((observable, oldValue, newValue) -> {
      resetValidity();
      try {
        if (newValue == null || newValue.isEmpty()) {
          return;
        }
        LocalTime lt = LocalTime.parse(newValue);
        Duration duration = Duration.between(LocalTime.MIN, lt);
        setValidFormat();
        onValueChange(duration);
      } catch (Exception parseException) {
        setInvalidFormat();
      }
    });
    addExtraNode(new Label(pattern));
    addExtraNode(filterValue);
    addExtraNode(validityLabel);
  }

  protected void setInvalidFormat() {
    validityLabel.setText("Invalid Duration format");
    validityLabel.setStyle("-fx-text-fill:red");
  }

  protected void setValidFormat() {
    validityLabel.setStyle("-fx-text-fill:green");
    validityLabel.setText("Valid");
  }

  protected void resetValidity() {
    validityLabel.setText("");
  }

  @Override
  public void resetView() {
    filterValue.textProperty().setValue("");
  }

  protected abstract void onValueChange(Duration duration);

}
