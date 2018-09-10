import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class eNodeB {

  // The client socket
  private static Socket clientSocket = null;
  // The output stream
  //private static PrintStream os = null;
  // The input stream
  //private static DataInputStream is = null;

  //private static final int maxServersCount = 10;
  
  private static final serverThread[] threads = new serverThread[2];
  
  //private static BufferedReader inputLine = null;
  
  //private static boolean closed = false;
  
  public static void main(String[] args) {

    // The default port.
    int[] portNumber = {2222,3333};
    // The default host.
    String host[] = {"localhost","localhost"};

    if (args.length < 4) {
      System.out
          .println("Usage: java eNodeB <host> <portNumber>\n"
              + "Now using host=" + host[0] + ", portNumber=" + portNumber[0]);
    } else {
      host[0] = args[0];
      portNumber[0] = Integer.valueOf(args[1]).intValue();
      host[1] = args[2];
      portNumber[1] = Integer.valueOf(args[3]).intValue();
      
    }

    /*
     * Open a socket on a given host and port. Open input and output streams.
     */

    for (int i=0;i<2;i++) {
      try {
        clientSocket = new Socket(host[i], portNumber[i]);
        //int i = 0;
        //for (i = 0; i < maxClientsCount; i++) {
          //if (threads[i] == null) {
        (threads[i] = new serverThread(clientSocket, threads)).start();
            //break;
          //}
        //}
        /*if (i == maxClientsCount) {
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Server too busy. Try later.");
          os.close();
          clientSocket.close();
        }*/
      } catch (IOException e) {
        System.out.println(e);
      }
    }

    /*
     * If everything has been initialized then we want to write some data to the
     * socket we have opened a connection to on the port portNumber.
     */
}
}

class serverThread extends Thread {

  private String clientName = null;
  private DataInputStream is = null;
  private PrintStream os = null;
  private Socket clientSocket = null;
  private final serverThread[] threads;
  boolean flag = true;
  //private int maxClientsCount;

  public serverThread(Socket clientSocket, serverThread[] threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    //maxClientsCount = threads.length;
  }

  public void run() {
    //int maxClientsCount = this.maxClientsCount;
    serverThread[] threads = this.threads;

    try {
      /*
       * Create input and output streams for this client.
       */
      is = new DataInputStream(clientSocket.getInputStream());
      os = new PrintStream(clientSocket.getOutputStream());
	  
	  if(flag == true)
	  {
		  flag = false;
		  os.println("LTE");
	  }

      /* Welcome the new the client. */
      /* Start the conversation. */
      while (true) {
        String line = is.readLine();
		if(line.equals("Send your device ID : ") || line.equals("Send your location : "))
			line = null;
        /* If the message is private sent it to the given client. */
          /* The message is public, broadcast it to all other clients. */
          synchronized (this) {
            for (int i = 0; i < 2; i++) {
              if (threads[i] != null && threads[i] != this && line != null) {
				System.out.println("\n"+line);
                threads[i].os.println(line);
              }
            }
          }
      }

      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
      /*
       * Close the output stream, close the input stream, close the socket.
       */
      //is.close();
      //os.close();
      //clientSocket.close();
    } 
	catch (IOException e) {
    }
  }
}