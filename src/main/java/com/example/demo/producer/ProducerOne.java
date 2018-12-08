package com.example.demo.producer;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/*Now we'll be sending strings that stand for complex tasks. We don't have a real-world task, like images to be resized or PDF files to be rendered, so let's fake it by just pretending we're busy - by using the Thread.sleep() function. We'll take the number of dots in the string as its complexity; every dot will account for one second of "work". For example, a fake task described by Hello... will take three seconds.*/
public class ProducerOne {

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private FanoutExchange exchange;
	/*The messages will be lost if no queue is bound to the exchange yet, but that's okay for us; if no consumer is listening yet we can safely discard the message.
*/	@Scheduled(fixedDelay = 1000, initialDelay = 500)
	public void send() {
		String message = "Hello World!";
		/* We need to supply a routingKey when sending, but its value is ignored for fanout exchanges. */
		this.template.convertAndSend(exchange.getName(),"", message);
		System.out.println(" [x] Sent '" + message + "'");
	}

}
