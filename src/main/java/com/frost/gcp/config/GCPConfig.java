/**
 * 
 */
package com.frost.gcp.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

/**
 * @author jobin
 *
 */
@Configuration
public class GCPConfig {

	@Value("${gcp.resources.pubsub.subscription.starter}")
	private String starterSub;

	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapter(
			@Qualifier("starterPubsubInputChannel") MessageChannel inputChannel, PubSubTemplate pubSubTemplate) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, starterSub);
		adapter.setOutputChannel(inputChannel);
		adapter.setAckMode(AckMode.MANUAL);
		return adapter;
	}

	@Bean
	public MessageChannel starterPubsubInputChannel() {
		return new DirectChannel();
	}

}
