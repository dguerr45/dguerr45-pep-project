package DAO;

import java.sql.*;
import java.util.*;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    /**
     * WIll communicate with Database and insert a new entry into Message table.
     * @param message a Message object attempting insertion into Message table.
     * @return Will return the newly inserted entry as Message object if successful, null otherwise.
     */
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO Message(posted_by, message_text, time_posted_epoch) "
                       + "VALUES (?, ?, ?)";
            PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, message.getPosted_by());
            pStatement.setString(2, message.getMessage_text());
            pStatement.setLong(3, message.getTime_posted_epoch());

            pStatement.executeUpdate();
            ResultSet rs = pStatement.getGeneratedKeys();

            if(rs.next()){
                message.setMessage_id((int) rs.getInt("message_id"));
                return message;
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Will retrieve all messages from Message table
     * @return Will return any message entries from Message table as an ArrayList, or empty ArrayList
     *         if no message entries are found
     */
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        ArrayList<Message> allMessages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM Message";
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                Message tempMessage = new Message(rs.getInt("message_id"),
                                                  rs.getInt("posted_by"),
                                                  rs.getString("message_text"),
                                                  rs.getLong("time_posted_epoch"));
                allMessages.add(tempMessage);
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return allMessages;
    }

    /**
     * Will retrieve a message from Message table searching by message_id
     * @param message_id an int representing message's message_id
     * @return message associated with message_id, empty message otherwise
     */
    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        Message tempMessage = null;

        try{
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setInt(1, message_id);

            ResultSet rs = pStatement.executeQuery();

            if(rs.next()){
                tempMessage = new Message(rs.getInt("message_id"),
                                                  rs.getInt("posted_by"),
                                                  rs.getString("message_text"),
                                                  rs.getLong("time_posted_epoch"));
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return tempMessage;
    }

    /**
     * Will retrieve message from Message table searching by message_id, then delete it from
     * the database
     * @param message_id an int representing the message's message_id
     */
    public void deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setInt(1, message_id);

            pStatement.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Will update message_text in message from Message table
     * @param message_id int representing message's message_id
     * @param newMessageText the new message that should be read in message_text
     */
    public void updateMessageById(int message_id, String newMessageText){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, newMessageText);
            pStatement.setInt(2, message_id);

            pStatement.executeUpdate();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}