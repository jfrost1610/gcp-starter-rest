/**
 * 
 */
package com.frost.gcp.model.payload;

import lombok.Data;

/**
 * @author jobin
 *
 */
@Data
public class AccountsPayload {

	private String accountNumber;
	private String accountName;
	private String type;

}
