/**
 * 
 */
package com.frost.gcp.model.collection;

import com.frost.gcp.model.payload.RequestPayload;

import lombok.Data;

/**
 * @author jobin
 *
 */
@Data
public class Case {

	private String caseId;
	private String status;
	private String cDate;

	public String documentId() {
		return caseId;
	}

	public Case(RequestPayload payload) {
		this.caseId = payload.getCaseId();
		this.status = payload.getStatus();
		this.cDate = payload.getCDate();
	}

}
