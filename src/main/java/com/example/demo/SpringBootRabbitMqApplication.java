package com.example.demo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.demo.consumer.ConsumerOne;
import com.example.demo.consumer.ConsumerTwo;
import com.example.demo.producer.ProducerOne;
/*In this part we'll implement the fanout pattern to deliver a message to multiple consumers. This pattern is also known as "publish/subscribe" and is implemented by configuring a number of beans .
*/
@SpringBootApplication
@EnableScheduling
public class SpringBootRabbitMqApplication {
	
	/*Published messages are going to be broadcast to all the receivers.*/
	@Bean
	@Profile("Sender")
	public ProducerOne producer() {
		return new ProducerOne();
	}
	
	@Bean
	@Profile("Receiver")
	public ConsumerOne consumer1() {
		return new ConsumerOne();
	}
	
	@Bean
	@Profile("Receiver")
	public ConsumerTwo consumer2() {
		return new ConsumerTwo();
	}
	/*An exchange is a very simple thing. On one side it receives messages from producers and the other side it pushes them to queues. The exchange must know exactly what to do with a message it receives. Should it be appended to a particular queue? Should it be appended to many queues? Or should it get discarded. The rules for that are defined by the exchange type.
	There are a few exchange types available: direct, topic, headers and fanout. We'll focus on the last one -- the fanout. 
	broadcasts all the messages it receives to all the queues it knows. And that's exactly what we need for fanning out our messages.
	 */	
	
	@Bean
	public FanoutExchange exchange() {
		return new FanoutExchange("app.fanout");
	}
	/*AnonymousQueues (non-durable, exclusive, auto-delete queues in AMQP terms*/
	@Bean
	@Profile("Receiver")
	public Queue queue1() {
		return new Queue("queue1");
	}
	/*We're interested only in currently flowing messages not in the old ones. 
	 * So not necessary to name a queue
*/	@Bean
	@Profile("Receiver")
	public Queue queue2() {
		return new Queue("queue2");
	}
@Profile("Receiver")

	@Bean
	public Binding binding1() {
		return BindingBuilder.bind(queue1()).to(exchange());
	}
@Profile("Receiver")

	@Bean
	public Binding binding2() {
		return BindingBuilder.bind(queue2()).to(exchange());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRabbitMqApplication.class, args);
	}
}
