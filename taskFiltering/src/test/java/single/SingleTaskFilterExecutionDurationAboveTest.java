package single;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import filter.single.SingleTaskFilterExecutionDurationAbove;
import filter.single.SingleTaskResultFilter;
import org.junit.jupiter.api.Test;

public class SingleTaskFilterExecutionDurationAboveTest extends SingleTaskFilterExecutionDurationTest {

  @Test
  public void testFilterMatching(){
    SingleTaskResultFilter filter = createInitializedFilter();
    assertTrue(filter.isAccepted(taskResultDurationAbove), "filter should accept");
  }

  @Test
  public void testFilterNonMatching(){
    SingleTaskResultFilter filter = createInitializedFilter();
    assertFalse(filter.isAccepted(taskResultDurationBelow), "filter should not accept");
  }

  @Override
  public SingleTaskResultFilter createEmptyFilter() {
    return new SingleTaskFilterExecutionDurationAbove();
  }

  @Override
  public SingleTaskResultFilter createInitializedFilter() {
    SingleTaskFilterExecutionDurationAbove filter = new SingleTaskFilterExecutionDurationAbove();
    filter.setDurationFilter(referenceDuration);
    return filter;
  }
}
