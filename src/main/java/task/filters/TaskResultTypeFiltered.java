package task.filters;

import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import task.ITaskResult;
import task.TaskResultType;

public class TaskResultTypeFiltered
    extends IterableEnvelope<ITaskResult>
  {

  public TaskResultTypeFiltered( TaskResultType taskResultType, Iterable<ITaskResult> iterable ) {
    super( new IterableOf<>( () -> new org.cactoos.iterator.Filtered<>( input -> input.getTaskResultType() == taskResultType, iterable.iterator() ) )
    );
  }
}
