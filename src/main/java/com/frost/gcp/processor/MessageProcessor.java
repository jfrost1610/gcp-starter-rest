/**
 * 
 */
package com.frost.gcp.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frost.gcp.model.MessageBody;
import com.frost.gcp.repo.MessageRepo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jobin
 *
 */
@Slf4j
@Component
public class MessageProcessor {

	@Autowired
	private MessageRepo messageRepo;

	@ServiceActivator(inputChannel = "starterPubsubInputChannel")
	public void messageReceiver(
			@Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage originalMessage,
			String payload) {

		log.info("Payload arrived wit id :: {} ", originalMessage.getPubsubMessage().getMessageId());

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			MessageBody body = objectMapper.readValue(payload, MessageBody.class);
			messageRepo.save(body);
		} catch (JsonProcessingException e) {
			log.error("Failed to parse the messaage!", e);
		}

		originalMessage.ack();

	}

}
