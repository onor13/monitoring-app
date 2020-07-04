package task;

import task.TaskResult;

import java.util.Iterator;

public interface TaskDataConnector {
  void connect();
  void disconnect();
  Iterator<TaskResult> pollNewData();
}
