package converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, Long> {

  Logger log = Logger.getLogger(DurationConverter.class.getSimpleName());

  @Override
  public Long convertToDatabaseColumn(Duration attribute) {
    log.info("Convert to Long");
    return attribute.toNanos();
  }

  @Override
  public Duration convertToEntityAttribute(Long duration) {
    log.info("Convert to Duration");
    return Duration.of(duration, ChronoUnit.NANOS);
  }
}
