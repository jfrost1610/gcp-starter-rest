/**
 * 
 */
package com.frost.gcp.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frost.gcp.model.collection.Account;
import com.frost.gcp.model.collection.Address;
import com.frost.gcp.model.collection.Case;
import com.frost.gcp.model.collection.Id;
import com.frost.gcp.model.collection.OtherName;
import com.frost.gcp.model.collection.Segment;
import com.frost.gcp.model.payload.AccountsPayload;
import com.frost.gcp.model.payload.AddressPayload;
import com.frost.gcp.model.payload.IdPayload;
import com.frost.gcp.model.payload.OtherNamePayload;
import com.frost.gcp.model.payload.RequestPayload;
import com.frost.gcp.repo.CaseRepo;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteBatch;

/**
 * @author jobin
 *
 */
@Service
public class CaseService {

	@Autowired
	private CaseRepo caseRepo;

	public void save(RequestPayload payload) {

		initialize(payload.getCaseId());

		WriteBatch batch = caseRepo.getBatch();

		Case caseDocument = new Case(payload);
		DocumentReference caseDocumentReference = caseRepo.getRootDocumentReference("case", caseDocument.documentId());
		batch.create(caseDocumentReference, caseDocument);

		List<Segment> segments = populateSegments(payload, caseDocumentReference, batch);

		segments.forEach(segmentDocument -> {
			DocumentReference segDocumentReference = caseRepo.getChildDocumentReference("segment",
					segmentDocument.documentId(), caseDocumentReference);
			batch.create(segDocumentReference, segmentDocument);
		});

		caseRepo.commit(batch);

	}

	private List<Segment> populateSegments(RequestPayload payload, DocumentReference caseDocumentReference,
			WriteBatch batch) {

		List<Segment> segments = new ArrayList<>();

		segments.addAll(populateId(payload.getId(), caseDocumentReference, batch));
		segments.addAll(populateAccounts(payload.getAccounts(), caseDocumentReference, batch));

		return segments;
	}

	private List<Segment> populateId(IdPayload id, DocumentReference caseDocumentReference, WriteBatch batch) {

		String itemNbr = getItemNbr();

		Id idDocument = new Id();
		idDocument.setCaseId(getCaseId());
		idDocument.setItemNo(itemNbr);
		idDocument.setLName(id.getLName());
		idDocument.setFName(id.getFName());
		idDocument.setDob(id.getDob());

		DocumentReference idDocumentReference = caseRepo.getChildDocumentReference("id", idDocument.documentId(),
				caseDocumentReference);
		batch.create(idDocumentReference, idDocument);

		List<Segment> segments = new ArrayList<>();

		segments.addAll(populateAddresses(id.getAddresses(), caseDocumentReference, batch));
		segments.addAll(populateOtherNames(id.getOtherNames(), caseDocumentReference, batch));

		Segment segment = populateDefaultSegment(itemNbr);
		segment.setType("ID");
		segment.setAccountNumber("");
		segment.setCode("99999");
		segments.add(segment);

		return segments;
	}

	private List<Segment> populateOtherNames(List<OtherNamePayload> otherNames, DocumentReference caseDocumentReference,
			WriteBatch batch) {

		List<Segment> segments = new ArrayList<>();

		otherNames.forEach(names -> {

			String itemNbr = getItemNbr();

			OtherName addressDocument = new OtherName();
			addressDocument.setCaseId(getCaseId());
			addressDocument.setItemNo(itemNbr);
			addressDocument.setLName(names.getLName());
			addressDocument.setFName(names.getFName());

			DocumentReference otherNameDocument = caseRepo.getChildDocumentReference("other-name",
					addressDocument.documentId(), caseDocumentReference);
			batch.create(otherNameDocument, addressDocument);

			Segment segment = populateDefaultSegment(itemNbr);
			segment.setType("ON");
			segment.setAccountNumber("");
			segment.setCode("78");
			segments.add(segment);

		});

		return segments;
	}

	private List<Segment> populateAddresses(List<AddressPayload> addresses, DocumentReference caseDocumentReference,
			WriteBatch batch) {

		List<Segment> segments = new ArrayList<>();

		addresses.forEach(account -> {

			String itemNbr = getItemNbr();

			Address addressDocument = new Address();
			addressDocument.setCaseId(getCaseId());
			addressDocument.setItemNo(itemNbr);
			addressDocument.setLine(account.getLine());
			addressDocument.setCity(account.getCity());
			addressDocument.setState(account.getState());
			addressDocument.setZip(account.getZip());
			addressDocument.setType(account.getType());

			DocumentReference addressDocumentReference = caseRepo.getChildDocumentReference("address",
					addressDocument.documentId(), caseDocumentReference);
			batch.create(addressDocumentReference, addressDocument);

			Segment segment = populateDefaultSegment(itemNbr);
			segment.setType(account.getType());
			segment.setAccountNumber("");
			segment.setCode("45");
			segments.add(segment);

		});

		return segments;
	}

	private List<Segment> populateAccounts(List<AccountsPayload> accounts, DocumentReference caseDocumentReference,
			WriteBatch batch) {

		List<Segment> segments = new ArrayList<>();

		accounts.forEach(account -> {

			String itemNbr = getItemNbr();

			Account accountDocument = new Account();
			accountDocument.setCaseId(getCaseId());
			accountDocument.setItemNo(itemNbr);
			accountDocument.setAccountNumber(account.getAccountNumber());
			accountDocument.setAccountName(account.getAccountName());
			accountDocument.setType(account.getType());

			DocumentReference accountDocumentReference = caseRepo.getChildDocumentReference(
					account.getType().equals("TC") ? "tc" : "cl", accountDocument.documentId(), caseDocumentReference);
			batch.create(accountDocumentReference, accountDocument);

			Segment segment = populateDefaultSegment(itemNbr);
			segment.setType(account.getType());
			segment.setAccountNumber(account.getAccountNumber());
			segment.setCode("2");
			segments.add(segment);

		});

		return segments;
	}

	private Segment populateDefaultSegment(String itemNbr) {
		Segment segment = new Segment();
		segment.setCaseId(getCaseId());
		segment.setItemNo(itemNbr);
		return segment;
	}

	private void initialize(String caseId) {
		MDC.put("itemNbr", "1");
		MDC.put("caseId", caseId);
	}

	private String getItemNbr() {

		String itemNbrString = MDC.get("itemNbr");

		Integer itemNbr = Integer.parseInt(itemNbrString);
		Integer nextItemNbr = itemNbr + 1;
		MDC.put("itemNbr", String.valueOf(nextItemNbr));

		return itemNbrString;

	}

	private String getCaseId() {
		return MDC.get("caseId");
	}

}
