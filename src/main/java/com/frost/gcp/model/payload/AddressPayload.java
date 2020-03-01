package com.frost.gcp.model.payload;

import lombok.Data;

/**
 * @author jobin
 *
 */
@Data
public class AddressPayload {
	
	private String line;
	private String state;
	private String city;
	private String zip;
	private String type;

}
