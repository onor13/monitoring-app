package config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@org.springframework.context.annotation.Configuration
@ComponentScan( basePackages = { "producers" })
@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource("classpath:log4j.properties")
})
@EnableRabbit
public class RabbitConfiguration {

  @Value("${jsa.rabbitmq.queue}")
  String queueName;

  @Value("${jsa.rabbitmq.exchange}")
  private String exchange;

  @Value("${jsa.rabbitmq.routingKey}")
  private String routingkey;

  @Value("${jsa.rabbitmq.connectionFactory.address}")
  private String connectionFactoryAddress;

  @Bean Queue queue() {
    return new Queue( queueName, false);
  }

  @Bean
  public MessageConverter jsonMessageConverter(){
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    connectionFactory.setAddresses( connectionFactoryAddress );
    //connectionFactory.setUsername(username);
   // connectionFactory.setPassword(password);
    return connectionFactory;
  }

  @Bean RabbitTemplate rabbitTemplate( ConnectionFactory connectionFactory ) {
    RabbitTemplate template = new RabbitTemplate( connectionFactory );
    template.setRoutingKey( routingkey );
    template.setMessageConverter( jsonMessageConverter() );
    return template;
  }
}
