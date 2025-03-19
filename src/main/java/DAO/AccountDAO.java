package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO{

    /**
     * Will communicate with Database and insert a new entry into Account table.
     * @param account an Account object attempting insertion into Account table
     * @return Will return the newly inserted entry as Account object if successful, null
     *         otherwise.
     */
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO Account(username, password) VALUES (?, ?)";
            PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, account.getUsername());
            pStatement.setString(2, account.getPassword());

            pStatement.executeUpdate();
            ResultSet rs = pStatement.getGeneratedKeys();
            if(rs.next()){
                account.setAccount_id((int) rs.getLong(1));
                return account;
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Will retrieve an account from Account table searching by username
     * @param username a String representing the account's username
     * @return will return the account associated with the username if it exists,
     *         null otherwise
     */
    public Account getAccountByName(String username){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, username);

            ResultSet rs = pStatement.executeQuery();

            if(rs.next()){
                Account temp = new Account(rs.getInt("account_id"), rs.getString("username"),
                                            rs.getString("password"));
                return temp;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}