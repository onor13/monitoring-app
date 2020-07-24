package queue;

import com.google.common.flogger.FluentLogger;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.TaskDataConnector;
import task.TaskResult;

@Service
public class TasksResultsReceiver implements TaskDataConnector {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private AtomicBoolean isUIupdatesEnabled = new AtomicBoolean(true);

  @Autowired
  private RabbitTemplate rabbitTemplate;

  ConcurrentLinkedDeque<TaskResult> messageQueue = new ConcurrentLinkedDeque<>();

  private CountDownLatch latch = new CountDownLatch(1);

  public TasksResultsReceiver() {
    logger.atConfig().log("Bean " + TasksResultsReceiver.class.getSimpleName() + " created ");
  }

  @RabbitListener(queues = "${jsa.rabbitmq.queue}")
  public void receiveMessage(TaskResult taskResult) {
    messageQueue.add(taskResult);
    logger.atFine().log("Received task [%s]", taskResult.getTaskName());
    logger.atFine().log("internal queue size %d", messageQueue.size());

    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }

  @Override
  public void connectToUI() {
    isUIupdatesEnabled.set(true);
  }

  @Override
  public void disconnectFromUI() {
    isUIupdatesEnabled.set(false);
  }

  @Override
  public boolean isConnectedToUI() {
    return isUIupdatesEnabled.get();
  }

  @Override
  public Queue<TaskResult> pollNewData() {
    return messageQueue;
  }
}
