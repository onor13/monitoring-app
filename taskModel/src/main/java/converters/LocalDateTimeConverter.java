package converters;

import formatter.Formatter;
import parser.Parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements Formatter<LocalDateTime>, Parser<LocalDateTime> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private static final String EMPTY_STRING = "";

  @Override
  public String format(LocalDateTime value) {
    return value == null ? EMPTY_STRING : FORMATTER.format(value);
  }

  @Override
  public LocalDateTime parse(String value) {
    return LocalDateTime.parse(value, FORMATTER);
  }
}
