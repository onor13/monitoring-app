package queue;

import constants.AmqpConstants;
import javafx.MonitoringFXApp;
import javafx.controllers.TaskResultsTableController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import task.JsonTaskResult;
import task.TaskResult;

import java.util.concurrent.CountDownLatch;

@Service
public class TasksResultsReceiver {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  static Logger log = LoggerFactory.getLogger( TasksResultsReceiver.class );

  private CountDownLatch latch = new CountDownLatch(1);

  @Autowired TaskResultsTableController tableController;

  @RabbitListener( queues = AmqpConstants.QUEUE_NAME )
  //@Scheduled(fixedRate = 2000)
  public void  receiveMessage( JsonTaskResult taskResult ) {
    log.info("Received <" + taskResult.getTaskName() + ">");
    tableController.addTaskResult( taskResult );
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }
}
