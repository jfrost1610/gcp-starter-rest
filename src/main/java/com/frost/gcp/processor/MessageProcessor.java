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

import com.frost.gcp.model.MessageBody;
import com.frost.gcp.repo.MessageRepo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jobin
 *
 */
@Slf4j
@Component
public class MessageProcessor {

	@Autowired
	private Gson gson;

	@Autowired
	private MessageRepo messageRepo;

	@ServiceActivator(inputChannel = "starterPubsubInputChannel")
	public void messageReceiver(
			@Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage originalMessage,
			String payload) {

		log.info("Payload arrived wit id :: {} ", originalMessage.getPubsubMessage().getMessageId());

		try {

			MessageBody body = gson.fromJson(payload, MessageBody.class);
			messageRepo.save(body);

		} catch (JsonSyntaxException e) {
			log.error("Failed to parse the messaage!", e);
		}

		originalMessage.ack();

	}

}
