package queue;

import com.google.common.flogger.FluentLogger;
import javafx.controllers.TaskResultsTableController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.JsonTaskResult;
import task.TaskDataConnector;
import task.TaskResult;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;

@Service
public class TasksResultsReceiver{

  //private boolean isUIupdatesEnabled = false;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  ConcurrentLinkedDeque<TaskResult> messageQueue = new ConcurrentLinkedDeque<>();

  private CountDownLatch latch = new CountDownLatch(1);

  @Autowired TaskResultsTableController tableController;

  @RabbitListener( queues="${jsa.rabbitmq.queue}" )
  public void receiveMessage( JsonTaskResult taskResult ) {
    messageQueue.add( taskResult );
    logger.atFine().log( "Received task [%s]", taskResult.getTaskName() );
    logger.atFine().log("internal queue size %d", messageQueue.size() );
    tableController.addTaskResult( taskResult );
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }
}
