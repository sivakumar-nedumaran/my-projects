import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.Queue;
import java.util.Collections;

/*
 * A chat server that delivers public and private messages.
 */
public class D2DServer {

  // The server socket.
  private static ServerSocket serverSocket = null;
  // The client socket.
  private static Socket clientSocket = null;

  // This chat server can accept up to maxClientsCount clients' connections.
  private static final int maxClientsCount = 10;
  static final GraphAdjacencyList graph = new GraphAdjacencyList(maxClientsCount);
  private static final clientThread[] threads = new clientThread[maxClientsCount];

  public static void main(String args[]) {

    // The default port number.
    int portNumber = 2222;
    if (args.length < 1) {
      System.out.println("Usage: java D2DServer <portNumber>\n"
          + "Now using port number=" + portNumber);
    } else {
      portNumber = Integer.valueOf(args[0]).intValue();
    }

    /*
     * Open a server socket on the portNumber (default 2222). Note that we can
     * not choose a port less than 1023 if we are not privileged users (root).
     */
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      System.out.println(e);
    }

    /*
     * Create a client socket for each connection and pass it to a new client
     * thread.
     */
    while (true) {
      try {
        clientSocket = serverSocket.accept();
        int i = 0;
        for (i = 0; i < maxClientsCount; i++) {
          if (threads[i] == null) {
            (threads[i] = new clientThread(clientSocket, threads)).start();
            break;
          }
        }
        if (i == maxClientsCount) {
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Server too busy. Try later.");
          os.close();
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}

/*
 * The chat client thread. This client thread opens the input and the output
 * streams for a particular client, ask the client's name, informs all the
 * clients connected to the server about the fact that a new client has joined
 * the chat room, and as long as it receive data, echos that data back to all
 * other clients. The thread broadcast the incoming messages to all clients and
 * routes the private message to the particular client. When a client leaves the
 * chat room this thread informs also all the clients about that and terminates.
 */
class clientThread extends Thread {

  String clientName = null;
  String parentName = null;
  private DataInputStream is = null;
  private PrintStream os = null;
  private Socket clientSocket = null;
  private final clientThread[] threads;
  private int maxClientsCount;

  public clientThread(Socket clientSocket, clientThread[] threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
  }

  public void run() {
    int maxClientsCount = this.maxClientsCount;
    clientThread[] threads = this.threads;

    try {
      /*
       * Create input and output streams for this client.
       */
      is = new DataInputStream(clientSocket.getInputStream());
      os = new PrintStream(clientSocket.getOutputStream());
      String name;
      while (true) {
        os.println("Send your device ID : ");
        name = is.readLine().trim();
        if (name.indexOf('@') == -1) {
          break;
        } else {
          os.println("The name should not contain '@' character.");
        }
      }

      /* Welcome the new the client. */
	  if(!name.equals("LTE"))
      		os.println("Device ID " + name + " has been registered to the D2D server.\nTo disable relaying, send /quit");
	  
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] == this) {
            clientName = "@" + name;
            if(!name.equals("LTE"))
            {
            os.println("Send your location : ");
            String loc = is.readLine().trim();
            String lat = loc.substring(0,loc.indexOf(' '));
            String lon = loc.substring(loc.indexOf(' ')+1);
            D2DServer.graph.addVertex(clientName, Double.parseDouble(lat),Double.parseDouble(lon));
            parentName = D2DServer.graph.getParent(clientName);
            }
            break;
          }
        }
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] != this && !threads[i].clientName.equals("@LTE")) {
            threads[i].os.println("*** A new device " + name
                + " has been registered in the network !!! ***");
          }
        }
      }
      /* Start the conversation. */
      while (true) {
        String line = is.readLine();
        if (line.startsWith("/quit")) {
          break;
        }
        /* If the message is private sent it to the given client. */
        if (line.startsWith("@")) {
          String[] words = line.split("\\s", 2);
          boolean flag1 = false;
          if (words.length > 1 && words[1] != null) {
            words[1] = words[1].trim();
            if (!words[1].isEmpty()) {
              synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                  if (threads[i] != null && threads[i] != this
                      && threads[i].clientName != null
                      && threads[i].clientName.equals(words[0])) {
						  /*if(threads[i].clientName.equals("@LTE"))
						  {
							  String[] words2 = words[1].split("\\s", 2);
					          if (words2.length > 1 && words2[1] != null) {
					            words2[1] = words2[1].trim();
					            if (!words2[1].isEmpty())
							  	  threads[i].os.println(words2[0] + " <" + name + "> " + words2[1]);
							  }
						  }
						  else {*/
						        flag1 = true;
							  threads[i].os.println("<" + name + "> " + words[1]);
							  //this.os.println(">" + name + "> " + words[1]);
						  //}
                    /*
                     * Echo this message to let the client know the private
                     * message was sent.
                     */
                    break;
                  }
                }
                if(!flag1)
                {
                    for(int i=0;i<maxClientsCount;i++)
                    {
                        if (threads[i] != null && threads[i] != this
                      && threads[i].clientName != null
                      && threads[i].clientName.equals(this.parentName)) 
                        {
                            threads[i].os.println(line);
                        }
                    }
                }
              }
            }
          }
        } else {
          /* The message is public, broadcast it to all other clients. */
          synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientName != null && !threads[i].clientName.equals("@LTE")) {
                threads[i].os.println("<" + name + "> " + line);
              }
            }
          }
        }
      }
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] != this
              && threads[i].clientName != null && !threads[i].clientName.equals("@LTE")) {
            threads[i].os.println("*** The device " + name
                + " is leaving the network !!! ***");
          }
        }
        D2DServer.graph.deleteVertex(clientName);
      }
      //os.println("*** Bye " + name + " ***");

      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] == this) {
            threads[i] = null;
          }
        }
      }
      /*
       * Close the output stream, close the input stream, close the socket.
       */
      is.close();
      os.close();
      clientSocket.close();
    } catch (IOException e) {
    }
  }
}
class DistanceCalculator 
{
  static double PI_RAD = Math.PI / 180.0;

  /**
   * Use Great Circle distance formula to calculate distance between 2 coordinates in meters.
   */
  public double greatCircleInFeet(double lat1, double long1, double lat2, double long2) {
    return greatCircleInMeters(lat1,long1,lat2,long2) * 3.28084;
  }

  /**
   * Use Great Circle distance formula to calculate distance between 2 coordinates in kilometers.
   * https://software.intel.com/en-us/blogs/2012/11/25/calculating-geographic-distances-in-location-aware-apps
   */
  public double greatCircleInMeters(double lat1, double long1, double lat2, double long2) {
    double phi1 = lat1 * PI_RAD;
    double phi2 = lat2 * PI_RAD;
    double lam1 = long1 * PI_RAD;
    double lam2 = long2 * PI_RAD;

    return 1000 * 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));
  }
}

class GraphAdjacencyList 
{
 	List<List<Integer>> adjList;
 	int maxVerts;
 	int numVerts;
 	Vertex[] vertList;
	private Queue<Integer> queue;
 
 	public GraphAdjacencyList(int maxVerts)
	{
  	  	this.maxVerts = maxVerts;
		queue = new LinkedList<Integer>();
  		numVerts = 0;
  	  	adjList = new ArrayList<List<Integer>>();
  	  	vertList = new Vertex[maxVerts];
  	  	for (int i = 0; i < maxVerts; i++) 
		{
   		 	adjList.add(new LinkedList<Integer>());
  	  	}
 	}

	public void setParent(String label)
	{
	  int key = findIndexOf(label);
	  int level = 1;
	  boolean flag = false;
	  vertList[key].isVisited = true;
	  //display(key);
	  queue.removeAll(queue);
	  queue.add(key);
	  boolean first = true;
	  int thres = 1;
  
	  while( !queue.isEmpty() && level >= thres )
	  {
	   	int vert = queue.remove();
	   	int idx;
	   	if(first)
	   	{
	   		//thres ++;
	   		first = false;
	   		//System.out.println("\nFinding the parent of " + vertList[key].label + " Level 1");
	   		//verticesAdjacentTo(vertList[key].label);
	   	}
	   	else
	   	{
	   		//System.out.println("\nFinding the parent of " + vertList[vert].label + " Level 2");
	   		//verticesAdjacentTo(vertList[vert].label);
	   	}
   		
   		clearVisits();
	   	while( (idx=getAdjUnvistedVertex(vert)) != -1 )
	   	{
	    	vertList[idx].isVisited = true;
	    	flag = false;
	    	//display(idx);
			if(vertList[idx].parent != null && vertList[idx].parent.equals("@LTE"))
			{
				if(vert==key)
				{
					vertList[key].parent = vertList[idx].label;
					//System.out.println("Found cluster head at " + vertList[idx].label);
					System.out.println("Making " + vertList[idx].label + " as parent of " + vertList[key].label);
				}
				else
				{
					vertList[key].parent = vertList[vert].label;
					//System.out.println("Found cluster head at " + vertList[idx].label);
					System.out.println("Making " + vertList[vert].label + " as parent of " + vertList[key].label);
				}
				flag = true;
				break;
			}
	    	flag = false;
	    	queue.add(idx);
	    	if(vert == key)
	    		level++;
	   	}
	   	if(flag == true)
	   		break;
		level--;
	  }
	  clearVisits();
	  if(flag == true)
	  		return;
	  vertList[key].parent = "@LTE";
	  System.out.println("Making " + vertList[key].label + " as cluster head");
	}
 
	public int getAdjUnvistedVertex(int vert)
	{
		List<Integer> list = adjList.get(vert);
		//System.out.print("\nReceived " + vertList[vert].label);
	 	for (int i = 0; i < list.size(); i++) 
		{
	   	 	if( !vertList[list.get(i)].isVisited )
			{
				//System.out.print(" Sending " + vertList[list.get(i)].label);
	    		return list.get(i);
	   	 	}
	  	}
	  	return -1;
	}
	 
 	public void addVertex(String label,double x,double y)
	{
  	  	System.out.print(" Added device " + label + " : ");
  	  	if(numVerts < maxVerts)
		{
   			vertList[numVerts++] = new Vertex(label,x,y);
  		}
		//displayAllVertices();
		DistanceCalculator dc = new DistanceCalculator();
  	  	for (int i = 0; i < numVerts-1; i++) 
		{
			double dist = dc.greatCircleInMeters(x,y,vertList[i].x,vertList[i].y);
			//System.out.println("Considering " + x + " , " + y + " and " + vertList[i].x + " , " + vertList[i].y + " Dist = " + dist);
    		if(dist <= 400000 )
			{
				addEdge(numVerts-1,i);
				//System.out.print("Edge added\n");
			}
  	  	}
  	  	setParent(label);
	}
 
 	public void addEdge(int src, int dest)
	{
  	  	if(src < numVerts && dest < numVerts)
		{
   			adjList.get(src).add(dest);
   	 		adjList.get(dest).add(src);
  		}
 	}
 
 	public boolean hasEdge(int src, int dest)
	{
  	  	return adjList.get(src).contains(dest);
 	}
	
 	public void display(int idx)
	{
  	  	System.out.print(vertList[idx].label);
 	}
 	
 	public String getParent(String label)
	{
	    int i = findIndexOf(label);
	    return vertList[i].parent;
 	}
 
 	public void displayAllEdges()
	{
  	  	for (int i = 0; i < numVerts ; i++) 
		{
   		 	List<Integer> adjVertex = adjList.get(i);
   		 	for (int j = 0; j < adjVertex.size(); j++) 
			{
    			System.out.print(""+vertList[i].label+""+vertList[adjVertex.get(j)].label+" ");
   		 	}
  	  	}
  	  	System.out.println();
 	}
	
 	public void displayAllVertices()
	{
		System.out.println("Adj list size = "+ (numVerts - 1));
  	  	for (int i = 0; i < numVerts - 1; i++) 
		{
   		 	List<Integer> adjVertex = adjList.get(i);
    		System.out.print("Vertex " + i + " " + vertList[i].label + "\n");
  	  	}
  	  	System.out.println();
 	}
 
 	public int findIndexOf(String label)
 	{
 		int i;
  	  	for (i = 0; i <= numVerts-1; i++) 
		{
			if(vertList[i].label.equals(label))
				break;
		}
		if(i > numVerts - 1)
			return -1;
		else
			return i;
 	}
 	
 	public void deleteVertex(String label)
 	{
 	   int key = findIndexOf(label);
 	   System.out.println("Found index of "+label+" as " + key);
 	   verticesAdjacentTo(vertList[key].label);
 	   List<Integer> adjVertex2 = adjList.get(key);
 	   System.out.println("Moving "+vertList[numVerts-1].label+" at index "+ (numVerts-1) +" to " + vertList[key].label +" at index "+ key);
 	   Collections.swap(adjList, key, numVerts-1);
 	   adjList.remove(numVerts-1);
 	   
 	   for (int i = 0; i < numVerts ; i++) 
		{
		    if(i!=key)
		    {
   		 	List<Integer> adjVertex = adjList.get(i);
   		 	System.out.println("At vertex "+vertList[i].label);
   		 	verticesAdjacentTo(vertList[i].label);
   		 	for (int j = 0; j < adjVertex.size(); j++) 
			{
    			if(vertList[adjVertex.get(j)].label!=null && vertList[adjVertex.get(j)].label.equals(label))
    			{
    			    System.out.println("Removing "+vertList[adjVertex.get(j)].label);
    			    adjVertex.remove(j);
    			}
   		 	}
   		 }
  	  	}
  	  	for (int j = 0; j < adjVertex2.size(); j++) 
	    {
	            System.out.println("Computing new parent for  "+vertList[adjVertex2.get(j)].label);
    			setParent(vertList[adjVertex2.get(j)].label);
   		 }
   		 vertList[key]=vertList[numVerts-1];
 	     vertList[numVerts-1]=null;
 	     numVerts--;
 	}
	public void verticesAdjacentTo(String label)
	{
		int i = findIndexOf(label);
		//System.out.println("Found index as " + i);
		if(i==-1)
			return;
		System.out.print("Vertices adjacent to "+label+" are ");
     	List<Integer> list = adjList.get(i);
     	for (int j = 0; j < list.size(); j++) 
		{
      	  	System.out.print(vertList[list.get(j)].label);
     	}
     	System.out.println();
	}
	public void clearVisits()
	{
		for(int i=0 ; i<=numVerts - 1 ; i++)
		{
			vertList[i].isVisited = false;
		}
	}
}

class Vertex
{
 	public String label;
	double x,y;
	public String parent;
 	public boolean isVisited = false;
 
 	public Vertex(String label, double x, double y)
	{
  	  	this.label = label;
		this.x = x;
		this.y = y;
 	}
}
