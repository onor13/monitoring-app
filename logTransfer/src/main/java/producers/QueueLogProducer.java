package producers;

import com.google.common.flogger.FluentLogger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import task.TaskResult;

@Component
public class QueueLogProducer implements LogProducer {
  @Autowired
  RabbitTemplate rabbitTemplate;

  @Value("${jsa.rabbitmq.queue}")
  String queueName;

  @Value("${jsa.rabbitmq.exchange}")
  private String exchange;

  @Value("${jsa.rabbitmq.routingKey}")
  private String routingkey;

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Override
  public void sendTaskResult(TaskResult taskResult) {
    logger.atFine().log("Sending message...");
    rabbitTemplate.convertAndSend(exchange, routingkey, taskResult);
  }
}
