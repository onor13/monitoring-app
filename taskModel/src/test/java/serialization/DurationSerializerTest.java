package serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import serializer.CustomDurationSerializer;

@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class DurationSerializerTest {
  private ObjectMapper             mapper;
  private CustomDurationSerializer serializer;

  @BeforeAll
  public void setup() {
    serializer = new CustomDurationSerializer();
    mapper = new ObjectMapper();
  }

  @Test
  public void testDurationSerializer() throws IOException {
    DurationSerializationHelper helper = new DurationSerializationHelper();
    Writer jsonWriter = new StringWriter();
    SerializerProvider serializerProvider = mapper.getSerializerProvider();
    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    try{
      serializer.serialize( helper.getDuration(), jsonGenerator, serializerProvider);
      jsonGenerator.flush();
    }
    finally {
      jsonGenerator.close();
    }

    String expectedSerializerDurationInMilliseconds = helper.getSerializedDurationValue();
    assertEquals(jsonWriter.toString(), expectedSerializerDurationInMilliseconds, "LocalDateTime serialization");
  }
}
