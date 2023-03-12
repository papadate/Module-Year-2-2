//This is a simple client that connects to on ip "ipAddy" on  port "portNo" 
// and sends two strings.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class CryptoClient {

    // ipAddy must be changed to the ip address of the server
    static String ipAddy = "127.0.0.1";
    static int portNo = 3801;
    static String hexKey="c4d2e473ac5869503abcf9d88e55a7bb";
    public static void main(String[] args) throws Exception {
	
	System.out.println("opening socket");
	Socket socket = new Socket(ipAddy,portNo);
	
	//Wrapping a PrintWriter around the output streams lets us to write strings
	OutputStream outStream = socket.getOutputStream();
	
	//Initiate the cipher object for encryption
	Cipher encAEScipher = Cipher.getInstance("AES");
	Key aesKey = new SecretKeySpec(hexStringToByteArray(hexKey), "AES");			
	encAEScipher.init(Cipher.ENCRYPT_MODE, aesKey);
	
	
	System.out.println("writing data");
	byte[] cipherText = encAEScipher.doFinal("username: tpc".getBytes());
       	System.out.println(cipherText.length);		

	outStream.write(cipherText);
	cipherText = encAEScipher.doFinal("password: kFS74ga*@%*Bna".getBytes());
       	System.out.println(cipherText.length);		

	outStream.write(cipherText);
	outStream.flush();
	
	System.out.println("closing socket");		
	socket.close();
	
	System.out.println("done");
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













