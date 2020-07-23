package producers;

import task.TaskResult;

public interface LogProducer {
  void sendTaskResult( TaskResult taskResult );
}
