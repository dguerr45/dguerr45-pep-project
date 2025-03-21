package Service;

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
}