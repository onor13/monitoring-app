package javafx;

import org.springframework.amqp.core.Queue;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
@SpringBootApplication
public class MonitoringApplication  {

  static final String topicExchangeName = "tasksResults";

  static final String queueName = "tasksResults";

  @Bean Queue queue() {
    return new Queue(queueName, false);
  }

  public static void main(String[] args) {
    Application.launch( MonitoringFXApp.class, args);
  }

}
