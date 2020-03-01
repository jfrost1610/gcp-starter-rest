/**
 * 
 */
package com.frost.gcp.model;

import lombok.Data;

/**
 * @author jobin
 *
 */
@Data
public class MessageUser {

	private String name;
	private String timeStamp;

	/**
	 * @param name
	 * @param timeStamp
	 */
	public MessageUser(MessageBody body) {
		this.name = body.getSender();
		this.timeStamp = body.getTimeStamp();
	}

}
