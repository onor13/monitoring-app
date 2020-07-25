package serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;

@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class LocalDateTimeDeserializerTest {

  private ObjectMapper mapper;

  @Before
  public void setup() {
    mapper = new ObjectMapper();
  }

  @Test
  public void testLocalDateTimeDeserializer() throws IOException {
    LocalDateTimeSerializationHelper testObject = new LocalDateTimeSerializationHelper();
    LocalDateTime expected = testObject.getLocalDateTime();

    LocalDateTimeSerializationHelper actualLocalDateTime = mapper.readValue("{\"" + testObject.getAttributeForSerializationName()+"\":" + testObject.getLocalDateTimeStr()+ "}", LocalDateTimeSerializationHelper.class);
    Assert.assertTrue( expected.equals( actualLocalDateTime.getLocalDateTime() ) );
  }
}
