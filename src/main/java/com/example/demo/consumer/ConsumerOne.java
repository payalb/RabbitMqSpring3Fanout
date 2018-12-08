package com.example.demo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
/*Doing a task can take a few seconds. You may wonder what happens if one of the consumers starts a long task and dies with it only partly done. Spring AMQP by default takes a conservative approach to message acknowledgement. If the listener throws an exception the container calls:
channel.basicReject(deliveryTag, requeue)
Requeue is true by default unless you explicitly set:
defaultRequeueRejected=false
or the listener throws an AmqpRejectAndDontRequeueException. This is typically the behavior you want from your listener. In this mode there is no need to worry about a forgotten acknowledgement. After processing the message the listener calls:
channel.basicAck()
Acknowledgement must be sent on the same channel the delivery was received on. Attempts to acknowledge using a different channel will result in a channel-level protocol exception. */
@RabbitListener(queues="queue1")
public class ConsumerOne {
	
/*	private final int instance;
	public ConsumerOne(int i) {
		this.instance=i;
	}*/

	/*annotate our receive method with @RabbitHandler passing in the payload that has been pushed to the queue.*/
	@RabbitHandler
    public void receive(String in) throws InterruptedException {
		  System.out.println(" [x] Received '" + in );
	        Thread.sleep(5000);
	        System.out.println("Processed by "+ this.getClass().getName()
	        		);
    }

}
