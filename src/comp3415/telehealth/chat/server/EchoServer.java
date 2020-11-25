package comp3415.telehealth.chat.server;

import comp3415.telehealth.chat.common.*;
import comp3415.telehealth.chat.ocsf.server.*;

import java.io.IOException;

/**
 * COMP 3415 Software Engineering
 * Personal Project Phase 1
 * Ian Cuninghame 0670401
 * October 12, 2020
 * Lakehead University
 */

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 */
public class EchoServer extends AbstractServer 
{
  // INTERFACE VARIABLES

  ChatIF serverUI;

  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port, ChatIF serverUI)
  {
    super(port);
    this.serverUI = serverUI;
  }
  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient (Object msg, ConnectionToClient client)
  {
    // Parse the message:
    String message = msg.toString();

    // Check to see if command syntax was entered. If so, goes to handleCommandFromClient:
    if (message.matches("#\\w+") || message.matches("#\\w+\\s\\S+")) {
      handleCommandFromClient(message, client);
      return;
    }

    // If the user is not logged in, send them an error message and close their connection:
    if (client.getInfo("login") == null){
      try {
        client.sendToClient("You must be logged in to do that. Closing connection.");
        client.close();
      } catch (IOException e) {
        System.out.println("ERROR: couldn't terminate connection with client.");
      }
      return;
    }

    // Otherwise, display the message and echo it to all clients:
    serverUI.display("[" + client.getId() + "] " + client.getInfo("login") + " > " + msg);
    this.sendToAllClients(client.getInfo("login") + " > " + msg);

  }

  /**
   * This method handles all commands incoming from the client. (messages from ChatClient.sendToServer())
   *
   * @param message The command, including the # prefix, from the client.
   */
  public void handleCommandFromClient(String message, ConnectionToClient client)
  {

    // Log the command received in the server console.
    System.out.println("Command received from User " + client.getId() + ": " + message);

    // #login <LOGIN_ID>:
    if (message.matches("#login\\s\\S+")){ //Check for proper syntax
      // Give an error if the command is received after the user is already logged in:
      if (client.getInfo("login") != null) {
        System.out.println("ERROR: #login command received from an already logged-in user.");
        try { client.sendToClient("ERROR: You are already logged in!"); } catch (IOException e) { System.out.println("ERROR: couldn't communicate with client."); }
        return; // Cancel the login process
      }
      String[] arr = message.split("\\s"); //Split the command into an array to get parameters
      client.setInfo("login", arr[1]); // Uses the first parameter after the command name as the LOGIN_ID
      System.out.println("User #" + client.getId() + " logged in as " + client.getInfo("login"));
    }
  }

  /**
   * This method handles all data coming from the Server UI. (ServerConsole class)
   *
   * @param message The message from the UI.
   */
  public void handleMessageFromServerUI(String message)
  {
    // Check to see if a command was entered. If so, calls handleCommandFromServerUI
    if (message.matches("#\\w+") || message.matches("#\\w+\\s\\S+"))
      handleCommandFromServerUI(message);
    else { // Otherwise, sends the message out to all the clients
      serverUI.display("SERVER > " + message);
      sendToAllClients("SERVER > " + message);
    }

  }

  /**
   * This method handles all admin commands coming from the Server UI. (ServerConsole class)
   *
   * @param message The command, including the # prefix, from the UI.
   */
  public void handleCommandFromServerUI(String message)
  {
    //#setport
    if (message.matches("#setport\\s\\d+")){ //Check for proper syntax
      String[] arr = message.split("\\s"); //Split the command into an array to get parameters
      setPort(Integer.parseInt(arr[1])); // Using the first parameter, sets the server port to user input.
    }
    //other commands
    switch(message){
      case "#quit": // Closes the server then terminates the process.
        serverUI.display("Goodbye!");
        try{
          close();
          System.exit(0);
        }catch (Exception e){
          serverUI.display("Error while trying to close the server. " + e);
        }
        break;
      case "#stop": // Stops the server from listening for new connections
        serverUI.display("Stopping listening for incoming connections...");
        stopListening();
        break;
      case "#close": // Closes the server without terminating the UI
        serverUI.display("Closing the server...");
        try{
          close();
        }catch (Exception e){
          serverUI.display("Error while trying to close the server. " + e);
        }
        break;
      case "#start": // Starts the server listening for new connections again
        serverUI.display("Starting listening threads...");
        try{
          listen(); // Starts the listening threads
        }catch (Throwable e){ // any exceptions will be handled by listeningException(e)
          listeningException(e);
        }
        break;
      case "#getport": // Displays the current listening port of the server
        serverUI.display("The listening port number is " + getPort());
        break;
      default:
        serverUI.display("Command not recognized.");
        break;
    }
  }

  // OVERRIDES

  /**
   * Hook method called each time an exception is thrown in a
   * ConnectionToClient thread. Overrides the method in AbstractServer.
   * Triggered whenever a client connection is broken.
   *
   * @param client the client that raised the exception.
   * @param exception the exception thrown.
   */
  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
    //user has likely disconnected if an exception was thrown:
    clientDisconnected(client);
  }

  /**
   * Hook method called each time a client disconnects.
   * Overrides the default implementation in AbstractServer
   *
   * @param client the connection with the client.
   */
  synchronized protected void clientDisconnected(ConnectionToClient client)
  {
    System.out.println("User " + client.getId() + " disconnected from the server.");
  }

  /**
   * Hook method called each time a client connects.
   * Overrides the default implementation in AbstractServer
   *
   * @param client the connection with the client.
   */
  synchronized protected void clientConnected(ConnectionToClient client)
  {
    System.out.println("User #" + client.getId() + " connected to the server from " + client);
  }

  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  // The Main Method is no longer used, as its functionality has been moved into the ServerConsole class.
}