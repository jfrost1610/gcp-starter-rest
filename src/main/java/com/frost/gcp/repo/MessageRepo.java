/**
 * 
 */
package com.frost.gcp.repo;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frost.gcp.model.MessageBody;
import com.frost.gcp.model.MessageUser;
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

			DocumentReference messageDocument = this.firestore.collection("message").document();
			messageDocument.create(data).get();

			log.info("Message saved in collection with id: {}!", messageDocument.getId());

			MessageUser user = new MessageUser(data);
			DocumentReference userDocument = messageDocument.collection("user").document();
			userDocument.create(user).get();

			log.info("User saved in collection with id: {}!", userDocument.getId());

		} catch (InterruptedException | ExecutionException e) {
			log.error("Failed to save message in collection!", e);
		}
	}

}
