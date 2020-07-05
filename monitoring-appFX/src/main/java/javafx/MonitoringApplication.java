package javafx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import constants.AmqpConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import javafx.application.Application;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import task.JsonTaskResult;

import java.util.HashMap;
import java.util.Map;

import static constants.AmqpConstants.ROUTING_KEY_PREFIX;

@ComponentScan( basePackages = {
    "javafx",
    "queue" })
@SpringBootApplication
public class MonitoringApplication  {

  @Bean Queue queue() {
    return new Queue( AmqpConstants.QUEUE_NAME, false);
  }

  @Bean
  public MessageConverter jsonMessageConverter(){
    Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
    jsonMessageConverter.setClassMapper(classMapper());
    return jsonMessageConverter;
  }

  @Bean
  public DefaultClassMapper classMapper() {
    DefaultClassMapper classMapper = new DefaultClassMapper();
    Map<String, Class<?>> idClassMapping = new HashMap<>();
    idClassMapping.put("task.JsonTaskResult", JsonTaskResult.class);
    classMapper.setIdClassMapping( idClassMapping );
    return classMapper;
  }

  @Bean
  @Primary
  public ObjectMapper objectMapper( Jackson2ObjectMapperBuilder builder) {
    ObjectMapper objectMapper = builder.build();
    objectMapper.registerModule( new JavaTimeModule() );
    objectMapper.configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    return objectMapper;
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange( AmqpConstants.EXCHANGE_OBJECTS );
  }

  @Bean Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with( ROUTING_KEY_PREFIX +"#");
  }

  public AmqpTemplate rabbitTemplate( ConnectionFactory connectionFactory ) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter( jsonMessageConverter() );
    return rabbitTemplate;
  }

  public static void main(String[] args) {
    Application.launch( MonitoringFXApp.class, args);
  }

}
