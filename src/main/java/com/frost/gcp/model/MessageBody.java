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
public class MessageBody {

	private String content;
	private String sender;
	private String timeStamp;

}
