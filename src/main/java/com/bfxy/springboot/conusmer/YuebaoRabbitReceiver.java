package com.bfxy.springboot.conusmer;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.bfxy.springboot.service.YuebaoService;
import com.rabbitmq.client.Channel;

@Component
public class YuebaoRabbitReceiver {
    @Autowired
	private YuebaoService yuebaoService;
    
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "queue-1", 
			durable="true"),
			exchange = @Exchange(value = "exchange-1", 
			durable="true", 
			type= "topic", 
			ignoreDeclarationExceptions = "true"),
			key = "springboot.*"
			)
	)
	@RabbitHandler
	public void onMessage(Message message, Channel channel) throws Exception {
		System.err.println("------------------onMessage--------------------");
		System.err.println("消费端Payload: " + message.getPayload());//获取消息体
		Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		//手工ACK
		channel.basicAck(deliveryTag, false);
	}
	
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "${spring.rabbitmq.listener.account.queue.name}", 
			durable="${spring.rabbitmq.listener.account.queue.durable}"),
			exchange = @Exchange(value = "${spring.rabbitmq.listener.account.exchange.name}", 
			durable="${spring.rabbitmq.listener.account.exchange.durable}", 
			type= "${spring.rabbitmq.listener.account.exchange.type}", 
			ignoreDeclarationExceptions = "${spring.rabbitmq.listener.account.exchange.ignoreDeclarationExceptions}"),
			key = "${spring.rabbitmq.listener.account.key}"
			)
	)
	@RabbitHandler
	public void onAmountMessage(@Payload com.bfxy.springboot.entity.AccountMessage account, 
			Channel channel, 
			@Headers Map<String, Object> headers) throws Exception {
		System.err.println("------------------onAmountMessage--------------------");
		if (account!=null) {
			System.err.println("消费端account: " + account.getMessageId()+",交易金额："+account.getAccount());
			try{
				
				yuebaoService.amountMessageHandler(account);
			}catch(Exception e){
				System.err.println("消息处理失败-->>"+e.toString());
				//在这里的异常可以具体的细分，如果是已经处理过的消息，则吧队列消息释放，并且吧修改确认表进行更改
				System.err.println("消息处理失败将t_message表中的status字段进行修改为1");
			}
		}
		Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
		//手工ACK
		channel.basicAck(deliveryTag, false);
	}
	
	
}
