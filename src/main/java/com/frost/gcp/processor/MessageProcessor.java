/**
 * 
 */
package com.frost.gcp.processor;

import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jobin
 *
 */
@Slf4j
@Component
public class MessageProcessor {

	@Bean
	@ServiceActivator(inputChannel = "starterPubsubInputChannel")
	public MessageHandler messageReceiver() {
		return message -> {
			log.info("Message arrived! Payload: " + new String((byte[]) message.getPayload()));
			BasicAcknowledgeablePubsubMessage originalMessage = message.getHeaders()
					.get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
			originalMessage.ack();
		};
	}

}
