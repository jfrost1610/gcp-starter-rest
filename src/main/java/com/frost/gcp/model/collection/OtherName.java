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
public class OtherName {

	private String caseId;
	private String itemNo;
	private String fName;
	private String lName;

	public String documentId() {
		return caseId + itemNo;
	}

}
