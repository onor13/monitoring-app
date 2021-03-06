package single;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import filter.single.SingleTaskFilterStartedAfter;
import filter.single.SingleTaskResultFilter;
import org.junit.jupiter.api.Test;

public class SingleTaskFilterStartedAfterTest extends SingleTaskFilterStartTime {

  @Test
  public void testFilterMatching(){
    SingleTaskResultFilter filter = createInitializedFilter();
    assertTrue(filter.isAccepted(taskResultStartedAfterReferenceTime), "filter should accept");
  }

  @Test
  public void testFilterNonMatching(){
    SingleTaskResultFilter filter = createInitializedFilter();
    assertFalse(filter.isAccepted(taskResultStartedBeforeReferenceTime), "filter should not accept");
  }

  @Override
  public SingleTaskResultFilter createEmptyFilter() {
    return new SingleTaskFilterStartedAfter();
  }

  @Override
  public SingleTaskResultFilter createInitializedFilter() {
    SingleTaskFilterStartedAfter filter = new SingleTaskFilterStartedAfter();
    filter.setStartedAfterFilter(referenceTime);
    return filter;
  }
}
