package serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import converters.LocalDateTimeConverter;
import java.io.IOException;
import java.time.LocalDateTime;

public class CustomLocalDateTimeDeserializer
    extends StdDeserializer<LocalDateTime> {

  LocalDateTimeConverter ldcParser = new LocalDateTimeConverter();

  public CustomLocalDateTimeDeserializer() {
    this(null);
  }

  public CustomLocalDateTimeDeserializer(Class<LocalDateTime> vc) {
    super(vc);
  }

  @Override
  public LocalDateTime deserialize(JsonParser jsonparser, DeserializationContext context)
      throws IOException {
    return ldcParser.parse(jsonparser.getText());
  }
}
