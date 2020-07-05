package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import constants.Formats;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomLocalDateTimeSerializer
    extends StdSerializer<LocalDateTime> {

  public CustomLocalDateTimeSerializer(){
    this( null );
  }

  public CustomLocalDateTimeSerializer( Class<LocalDateTime> t ) {
    super( t );
  }

  @Override
  public void serialize( LocalDateTime value, JsonGenerator gen, SerializerProvider provider ) throws IOException {
    gen.writeString( Formats.FORMATTER.format( value ) );
  }
}
