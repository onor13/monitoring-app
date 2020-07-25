package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.Duration;

public class CustomDurationSerializer extends StdSerializer<Duration> {

  private static final long serialVersionUID = -8026468255058961958L;

  public CustomDurationSerializer() {
    this(null);
  }

  public CustomDurationSerializer(Class<Duration> t) {
    super(t);
  }

  @Override
  public void serialize(Duration value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeString(String.valueOf(value.toMillis()));
  }
}
