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
public class Account {

	private String caseId;
	private String itemNo;
	private String accountNumber;
	private String accountName;
	private String type;

	public String documentId() {
		return caseId + itemNo;
	}

}
