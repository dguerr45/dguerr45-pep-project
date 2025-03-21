package DAO;

import java.sql.*;

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
}