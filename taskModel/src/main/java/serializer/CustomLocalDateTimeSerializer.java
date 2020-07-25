package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import converters.LocalDateTimeConverter;
import java.io.IOException;
import java.time.LocalDateTime;

public class CustomLocalDateTimeSerializer
    extends StdSerializer<LocalDateTime> {

  private static final long serialVersionUID = -6301777071724137026L;
  private final transient LocalDateTimeConverter ldcFormatter = new LocalDateTimeConverter();

  public CustomLocalDateTimeSerializer() {
    this(null);
  }

  public CustomLocalDateTimeSerializer(Class<LocalDateTime> t) {
    super(t);
  }

  @Override
  public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeString(ldcFormatter.format(value));
  }
}
