// This is a simple server that listens on port "portNo" for a client to connect 
// and then tries to read strings from the client and print them to stdout

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import javax.crypto.*;
import java.io.InputStream;
import javax.crypto.spec.SecretKeySpec;

public class CryptoServer {
    
    // portNo is the port number that the server will listen on
    static int portNo = 3801;
    static String hexKey="c4d2e473ac5869503abcf9d88e55a7bb";
    
    public static void main(String[] args) throws Exception {
	
	//There might be a network error, therefore we run the code in a "try" block 

	System.out.println("opening socket");
	ServerSocket serverSocket = new ServerSocket(portNo);
	
	// Listen on the socket until someone tries to connect.
	System.out.println("listening on port "+portNo);
	Socket socket = serverSocket.accept();
	System.out.println("got a connection from: "+socket.getInetAddress().toString());
	
	//Wrapping a BufferedReader around the input stream makes it easy to read strings
	InputStream inStream = socket.getInputStream();
	
	//Initiate the cipher object for decryptions
	Cipher decAEScipher = Cipher.getInstance("AES");
	Key aesKey = new SecretKeySpec(hexStringToByteArray(hexKey), "AES");		 
	decAEScipher.init(Cipher.DECRYPT_MODE, aesKey);
	
	// read, decrypt and print the first line.
	byte[] lineBytes1 = new byte[16];
	inStream.read(lineBytes1);
	byte[] plainText = decAEScipher.doFinal(lineBytes1);
	String line = new String(plainText);
	System.out.println("Received line: "+line); 
	
	// read, decrypt and print the second line.
	byte[] lineBytes2 = new byte[32];
	inStream.read(lineBytes2);
	    plainText = decAEScipher.doFinal(lineBytes2);
	    line = new String(plainText);
	    System.out.println("Received line: "+line); 
	    
	    // Check the password
	    if (line.equals("password: kFS74ga*@%*Bna")) 
		System.out.println("    Password correct");
	    else
		System.out.println("    Password incorrect");
    }
    
    private static byte[] hexStringToByteArray(String s) {
	int len = s.length();
	byte[] data = new byte[len / 2];
	for (int i = 0; i < len; i += 2) {
	    data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
				  + Character.digit(s.charAt(i+1), 16));
	}
	return data;
    }
    
}
