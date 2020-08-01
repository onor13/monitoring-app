package receivers;

import com.google.common.flogger.FluentLogger;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.TaskDataConnector;
import task.TaskResult;


@Service
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class TasksResultsReceiver implements TaskDataConnector {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private AtomicBoolean isUIupdatesEnabled = new AtomicBoolean(true);

  ConcurrentLinkedQueue<TaskResult> messageQueue = new ConcurrentLinkedQueue<>();

  private CountDownLatch latch = new CountDownLatch(1);

  Queue autoDeleteQueue1;
  Queue autoDeleteQueue2;

  @Autowired
  public TasksResultsReceiver(Queue queue1, Queue queue2) {
    logger.atConfig().log("Bean " + TasksResultsReceiver.class.getSimpleName() + " created ");
    autoDeleteQueue1 = queue1;
    autoDeleteQueue2 = queue2;
  }



  @RabbitListener(queues = "#{autoDeleteQueue1.name}")
  public void receiveMessage(TaskResult taskResult) {
    receive(taskResult);
  }

  @RabbitListener(queues = "#{autoDeleteQueue2.name}")
  public void receiveMessage2(TaskResult taskResult) {
    receive(taskResult);
  }

  protected void receive(TaskResult taskResult) {
    logger.atFine().log("Received task [%s]", taskResult.getTaskName());
    messageQueue.add(taskResult);
    logger.atFine().log("internal queue size %d", messageQueue.size());

    latch.countDown();
  }

  /*@RabbitListener(queues = "${jsa.rabbitmq.deadLetterQueue}")
  public void receiveFailedMessage(Message message) {
    logger.atInfo().log("Received failed message [%s]", message.toString());
  }*/

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
  public java.util.Queue<TaskResult> pollNewData() {
    return messageQueue;
  }
}
