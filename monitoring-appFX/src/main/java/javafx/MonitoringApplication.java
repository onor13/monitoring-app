package javafx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import storage.DbTasksResultsStorage;
import storage.TasksResultsStorage;
import task.JsonTaskResult;
import task.config.DbConfig;

@ComponentScan(basePackages = {
    "javafx",
    "queue",
    "task"},
    basePackageClasses = DbConfig.class)
@SpringBootApplication
@EnableScheduling
public class MonitoringApplication  {

  @Value("${jsa.rabbitmq.queue}")
  String queueName;

  @Value("${jsa.rabbitmq.exchange}")
  private String exchange;

  @Value("${jsa.rabbitmq.routingKey}")
  private String routingkey;

  @Bean Queue queue() {
    return new Queue(queueName, false);
  }


  @Value("${jsa.rabbitmq.connectionFactory.address}")
  private String connectionFactoryAddress;

  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    connectionFactory.setAddresses(connectionFactoryAddress);
    return connectionFactory;
  }

  @Bean
  RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setRoutingKey(routingkey);
    template.setMessageConverter(jsonMessageConverter());
    //TODO use anonymous Queues instead, so on start we do not receive old messages
    return template;
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
    jsonMessageConverter.setClassMapper(classMapper());
    return jsonMessageConverter;
  }

  @Bean
  public DefaultClassMapper classMapper() {
    DefaultClassMapper classMapper = new DefaultClassMapper();
    Map<String, Class<?>> idClassMapping = new HashMap<>();
    idClassMapping.put("task.JsonTaskResult", JsonTaskResult.class);
    classMapper.setIdClassMapping(idClassMapping);
    return classMapper;
  }

  @Bean
  @Primary
  public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
    ObjectMapper objectMapper = builder.build();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    return objectMapper;
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(exchange);
  }

  @Bean
  Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(routingkey);
  }

  @Bean
  TasksResultsStorage tasksResultsStorage() {
    TasksResultsStorage storage = new DbTasksResultsStorage();
    return storage;
  }

  public static void main(String[] args) {
    Application.launch(MonitoringFxApp.class, args);
  }

}
