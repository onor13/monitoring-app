package serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import converters.LocalDateTimeConverter;
import java.io.IOException;
import java.time.LocalDateTime;

public class CustomLocalDateTimeDeserializer
    extends StdDeserializer<LocalDateTime> {

  private static final long serialVersionUID = -6393963298727586570L;
  private final transient LocalDateTimeConverter ldcParser = new LocalDateTimeConverter();

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
