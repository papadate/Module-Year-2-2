import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SecureSocketClient {

    static String ipAddy = "127.0.0.1";
    static int portNo = 3806;
    
    public static void main(String[] args) throws IOException {
      
      System.out.println("setting up socket");
      // Create the SocketFactory which will be used to create the Socket
      SSLSocketFactory secSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
      SSLSocket secSocket = (SSLSocket) secSocketFactory.createSocket();
      
      // Set the cipher suite to be used.
      String[] enabledCipherSuites = { "TLS_DH_anon_WITH_AES_128_CBC_SHA"};
      //String[] enabledCipherSuites = { "SSL_DH_anon_WITH_RC4_128_MD5" };
      secSocket.setEnabledCipherSuites(enabledCipherSuites);
      
      System.out.println("opening socket");	
      secSocket.connect(new InetSocketAddress(ipAddy, portNo));
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
}
