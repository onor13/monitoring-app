package task;

import java.util.Queue;

public interface TaskDataConnector {
  void connectToUI();

  void disconnectFromUI();

  boolean isConnectedToUI();

  Queue<TaskResult> pollNewData();
}
