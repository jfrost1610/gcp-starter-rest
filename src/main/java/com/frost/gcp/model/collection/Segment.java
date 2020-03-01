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
public class Segment {

	private String caseId;
	private String itemNo;
	private String type;
	private String accountNumber;
	private String code;

	public String documentId() {
		return caseId + itemNo;
	}

}
