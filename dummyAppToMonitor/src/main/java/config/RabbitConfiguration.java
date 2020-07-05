package config;

import constants.AmqpConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.*;

@org.springframework.context.annotation.Configuration
@ComponentScan( basePackages = { "logProducer" })
@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource("classpath:log4j.properties")
})
@EnableRabbit
public class RabbitConfiguration {

  @Bean Queue queue() {
    return new Queue( AmqpConstants.QUEUE_NAME, false);
  }

  @Bean
  public MessageConverter jsonMessageConverter(){
    return new Jackson2JsonMessageConverter();
  }

  @Bean RabbitTemplate rabbitTemplate( ConnectionFactory connectionFactory ) {
    RabbitTemplate template = new RabbitTemplate( connectionFactory );
    template.setRoutingKey( AmqpConstants.ROUTING_KEY_PREFIX + "test" );
    template.setMessageConverter( jsonMessageConverter() );
    return template;
  }
}
