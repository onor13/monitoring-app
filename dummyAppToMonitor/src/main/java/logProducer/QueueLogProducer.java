package logProducer;

import constants.AmqpConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.TaskResult;

@Component
public class QueueLogProducer implements LogProducer {
  @Autowired
  RabbitTemplate rabbitTemplate;

  public QueueLogProducer(){}

  @Override public void sendTaskResult( TaskResult taskResult ) {
    System.out.println("Sending message...");
     rabbitTemplate.convertAndSend( AmqpConstants.EXCHANGE_OBJECTS, AmqpConstants.ROUTING_KEY_PREFIX + "test", taskResult );
  }
}
