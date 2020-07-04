package task.filters;

import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import task.TaskResult;
import task.TaskResultType;

public class TaskResultTypeFiltered
    extends IterableEnvelope<TaskResult>
  {

  public TaskResultTypeFiltered( TaskResultType taskResultType, Iterable<TaskResult> iterable ) {
    super( new IterableOf<>( () -> new org.cactoos.iterator.Filtered<>( input -> input.getTaskResultType() == taskResultType, iterable.iterator() ) )
    );
  }
}
