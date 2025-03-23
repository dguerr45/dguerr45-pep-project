package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO{
    /**
     * Will communicate with Database and insert a new entry into Account table.
     * @param account an Account object attempting insertion into Account table
     * @return newly inserted entry as Account object if successful, null otherwise.
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
                account.setAccount_id(rs.getInt("account_id"));
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
     * @return account associated with the username if it exists,
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
                Account tempAccount = new Account(rs.getInt("account_id"), 
                                                  rs.getString("username"),
                                                  rs.getString("password"));
                return tempAccount;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Will retrieve an account from Account table searching by id
     * @param id an int representing the account's account_id
     * @return the account associated with given account_id, null otherwise
     */
    public Account getAccountById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setInt(1, id);

            ResultSet rs = pStatement.executeQuery();

            if(rs.next()){
                Account tempAccount = new Account(rs.getInt("account_id"),
                                                  rs.getString("username"),
                                                  rs.getString("password"));
                return tempAccount;
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}