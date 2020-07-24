package javafx.controllers;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

@Component
public class SimpleController {

  private final HostServices hostServices;

  @FXML
  public Label label;

  @FXML
  public Button button;

  SimpleController(HostServices hostServices) {
    this.hostServices = hostServices;
  }

  @FXML
  public void initialize() {
    this.button.setOnAction(actionEvent -> this.label.setText(this.hostServices.getDocumentBase()));
  }
}
