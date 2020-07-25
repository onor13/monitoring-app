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

@SuppressWarnings("PMD.BeanMembersShouldSerialize")
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
    Assert.assertEquals( "LocalDateTime serialization", jsonWriter.toString(), expectedSerializerDurationInMilliseconds );
  }
}
