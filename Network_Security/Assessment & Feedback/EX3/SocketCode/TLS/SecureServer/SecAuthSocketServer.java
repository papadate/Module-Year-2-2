import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;


public class SecAuthSocketServer {

    // Default port to listen on
    static int portNo = 3807;
    
    static String keyStoreFile = "server.jks";
    static String notSoSecretPassword = "password";
    
    public static void main(String[] args) throws Exception {
      
      
      // The key store holds the key that server will use to identify itself
      System.out.println("Loading key store from "+keyStoreFile);
      KeyStore ks = KeyStore.getInstance("JKS");
      ks.load(new FileInputStream(keyStoreFile), notSoSecretPassword.toCharArray());
      KeyManagerFactory keyStore = KeyManagerFactory.getInstance("SunX509");
      keyStore.init(ks, notSoSecretPassword.toCharArray());
      
      // The SSLContext musts be initiated using the keystore
      System.out.println("Initiate the Socket Factory");
      SSLContext sc = SSLContext.getInstance("TLS");
      sc.init(keyStore.getKeyManagers(), null, null);
      SSLServerSocketFactory secSocketFactory = sc.getServerSocketFactory();	  
      System.out.println("opening secure socket");
	
      // Create the socket and listen for a connection
      SSLServerSocket secSocket = (SSLServerSocket) secSocketFactory.createServerSocket(portNo);  
      System.out.println("listening on port "+portNo);
      SSLSocket socket = (SSLSocket) secSocket.accept();
      System.out.println("got a connection from: "+socket.getInetAddress().toString());
	  
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      
      System.out.print("Listening for data:");
      String line = in.readLine();
      while ( line != null) {
	System.out.println(line); 
	line = in.readLine();
      }
      
      System.out.print("Closing socket");
      out.close();
      in.close();
      socket.close();
      secSocket.close();
    }
}
