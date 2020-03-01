/**
 * 
 */
package com.frost.gcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.frost.gcp.model.payload.RequestPayload;
import com.frost.gcp.service.CaseService;

/**
 * @author jobin
 *
 */
@RestController
public class CaseController {

	@Autowired
	private CaseService caseService;

	@PostMapping("/create-case")
	public ResponseEntity<String> createCase(@RequestBody RequestPayload payload) {
		caseService.save(payload);
		return ResponseEntity.status(HttpStatus.OK).body("Case created succesfully");
	}

}
