package serialization;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import serializer.CustomDurationSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class DurationSerializerTest {
  private ObjectMapper             mapper;
  private CustomDurationSerializer serializer;

  @Before
  public void setup() {
    serializer = new CustomDurationSerializer();
    mapper = new ObjectMapper();
  }

  @Test
  public void testDurationSerializer() throws IOException {
    DurationSerializationHelper helper = new DurationSerializationHelper();
    Writer jsonWriter = new StringWriter();
    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    SerializerProvider serializerProvider = mapper.getSerializerProvider();
    serializer.serialize( helper.getDuration(), jsonGenerator, serializerProvider);
    jsonGenerator.flush();

    String ldcSerialized = jsonWriter.toString();
    String expectedSerializerDurationInMilliseconds = helper.getSerializedDurationValue();
    Assert.assertEquals( ldcSerialized, expectedSerializerDurationInMilliseconds );
  }
}
