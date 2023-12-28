package com.example.Notificationservice.Config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig1 {
    private final String QUEUE_1 ="notification_Key_1";
    private final String ROUTING_KEY_1 ="notification_Routing_Key_1";

    private final String EXCHANGE ="notification_Exchange";


    @Bean
    public Queue queue1(){
        return new Queue(QUEUE_1);
    }

    @Bean
    public TopicExchange exchange1(){
        return new TopicExchange(EXCHANGE);
    }


    @Bean
    public Binding bindings(){
        return BindingBuilder
                .bind(queue1())
                .to(exchange1())
                .with(ROUTING_KEY_1);
    }

}
