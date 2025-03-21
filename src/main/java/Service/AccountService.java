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
     * @param account an Account object
     * @return the newly created Account if successful, null otherwise
     */
    public Account addAccount(Account account){
        return this.accountDAO.insertAccount(account);
    }

    /**
     * Will retrieve an account from database searching by username.
     * @param username a String representing the account's username
     * @return will return the account associated with the username if it exists,
     *         null otherwise
     */
    public Account getAccountByName(String username){
        return this.accountDAO.getAccountByName(username);
    }

    /**
     * Will retrieve an account from database searching by id.
     * @param id an int representing the account's id
     * @return will return the account associated with the id if it exists,
     *         otherwise null
     */
    public Account getAccountById(int id){
        return this.accountDAO.getAccountById(id);
    }
}