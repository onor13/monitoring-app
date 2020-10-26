package javafx.view;

import java.time.LocalDateTime;
import javafx.listeners.DateTimeChangeListener;

public abstract class TaskStartTimeTypeView extends TaskFilterTypeView {
  final transient DateTimePicker dateTimePicker = new DateTimePicker();

  protected TaskStartTimeTypeView(RemoveFilterViewListener removeFilterViewListener) {
    super(removeFilterViewListener);
    addExtraNode(dateTimePicker);
    dateTimePicker.addChangeListener(new DateTimeChangeListener() {
      @Override
      public void onDateTimeChange(LocalDateTime dateTime) {
        onValueChange(dateTime);
      }
    });
  }

  protected abstract void onValueChange(LocalDateTime localDateTime);

  @Override
  public void resetView() {
    dateTimePicker.getEditor().clear();
  }
}