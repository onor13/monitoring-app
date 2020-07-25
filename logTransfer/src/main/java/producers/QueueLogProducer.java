package producers;

import com.google.common.flogger.FluentLogger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import task.TaskResult;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class QueueLogProducer implements LogProducer {
  @Autowired
  private transient RabbitTemplate rabbitTemplate;

  //@Value("${jsa.rabbitmq.queue}")
  //private String queueName;

  @Value("${jsa.rabbitmq.exchange}")
  private String rabbitExchange;

  @Value("${jsa.rabbitmq.routingKey}")
  private String routingkey;

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Override
  public void sendTaskResult(TaskResult taskResult) {
    logger.atFine().log("Sending message...");
    rabbitTemplate.convertAndSend(rabbitExchange, routingkey, taskResult);
  }
}
