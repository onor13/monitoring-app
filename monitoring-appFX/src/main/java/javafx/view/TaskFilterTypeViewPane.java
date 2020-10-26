package javafx.view;

import javafx.scene.layout.Pane;
import task.criteria.FilterCriteriaType;

public interface TaskFilterTypeViewPane {
  Pane getView();

  void updateLabel();

  FilterCriteriaType getFilterType();

  void resetView();
}
