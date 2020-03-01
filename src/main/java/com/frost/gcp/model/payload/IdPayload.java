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
public class IdPayload {

	private String fName;
	private String lName;
	private String dob;
	private List<AddressPayload> addresses;
	private List<OtherNamePayload> otherNames;

}
