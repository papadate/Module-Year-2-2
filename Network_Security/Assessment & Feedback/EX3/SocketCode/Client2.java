import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Client2
{
    static String ipAddy = "127.0.0.1";
    static int portNo = 11338;
    static String hexKey= null;
    static BigInteger g = new BigInteger("129115595377796797872260754286990587373919932143310995152019820961988539107450691898237693336192317366206087177510922095217647062219921553183876476232430921888985287191036474977937325461650715797148343570627272553218190796724095304058885497484176448065844273193302032730583977829212948191249234100369155852168");
    static BigInteger p = new BigInteger("165599299559711461271372014575825561168377583182463070194199862059444967049140626852928438236366187571526887969259319366449971919367665844413099962594758448603310339244779450534926105586093307455534702963575018551055314397497631095446414992955062052587163874172731570053362641344616087601787442281135614434639");

    static boolean debug = true;
    public static void main(String[] args) throws Exception {
        Socket s = new Socket(ipAddy,portNo);
        InputStream in = s.getInputStream();
        DataInputStream inputStream = new DataInputStream(in);
        DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());

        DHParameterSpec dhSpec = new DHParameterSpec(p,g);
        KeyPairGenerator diffieHellmanGen = KeyPairGenerator.getInstance("DiffieHellman");
        diffieHellmanGen.initialize(dhSpec);
        KeyPair serverPair = diffieHellmanGen.generateKeyPair();
        PrivateKey y = serverPair.getPrivate();
        PublicKey gToTheY = serverPair.getPublic();

        //Protocol message 1
        //PublicKey cert can vary in length, therefore the length is sent first
        byte[] pk = gToTheY.getEncoded();
        outputStream.writeInt(pk.length);
        outputStream.write(pk);


        // Nc ->S
        // S -> E(Nc+1), Ns -> C
        // C -> E(Ns+1) -> S
          // C'-Nc'(=Ns)-> S
          // S -> E(Nc'+1), Ns' -> C
          //        ^ = Ns


        // Nc = Ns
        // E(Ns+1)

        //Protocol message 2
        int pkL = inputStream.readInt();
        byte[] pks = new byte[pkL];
        inputStream.read(pks);
        KeyFactory keyfactoryDH = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(pks);
        PublicKey gToTheX = keyfactoryDH.generatePublic(x509Spec);
        System.out.println("g^x len: "+gToTheX);
        System.out.println("g^x cert: "+byteArrayToHexString(gToTheX.getEncoded()));
        if (debug) System.out.println("g^x len: "+pkL);
        if (debug) System.out.println("g^x cert: "+byteArrayToHexString(gToTheX.getEncoded()));

        //Calculate session key
        // This method sets decAESsessionCipher & encAESsessionCipher
        calculateSessionKey(y, gToTheX);

        //Protocol Step 3
        int nounce =  0;
        byte[] cN = encAESsessionCipher.doFinal(BigInteger.valueOf(nounce).toByteArray());
        outputStream.write(cN);

        //Protocol Step 4
        byte[] bs = new byte[32];
        in.read(bs);
        byte[] ans = decAESsessionCipher.doFinal(bs);
        System.out.println(ans.length);
        byte[] encSNounce = new byte[16];
        byte[] srvNoun = new byte[4];
        System.arraycopy(ans, 0, encSNounce, 0, 16);
        System.arraycopy(ans, 16, srvNoun, 0, 4);
        System.out.println("N:");
        System.out.println(new BigInteger(srvNoun));
        Scanner sc = new Scanner(System.in);
        String token = sc.nextLine().trim();


        byte[] srvNInc = hexStringToByteArray(token); // TODO: 自己获取一下
        byte[] esb = encAESsessionCipher.doFinal(srvNInc);
        outputStream.write(esb);

        int avail = inputStream.available();
        System.out.println(avail);
        byte[] anS = new byte[avail];
        inputStream.read(anS);

        List<Byte> ls = new ArrayList<>();
        try {
            while (true){
                byte b = inputStream.readByte();
                ls.add(b);
            }
        }
        catch (Exception e) {

        }
        byte[] vvvv = new byte[ls.size()];
        int index = 0;
        for (Byte _b : ls) {
            vvvv[index] = _b;
            index++;
        }
        byte[] ssss= decAESsessionCipher.doFinal(vvvv);
        System.out.println(new String(ssss));

    }

    public static Cipher decAESsessionCipher;
    public static Cipher encAESsessionCipher;
    // This method sets decAESsessioncipher & encAESsessioncipher
    private static void calculateSessionKey(PrivateKey y, PublicKey gToTheX)  {
        try {
            // Find g^xy
            KeyAgreement serverKeyAgree = KeyAgreement.getInstance("DiffieHellman");
            serverKeyAgree.init(y);
            serverKeyAgree.doPhase(gToTheX, true);
            byte[] secretDH = serverKeyAgree.generateSecret();
            System.out.println("g^xy: "+byteArrayToHexString(secretDH));
            //Use first 16 bytes of g^xy to make an AES key
            byte[] aesSecret = new byte[16];
            System.arraycopy(secretDH,0,aesSecret,0,16);
            Key aesSessionKey = new SecretKeySpec(aesSecret, "AES");
            System.out.println("Session key: "+byteArrayToHexString(aesSessionKey.getEncoded()));
            // Set up Cipher Objects
            decAESsessionCipher = Cipher.getInstance("AES");
            decAESsessionCipher.init(Cipher.DECRYPT_MODE, aesSessionKey);
            encAESsessionCipher = Cipher.getInstance("AES");
            encAESsessionCipher.init(Cipher.ENCRYPT_MODE, aesSessionKey);
        } catch (NoSuchAlgorithmException e ) {
            System.out.println(e);
        } catch (InvalidKeyException e) {
            System.out.println(e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public static void generateDHprams() throws NoSuchAlgorithmException, InvalidParameterSpecException {
        AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
        paramGen.init(1024);
        //Generate the parameters
        AlgorithmParameters params = paramGen.generateParameters();
        DHParameterSpec dhSpec = (DHParameterSpec)params.getParameterSpec(DHParameterSpec.class);
        System.out.println("These are some good values to use for p & g with Diffie Hellman");
        System.out.println("p: "+dhSpec.getP());
        System.out.println("g: "+dhSpec.getG());

    }

    private static String byteArrayToHexString(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
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
