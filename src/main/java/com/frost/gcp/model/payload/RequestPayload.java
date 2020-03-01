/**
 * 
 */
package com.frost.gcp.model.payload;

import java.util.List;

import lombok.Data;

/**
 * @author jobin
 *
 */
@Data
public class RequestPayload {

	private String caseId;
	private String status;
	private String cDate;
	private IdPayload id;
	private List<AccountsPayload> accounts;

}
