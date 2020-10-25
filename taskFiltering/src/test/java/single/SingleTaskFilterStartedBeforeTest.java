package single;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import filter.single.SingleTaskFilterStartedBefore;
import filter.single.SingleTaskResultFilter;
import org.junit.jupiter.api.Test;

public class SingleTaskFilterStartedBeforeTest extends SingleTaskFilterStartTime {

  @Test
  public void testFilterMatching(){
    SingleTaskResultFilter filter = createInitializedFilter();
    assertTrue(filter.isAccepted(taskResultStartedBeforeReferenceTime), "filter should accept");
  }

  @Test
  public void testFilterNonMatching(){
    SingleTaskResultFilter filter = createInitializedFilter();
    assertFalse(filter.isAccepted(taskResultStartedAfterReferenceTime), "filter should not accept");
  }

  @Override
  public SingleTaskResultFilter createEmptyFilter() {
    return new SingleTaskFilterStartedBefore();
  }

  @Override
  public SingleTaskResultFilter createInitializedFilter() {
    SingleTaskFilterStartedBefore filter = new SingleTaskFilterStartedBefore();
    filter.setStartedBeforeFilter(referenceTime);
    return filter;
  }
}
