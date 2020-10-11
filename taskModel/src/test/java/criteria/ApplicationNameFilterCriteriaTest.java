package criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import task.Application;
import task.JsonApplication;
import task.JsonTaskResult;
import task.criteria.ApplicationNameFilterCriteria;
import task.criteria.FilterCriteria;

public class ApplicationNameFilterCriteriaTest {

  final static String expectedAppName = "accounting";

  @Test
  public void testAcceptedApplicationNameFilter(){
    Application app = new JsonApplication(expectedAppName, LocalDateTime.now());
    FilterCriteria criteria = new ApplicationNameFilterCriteria(expectedAppName);
    JsonTaskResult taskResult = new JsonTaskResult(app);
    assertTrue(criteria.isAccepted(taskResult), "application name comparison");
  }

  @Test
  public void testRejectedApplicationNameFilter(){

    Application app = new JsonApplication(expectedAppName, LocalDateTime.now());
    FilterCriteria criteria = new ApplicationNameFilterCriteria("fakeValue");
    JsonTaskResult taskResult = new JsonTaskResult(app);
    assertFalse(criteria.isAccepted(taskResult), "application name comparison");
  }
}
