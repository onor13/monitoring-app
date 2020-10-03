package serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class DurationDeserializerTest {

  private static ObjectMapper mapper;

  @BeforeAll
  public static void setup() {
    mapper = new ObjectMapper();
  }

  @Test
  public void testDurationDeserializer() throws IOException {
    DurationSerializationHelper testObject = new DurationSerializationHelper();
    Duration expected = testObject.getDuration();

    DurationSerializationHelper actualLocalDateTime = mapper.readValue(
        "{\"" + testObject.getAttributeForSerializationName()+"\":" + testObject.getSerializedDurationValue()
            + "}", DurationSerializationHelper.class);
    assertEquals(expected.getSeconds(), actualLocalDateTime.getDuration().getSeconds(), "deserialization");
  }

}
