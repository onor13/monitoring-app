package serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class DurationDeserializerTest {

  private ObjectMapper mapper;

  @Before
  public void setup() {
    mapper = new ObjectMapper();
  }

  @Test
  public void testDurationDeserializer() throws IOException {
    DurationSerializationHelper testObject = new DurationSerializationHelper();
    Duration expected = testObject.getDuration();

    DurationSerializationHelper actualLocalDateTime = mapper.readValue("{\"" + testObject.getAttributeForSerializationName()+"\":" + testObject.getSerializedDurationValue()+ "}", DurationSerializationHelper.class);
    Assert.assertEquals( expected.getSeconds(), actualLocalDateTime.getDuration().getSeconds() );
  }

}
