package com.example.demo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues="queue2")
public class ConsumerTwo{
	
	/*annotate our receive method with @RabbitHandler passing in the payload that has been pushed to the queue.*/
	@RabbitHandler
    public void receive(String in) throws InterruptedException {
        System.out.println(" [x] Received '" + in );
        Thread.sleep(5000);
        System.out.println("Processed by "+ this.getClass().getName()
        		);
    }

}