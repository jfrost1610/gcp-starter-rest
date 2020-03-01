/**
 * 
 */
package com.frost.gcp.model.collection;

import lombok.Data;

/**
 * @author jobin
 *
 */
@Data
public class Address {

	private String caseId;
	private String itemNo;
	private String line;
	private String state;
	private String city;
	private String zip;
	private String type;

	public String documentId() {
		return caseId + itemNo;
	}

}
