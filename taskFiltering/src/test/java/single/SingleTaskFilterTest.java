package single;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import filter.single.SingleTaskResultFilter;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.JUnitTestContainsTooManyAsserts"})
public abstract class SingleTaskFilterTest {

  public abstract SingleTaskResultFilter createEmptyFilter();

  public abstract SingleTaskResultFilter createInitializedFilter();

  @Test
  public void testFilterDefaultEmpty() {
    SingleTaskResultFilter filter = createEmptyFilter();
    assertTrue(filter.isEmptyFilter(), "filter should not be empty");
  }

  @Test
  public void testFilterNonEmpty() {
    SingleTaskResultFilter filter = createInitializedFilter();
    assertFalse(filter.isEmptyFilter(), "filter should not be empty");
  }

  @Test
  public void testFilterReset() {
    SingleTaskResultFilter filter = createInitializedFilter();
    assertFalse(filter.isEmptyFilter(), "filter should not be empty");
    filter.resetFilter();
    assertTrue(filter.isEmptyFilter(), "after reset filter should be empty");
  }
}
