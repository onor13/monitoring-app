package producers;

import com.google.common.flogger.FluentLogger;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task.TaskResult;

@Component
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class QueueLogProducer implements LogProducer {
  @Autowired
  private transient RabbitTemplate rabbitTemplate;

  @Autowired
  private FanoutExchange exchange;

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  @Override
  public void sendTaskResult(TaskResult taskResult) {
    logger.atFine().log("Sending message...");
    rabbitTemplate.convertAndSend(exchange.getName(), "", taskResult);
  }
}
