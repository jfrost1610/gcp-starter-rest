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
public class Id {

	private String caseId;
	private String itemNo;
	private String fName;
	private String lName;
	private String dob;

	public String documentId() {
		return caseId + itemNo;
	}

}
