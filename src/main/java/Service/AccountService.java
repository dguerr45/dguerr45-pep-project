package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Used with mock AccountDAO that exhibits mock behavior in test cases.
     * Allows for testing of AccountService independently of AccountDAO.
     * @param authorDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * Will create an account in Database. ID is not given.
     * @param account an account object
     * @return the newly created account if successful
     */
    public Account addAccount(Account account){
        return this.accountDAO.insertAccount(account);
    }

    /**
     * Will retrieve an account searching by username
     * @param username a String representing the account's username
     * @return will return the account associated with the username if it exists,
     *         null otherwise
     */
    public Account getAccountByName(String username){
        return this.accountDAO.getAccountByName(username);
    }
}