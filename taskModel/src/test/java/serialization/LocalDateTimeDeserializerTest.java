package serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class LocalDateTimeDeserializerTest {

  private static ObjectMapper mapper;

  @BeforeAll
  public static void setup() {
    mapper = new ObjectMapper();
  }

  @Test
  public void testLocalDateTimeDeserializer() throws IOException {
    LocalDateTimeSerializationHelper testObject = new LocalDateTimeSerializationHelper();
    LocalDateTime expected = testObject.getLocalDateTime();

    LocalDateTimeSerializationHelper actualLocalDateTime = mapper.readValue("{\"" + testObject.getAttributeForSerializationName()+"\":" + testObject.getLocalDateTimeStr()+ "}", LocalDateTimeSerializationHelper.class);
    assertEquals(expected, actualLocalDateTime.getLocalDateTime(), "localDateTime");
  }
}
