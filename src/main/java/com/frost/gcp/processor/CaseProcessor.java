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

import com.frost.gcp.model.payload.RequestPayload;
import com.frost.gcp.service.CaseService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jobin
 *
 */
@Slf4j
@Component
public class CaseProcessor {

	@Autowired
	private Gson gson;

	@Autowired
	private CaseService caseService;

	@ServiceActivator(inputChannel = "casePubsubInputChannel")
	public void messageReceiver(
			@Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage originalMessage,
			String payload) {

		log.info("Payload arrived wit id :: {} ", originalMessage.getPubsubMessage().getMessageId());

		try {

			RequestPayload body = gson.fromJson(payload, RequestPayload.class);
			caseService.save(body);

			log.info("Case saved successfully!");

		} catch (JsonSyntaxException e) {
			log.error("Failed to parse the messaage!", e);
		}

		originalMessage.ack();

	}

}
