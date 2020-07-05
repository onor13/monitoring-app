package serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import constants.Formats;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomLocalDateTimeDeserializer
    extends StdDeserializer<LocalDateTime> {


  public CustomLocalDateTimeDeserializer() {
    this(null);
  }

  public CustomLocalDateTimeDeserializer(Class<LocalDateTime> vc) {
    super(vc);
  }

  @Override
  public LocalDateTime deserialize( JsonParser jsonparser, DeserializationContext context)
      throws IOException {
    String ldtStr = jsonparser.getText();
    return LocalDateTime.parse( ldtStr , Formats.FORMATTER );
  }
}
