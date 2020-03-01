/**
 * 
 */
package com.frost.gcp.repo;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteBatch;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jobin
 *
 */
@Slf4j
@Repository
public class CaseRepo {

	@Autowired
	Firestore firestore;

	public DocumentReference getRootDocumentReference(String collectionName, String id) {
		return this.firestore.collection(collectionName).document(id);
	}

	public DocumentReference getChildDocumentReference(String collectionName, String id,
			DocumentReference rootDocumentReference) {
		return rootDocumentReference.collection(collectionName).document(id);
	}

	public WriteBatch getBatch() {
		return this.firestore.batch();
	}

	public void commit(WriteBatch batch) {

		try {
			batch.commit().get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Failed to commit to firestore!", e);
		}

	}

}
