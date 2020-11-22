// This file contains material supporting section 6.13 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package comp3415.telehealth.chat.ocsf.server;

/**
* The <code> AdaptableServer </code> is an adapter class
* that extends the <code> AbstractServer </code> class in place of
* the <code> AbstractObservableServer </code>.<p>
*
* Project Name: OCSF (Object Client-Server Framework)<p>
*
* @author Dr. Robert Lagani&egrave;re
* @version Febuary 2001
*/
class AdaptableServer extends AbstractServer
{
  //Instance variables **********************************************

  /**
   * The adapter used to simulate multiple class inheritance.
   */
  private ObservableServer server;

// CONSTRUCTORS *****************************************************

  /**
   * Constructs the server adapter.
   *
   * @param  port  the port number.
   */
  public AdaptableServer(int port, ObservableServer server)
  {
    super(port);
    this.server = server;
  }

// OVERRIDDEN METHODS ---------

  /**
   * Hook method called each time a new comp3415.telehealth.chat.client connection is
   * accepted.
   *
   * @param client the connection connected to the comp3415.telehealth.chat.client.
   */
  final protected void clientConnected(ConnectionToClient client)
  {
    server.clientConnected(client);
  }

  /**
   * Hook method called each time a comp3415.telehealth.chat.client disconnects.
   *
   * @param client the connection with the comp3415.telehealth.chat.client.
   */
  final protected void clientDisconnected(ConnectionToClient client)
  {
    server.clientDisconnected(client);
  }

  /**
   * Hook method called each time an exception
   * is raised in a comp3415.telehealth.chat.client thread.
   *
   * @param client the comp3415.telehealth.chat.client that raised the exception.
   * @param exception the exception raised.
   */
  final protected void clientException(ConnectionToClient client,
                                        Throwable exception)
  {
    server.clientException(client, exception);
  }

  /**
   * Hook method called when the server stops accepting
   * connections because an exception has been raised.
   *
   * @param exception the exception raised.
   */
  final protected void listeningException(Throwable exception)
  {
    server.listeningException(exception);
  }

  /**
   * Hook method called when the server stops accepting
   * connections.
   */
  final protected void serverStopped()
  {
    server.serverStopped();
  }

  /**
   * Hook method called when the server starts listening for
   * connections.
   */
  final protected void serverStarted()
  {
    server.serverStarted();
  }

  /**
   * Hook method called when the server is closed.
   */
  final protected void serverClosed()
  {
    server.serverClosed();
  }

  /**
   * Handles a command sent from the comp3415.telehealth.chat.client to the server.
   *
   * @param msg   the message sent.
   * @param client the connection connected to the comp3415.telehealth.chat.client that
   *  sent the message.
   */
  final protected void handleMessageFromClient(Object msg,
                                         ConnectionToClient client)
  {
    server.handleMessageFromClient(msg, client);
  }
}
