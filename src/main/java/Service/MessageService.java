package Service;

import java.util.*;

import Model.Message;
import DAO.MessageDAO;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Used with mock MessageDAO that exhibits mock behavior in test cases.
     * Allows for testing of MessageService independently of MessageDAO.
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    /**
     * Will create a message in Database. ID is not given.
     * @param message a Message object
     * @return the newly created Message if successful, null otherwise
     */
    public Message addMessage(Message message){
        return this.messageDAO.insertMessage(message);
    }

    /**
     * Will return all messages from Database.
     * @return all messages as ArrayList
     */
    public List<Message> getAllMessages(){
        return this.messageDAO.getAllMessages();
    }

    /**
     * Will return single message from Database searching by message_id
     * @param message_id an int representing message's message_id
     * @return will return the message associated with message_id, empty message otherwise
     */
    public Message getMessageById(int message_id){
        return this.messageDAO.getMessageById(message_id);
    }
}