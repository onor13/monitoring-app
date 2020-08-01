package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import receivers.TasksResultsReceiver;
import task.JsonTaskResult;

@ComponentScan(basePackages = {"producers"})
@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource("classpath:log4j.properties")
})
@EnableJpaRepositories
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class RabbitConfiguration {

  @Value("${jsa.rabbitmq.exchange}")
  private String exchange;

  @Value("${jsa.rabbitmq.connectionFactory.address}")
  private String connectionFactoryAddress;

  @Bean
  public Queue autoDeleteQueue1() {
    return new AnonymousQueue();
  }

  @Bean
  public Queue autoDeleteQueue2() {
    return new AnonymousQueue();
  }

  @Bean
  public Binding binding1(FanoutExchange fanoutExchange,
                          Queue autoDeleteQueue1) {
    return BindingBuilder.bind(autoDeleteQueue1).to(fanoutExchange);
  }

  @Bean
  public Binding binding2(FanoutExchange fanoutExchange,
                          Queue autoDeleteQueue2) {
    return BindingBuilder.bind(autoDeleteQueue2).to(fanoutExchange);
  }

  @Bean
  public FanoutExchange fanoutExchange() {
    return new FanoutExchange(exchange);
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    connectionFactory.setAddresses(connectionFactoryAddress);
    return connectionFactory;
  }

  @Bean
  RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setExchange(exchange);
    //  template.setRoutingKey(routingkey);
    template.setMessageConverter(jsonMessageConverter());
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
  public Jackson2ObjectMapperBuilder builder() {
    return new Jackson2ObjectMapperBuilder();
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
  TasksResultsReceiver connector() {
    return new TasksResultsReceiver(autoDeleteQueue1(), autoDeleteQueue2());
  }
}
