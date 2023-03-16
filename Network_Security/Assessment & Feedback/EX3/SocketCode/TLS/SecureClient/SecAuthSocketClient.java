// The trust store must be loaded using the -Djavax.net.ssl.trustStore option.

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SecAuthSocketClient {
    
    // Default port and IP address for server.
    static String ipAddy = "127.0.0.1";
    static int portNo = 3807;
    
    public static void main(String[] args) throws Exception {
      System.out.println("setting up socket");
      SSLSocketFactory secSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
      secSocketFactory.createSocket();
      SSLSocket secSocket = (SSLSocket) secSocketFactory.createSocket();
		      
      System.out.println("opening socket");	
      secSocket.connect(new InetSocketAddress(ipAddy, portNo));
      socketInfoClient(secSocket);
      PrintWriter out = new PrintWriter(secSocket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(secSocket.getInputStream()));
      
      System.out.println("sending data");
      out.write("userName:chothia\n");
      out.write("Password:Tjd9Hq#81\n");
      out.write("Cmd: move 100 pounds to account 44.64.13.146\n");
      
      System.out.println("closing socket");
      out.close();
      in.close();
      secSocket.close();
    }
    
    
    private static void socketInfoClient(SSLSocket s) {
      System.out.println("Socket (src ip, src port, des ip, des port) = ");
      System.out.print("   ("+s.getLocalAddress().toString());
      System.out.print(", "+s.getLocalPort());  
      System.out.print(", "+s.getInetAddress().toString());
      System.out.println(", "+s.getPort()+")");
      
      SSLSession ss = s.getSession();
      System.out.println("Cipher suite = "+ss.getCipherSuite());
    }
}
