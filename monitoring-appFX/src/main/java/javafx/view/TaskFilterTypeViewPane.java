package javafx.view;

import javafx.TaskFilterType;
import javafx.scene.layout.Pane;

public interface TaskFilterTypeViewPane {
  Pane getView();

  void updateLabel();

  TaskFilterType getFilterType();

  void resetView();
}
