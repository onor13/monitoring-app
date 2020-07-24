package serialization;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import converters.LocalDateTimeConverter;
import serializer.CustomLocalDateTimeDeserializer;
import serializer.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;

public class LocalDateTimeSerializationHelper {

  @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
  @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
  private final LocalDateTime localDateTime;

  private final static String  localDateTimeStr = "2016-03-04 11:30:00";

  public LocalDateTimeSerializationHelper(){
    localDateTime = new LocalDateTimeConverter().parse(localDateTimeStr);
  }

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  public String getLocalDateTimeStr(){
    return "\"" + localDateTimeStr + "\"";
  }

  public String getAttributeForSerializationName(){
    return "localDateTime";
  }
}
