package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postUserHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);

        return app;
    }

    /**
     * Handler to post a new User Account.
     * Jackson ObjectMapper converts JSON from POST request to Account object.
     * If accountSerivce returns null, then posting User Account is unsuccessful and API will return 400 message.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account newAccount = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = null;
        
        /**
         * Going through conditions to create new Account:
         * - username is not empty
         * - password is at least 4 characters long
         * - username doesn't already exist
         */
        if( !newAccount.getUsername().isEmpty() && newAccount.getPassword().length() >= 4 && 
            accountService.getAccountByName(newAccount.getUsername()) == null){
            addedAccount = accountService.addAccount(newAccount);
        }
        

        if(addedAccount != null){
            ctx.status(200).json( mapper.writeValueAsString(addedAccount) );
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler to verify authentication of an entered account.
     * Jackson ObjectMapper converts JSON from POST request to Account object.
     * If user is able to successfully authenticate by entering valid username/password,
     * then API will return account with account_id as JSON and 200 status.
     * Otherwise, will return 401 status.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account verifyAccount = mapper.readValue(ctx.body(), Account.class);

        /**
         * Verifying if account exists and that entered password matches password stored
         * in database. If Yes to both, then we have successful authentication
         */
        Account databaseAccount = accountService.getAccountByName(verifyAccount.getUsername());
        if(databaseAccount != null &&
           verifyAccount.getPassword().equals(databaseAccount.getPassword())){
            ctx.status(200).json(mapper.writeValueAsString(databaseAccount));

        // Account doesn't exist in database or password doesn't match
        } else {
            ctx.status(401);
        }
    }

    /**
     * Handler to submit a new post to the database.
     * Jackson ObjectMapper converts JSON from POST request to Account object.
     * If message is valid and meets criteria, then API will return message with message_id as JSON and 200 status.
     * Otherwise, will return 400 status.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = null;

        /**
         * Going through conditions to create new Message:
         * - message_text is not blank
         * - message_text is under 255 characters
         * - posted_by refers to an existing user
         */
        if( !newMessage.getMessage_text().isEmpty() &&
            newMessage.getMessage_text().length() < 255 &&
            accountService.getAccountById(newMessage.getPosted_by()) != null)
        {
            addedMessage = messageService.addMessage(newMessage);
        }

        if(addedMessage != null){
            ctx.status(200).json( mapper.writeValueAsString(addedMessage));
        } else {
            ctx.status(400);
        }
    }

    // /**
    //  * This is an example handler for an example endpoint.
    //  * @param context The Javalin Context object manages information about both the HTTP request and response.
    //  */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }
}