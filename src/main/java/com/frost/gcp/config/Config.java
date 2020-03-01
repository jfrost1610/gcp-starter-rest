/**
 * 
 */
package com.frost.gcp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

/**
 * @author jobin
 *
 */
@Configuration
public class Config {

	@Bean
	public Gson getGson() {
		return new Gson();
	}

}
