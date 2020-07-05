package serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Duration;

public class CustomDurationDeserializer extends StdDeserializer<Duration> {

  public CustomDurationDeserializer(){
    this( null );
  }

  public CustomDurationDeserializer( Class<Duration> vc ) {
    super( vc );
  }

  @Override
  public Duration deserialize( JsonParser jp, DeserializationContext ctxt ) throws IOException {
    String durationInMilliseconds = jp.getText();
    return Duration.ofMillis( Long.parseLong( durationInMilliseconds ) );
  }
}
