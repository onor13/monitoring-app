package serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import serializer.CustomLocalDateTimeSerializer;

@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class JsonLocalDateTimeSerializerTest {

  private static ObjectMapper mapper;
  private static CustomLocalDateTimeSerializer serializer;

  @BeforeAll
  public static void setup() {
    serializer = new CustomLocalDateTimeSerializer();
    mapper = new ObjectMapper();

  }

  @Test
  public void testLocalDateTimeSerializer() throws IOException {
    LocalDateTimeSerializationHelper testObject = new LocalDateTimeSerializationHelper();
    LocalDateTime expected = testObject.getLocalDateTime();

    Writer jsonWriter = new StringWriter();
    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    try{
      SerializerProvider serializerProvider = mapper.getSerializerProvider();
      serializer.serialize( expected , jsonGenerator, serializerProvider);
      jsonGenerator.flush();
    }
    finally {
      jsonGenerator.close();
    }
    String ldcSerialized = jsonWriter.toString();
    assertEquals(ldcSerialized, testObject.getLocalDateTimeStr(), "localDateTime serialization");
  }

  @Test
  public void ldcTest(){
    LocalDateTime ldc1 = LocalDateTime.of( 2020, 07, 12, 10, 00, 00 );
    LocalDateTime ldc2 = LocalDateTime.of( 2020, 07, 12, 10, 00, 00 );
    assertEquals(ldc1.hashCode(), ldc2.hashCode(), "localDateTime");
  }
}
