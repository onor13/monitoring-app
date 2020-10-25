package single;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import filter.single.SingleTaskFilterExecutionDurationBelow;
import filter.single.SingleTaskResultFilter;
import org.junit.jupiter.api.Test;

public class SingleTaskFilterExecutionDurationBelowTest extends SingleTaskFilterExecutionDurationTest {

  @Test
  public void testFilterMatching(){
    SingleTaskResultFilter filter = createInitializedFilter();
    assertTrue(filter.isAccepted(taskResultDurationBelow), "filter should accept");
  }

  @Test
  public void testFilterNonMatching(){
    SingleTaskResultFilter filter = createInitializedFilter();
    assertFalse(filter.isAccepted(taskResultDurationAbove), "filter should not accept");
  }

  @Override
  public SingleTaskResultFilter createEmptyFilter() {
    return new SingleTaskFilterExecutionDurationBelow();
  }

  @Override
  public SingleTaskResultFilter createInitializedFilter() {
    SingleTaskFilterExecutionDurationBelow filter = new SingleTaskFilterExecutionDurationBelow();
    filter.setDurationFilter(referenceDuration);
    return filter;
  }
}
