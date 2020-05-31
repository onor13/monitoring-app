package task.filters;

import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import task.ITaskResult;


public class TaskNameFiltered
    extends IterableEnvelope<ITaskResult> {

  public TaskNameFiltered( String taskName, Iterable<ITaskResult> iterable ) {
    super( new IterableOf<>( () -> new org.cactoos.iterator.Filtered<>( input -> input.getTaskName().equals( taskName ), iterable.iterator() ) )
    );
  }
}
