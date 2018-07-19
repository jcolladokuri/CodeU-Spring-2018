package codeu.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.omg.CORBA.portable.InputStream;

import codeu.model.data.Activity;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ActivityStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;

/**
 * Servlet implementation class GeneratingData
 */
public class GeneratingData extends HttpServlet {
    /** Store class that gives access to Conversations. */
    private ConversationStore conversationStore;
    /** Store class that gives access to Messages. */
    private MessageStore messageStore;
    /** Store class that gives access to Users. */
    private UserStore userStore;
    
	private String[] users = new String[]{"boss", "manager", "ted", "frank", "linda", "exuser"};


    /** Set up state for handling chat requests. */
    @Override
    public void init() throws ServletException {
      super.init();
      setConversationStore(ConversationStore.getInstance());
      setMessageStore(MessageStore.getInstance());
      setUserStore(UserStore.getInstance());
    }
    
    void setConversationStore(ConversationStore conversationStore) {
        this.conversationStore = conversationStore;
    }
    
    void setMessageStore(MessageStore messageStore) {
        this.messageStore = messageStore;
    }
    
    void setUserStore(UserStore userStore){
    	this.userStore = userStore;
    }
    
      
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext cntxt = getServletContext();
		FileInputStream fstream = 
                new FileInputStream(getServletContext().getRealPath("/datafile"));	
		
		//six mock users - will randomly be assigned a message
		for(String user: users){
		  newUser(user);
		}
		
		//one convo for now - can expand later
		String conversation = "codeu_faq";

	    newPrivateConvo("boss", "codeu_faq");
	    
		BufferedReader br = new BufferedReader((new InputStreamReader(fstream)));
		String line = br.readLine();
		List<String> messages = new ArrayList<String>();
		while(line !=null){
			messages.add(br.readLine());
			line = br.readLine();
		}
		Random rand = new Random();
		for(int i = 0; i <20; i++){
		  newMessage(users[rand.nextInt(5)], conversation, messages.get(rand.nextInt(38)));
		}
	  
	}
	
	public void newUser(String username){
		String hashed =BCrypt.hashpw("1234", BCrypt.gensalt());
		User newUser = new User(UUID.randomUUID(), username , hashed, Instant.now());
	    userStore.addUser(newUser);
	}
	public void newConvo(String username, String title){
		User user = userStore.getUser(username);
		Conversation newConvo =
		            new Conversation(UUID.randomUUID(), user.getId(), title, Instant.now());
		conversationStore.addConversation(newConvo);
	}
	public void newPrivateConvo(String username, String title){
		User user = userStore.getUser(username);
		Conversation newConvo =
		            new Conversation(UUID.randomUUID(), user.getId(), title, Instant.now(), true);
		for(String name: users){
		  User users = userStore.getUser(name);
		  newConvo.addUserToWhitelist(users);
		}
		conversationStore.addConversation(newConvo);
	}
	public void newMessage(String username, String title, String message){
		User user = userStore.getUser(username);
		Conversation convo = conversationStore.getConversationWithTitle(title);
		Message newMessage = new Message(UUID.randomUUID(), convo.getId(), user.getId(), message, Instant.now());
		    messageStore.addMessage(newMessage);
	}
	
	

}
