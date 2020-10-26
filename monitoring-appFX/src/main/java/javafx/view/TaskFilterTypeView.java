package javafx.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "unchecked"})
public abstract class TaskFilterTypeView implements TaskFilterTypeViewPane {
  final HBox hbox = new HBox();
  final Label label = new Label("");
  final Button button = new Button("Delete");

  protected TaskFilterTypeView(RemoveFilterViewListener removeFilterViewListener) {
    hbox.setSpacing(20);
    label.setPadding(new Insets(5, 0, -10, 0));
    button.setOnAction(event -> {
      removeFilterViewListener.onRemoveFilterView(getFilterType());
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
