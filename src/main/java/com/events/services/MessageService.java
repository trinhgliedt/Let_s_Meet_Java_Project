package com.events.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.events.models.Message;
import com.events.repositories.MessageRepository;


@Service
public class MessageService {
	private final MessageRepository messageRepository;
	
	public MessageService(MessageRepository MessageRepository) {
		this.messageRepository = MessageRepository;
	}
	
	// Retrieve all Messages
	public List<Message> allMessages() {
		return messageRepository.findAll();
	}
	
	// Create a Message
	public Message createMessage(Message myMessage) {
		return messageRepository.save(myMessage); // Create new record with this Message
	}
	
	// Read a Message
	public Message readMessage(Long id) {
		// Find Message, if possible
		Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent()) { // If not null
            return optionalMessage.get(); // Return found Message
        } else {
            return null;
        }
	}
	
	// Update a Message
	public Message updateMessage(Message myMessage) {
		return messageRepository.save(myMessage);
	}
	
	// Delete a Message
	public void deleteMessage(Long id) {
		if (messageRepository.existsById(id)) { // If Message with id exists
			messageRepository.deleteById(id); // Delete it
		} // If not, do nothing
	}
}