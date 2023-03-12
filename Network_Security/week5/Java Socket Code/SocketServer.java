// This is a simple server that listens on port "portNo" for a client to connect 
// and then tries to read strings from the client and print them to stdout

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    
    // portNo is the port number that the server will listen on
    static int portNo = 3801;
    
    public static void main(String[] args) {
	
	//There might be a network error, therefore we run the code in a "try" block 
	try {
	    
	    System.out.println("opening socket");
	    ServerSocket serverSocket = new ServerSocket(portNo);
	    
	    // Listen on the socket until someone tries to connect.
	    System.out.println("listening on port "+portNo);
	    Socket socket = serverSocket.accept();
	    System.out.println("got a connection from: "+socket.getInetAddress().toString());
	    
	    //Wrapping a BufferedReader around the input stream makes it easy to read strings
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    
	    //  Wait for a line of data. 
	    String line = in.readLine();
	    while ( line != null) {
		// print the data recieved across the socket.
		System.out.println("Received line: "+line); 
		// wait for more data, line == null when the connection is closed.
		
		if (line.equals("password: kFS74ga*@%*Bna")) 
		    System.out.println("    Password correct");
		line = in.readLine();
	    }
	    
	    
	    serverSocket.close();
	}
	catch (IOException e) {
	    // This code is run if a network error occurs, e.g. the Internet connection is lost.
	    System.out.println("Network error: "+e);
	}
    }
}
