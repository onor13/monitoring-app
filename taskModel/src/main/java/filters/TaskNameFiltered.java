package filters;

import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import task.TaskResult;


public class TaskNameFiltered
    extends IterableEnvelope<TaskResult> {

  public TaskNameFiltered(String taskName, Iterable<TaskResult> iterable) {
    super(new IterableOf<>(
        () -> new org.cactoos.iterator.Filtered<>(
            input -> input.getTaskName().equals(taskName), iterable.iterator()))
    );
  }
}
