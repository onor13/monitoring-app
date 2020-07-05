package serialization;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import serializer.CustomDurationDeserializer;
import serializer.CustomDurationSerializer;

import java.time.Duration;

public class DurationSerializationHelper {

  @JsonSerialize(using = CustomDurationSerializer.class)
  @JsonDeserialize(using = CustomDurationDeserializer.class)
  private final Duration duration;

  public final static int durationInMinutes = 10;
  public final static String  durationInMillisecondsStr = String.valueOf( durationInMinutes * 60 * 1000 );

  public DurationSerializationHelper(){
    duration = Duration.ofMinutes( 10 );
  }

  public Duration getDuration(){
    return duration;
  }

  public String getSerializedDurationValue(){
    return "\"" + durationInMillisecondsStr + "\"";
  }

  public String getAttributeForSerializationName() {
    return "duration";
  }
}
