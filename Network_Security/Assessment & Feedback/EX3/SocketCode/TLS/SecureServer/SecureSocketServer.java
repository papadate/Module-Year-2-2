import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;


public class SecureSocketServer {
    
    static int portNo = 3806;
    
    public static void main(String[] args) throws IOException {
      
      System.out.println("opening secure socket");
      
      // Make a factory from which to generate the SSLServerSocket
      SSLServerSocketFactory secSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
      SSLServerSocket secSocket = (SSLServerSocket) secSocketFactory.createServerSocket(portNo);

      // Print the default Cipher Suites
      //System.out.println("   Default cipher suites:");
      //String[] dcsList = secSocketFactory.getDefaultCipherSuites();
      //for (int i=0; i<dcsList.length; i++) {
      //	System.out.println("      "+dcsList[i]);
      //}	
      
      // Set the Cipher Suites to use RC4 and no authentication
      String[] enabledCipherSuites = { "TLS_DH_anon_WITH_AES_128_CBC_SHA"};
      secSocket.setEnabledCipherSuites(enabledCipherSuites);
      
      System.out.println("listening on port "+portNo);
      SSLSocket socket = (SSLSocket) secSocket.accept();
      
      System.out.println("got a connection from: "+socket.getInetAddress().toString());
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 
      // Read each line sent over the socket and print it to the screen
      String line = in.readLine();
      while ( line != null) {
	System.out.println(line); 
	line = in.readLine();
      }
      
      // close the socket connection
      out.close();
      in.close();
      socket.close();
      secSocket.close();
    }
}
