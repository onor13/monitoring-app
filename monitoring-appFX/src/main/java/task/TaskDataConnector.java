package task;

import java.util.Iterator;

public interface TaskDataConnector {
  void connect();
  void disconnect();
  Iterator<TaskResult> pollNewData();
}
