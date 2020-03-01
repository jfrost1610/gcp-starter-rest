/**
 * 
 */
package com.frost.gcp.repo;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frost.gcp.model.MessageBody;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jobin
 *
 */
@Slf4j
@Repository
public class MessageRepo {

	@Autowired
	Firestore firestore;

	public void save(MessageBody data) {

		try {

			data.setTimeStamp(LocalDateTime.now().toString());
			DocumentReference doc = this.firestore.collection("message").document();
			doc.create(data).get();
			log.info("Message saved in collection with id: {}!", doc.getId());

		} catch (InterruptedException | ExecutionException e) {
			log.error("Failed to save message in collection!", e);
		}
	}

}
