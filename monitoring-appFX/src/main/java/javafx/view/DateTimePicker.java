package javafx.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.listeners.DateTimeChangeListener;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "unchecked"})
public class DateTimePicker extends DatePicker {
  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

  private DateTimeFormatter formatter;
  private ObjectProperty<LocalDateTime> dateTimeValue = new SimpleObjectProperty<>(LocalDateTime.now());
  private ObjectProperty<String> format = new SimpleObjectProperty<>() {
    @Override
    public void set(String newValue) {
      super.set(newValue);
      formatter = DateTimeFormatter.ofPattern(newValue);
    }
  };
  private final Collection<DateTimeChangeListener> changeListeners = new ArrayList<>();

  /***
   * <p>Custom date time picker. Requires ENTER key to be pressed to notify about changes.</p>
   */
  public DateTimePicker() {
    getStyleClass().add("datetime-picker");
    format.set(DATE_TIME_FORMAT);
    setConverter(new LocalDateConverter());

    // Synchronize changes to the underlying date value back to the dateTimeValue
    valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == null) {
        dateTimeValue.set(null);
      } else {
        if (dateTimeValue.get() == null) {
          dateTimeValue.set(LocalDateTime.of(newValue, LocalTime.now()));
        } else {
          dateTimeValue.set(LocalDateTime.of(newValue, dateTimeValue.get().toLocalTime()));
        }
      }
    });

    // Synchronize changes to dateTimeValue back to the underlying date value
    dateTimeValue.addListener((observable, oldValue, newValue) -> {
      setValue(newValue == null ? null : newValue.toLocalDate());
      changeListeners.stream().forEach(changeListener -> changeListener.onDateTimeChange(newValue));
    });

    // Persist changes on blur
    getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        simulateEnterPressed();
      }
    });

  }

  public void addChangeListener(DateTimeChangeListener changeListener) {
    changeListeners.add(changeListener);
  }

  private void simulateEnterPressed() {
    getEditor().fireEvent(new KeyEvent(getEditor(), getEditor(), KeyEvent.KEY_PRESSED, null, null,
        KeyCode.ENTER, false, false, false, false));
  }

  public LocalDateTime getDateTimeValue() {
    return dateTimeValue.get();
  }

  class LocalDateConverter extends StringConverter<LocalDate> {
    @Override
    public String toString(LocalDate object) {
      LocalDateTime value = getDateTimeValue();
      return (value != null) ? value.format(formatter) : "";
    }

    @Override
    public LocalDate fromString(String value) {
      if (value == null) {
        dateTimeValue.set(null);
        return null;
      }

      dateTimeValue.set(LocalDateTime.parse(value, formatter));
      return dateTimeValue.get().toLocalDate();
    }
  }
}
