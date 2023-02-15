import java.io.Console;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//The standard Java crypto libraries don't do CCM mode as default, 
// so we will need another provider.
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class EncTool {

	static String inFile = "plainText.txt";
	static String outFile = "cipherText.enc";
	static String hexKey = "3eafda76cd8b015641cb946708675423";
	static String keyStore;
	static String keyName;

	public static void main(String[] args) {

		// Handle the command line arguments
		if (args.length == 4 && args[0].equals("-encAESCTR") && args[1].length() == 32) {
			hexKey = args[1];
			inFile = args[2];
			outFile = args[3];
			encryptAESCTR();
		} else if (args.length == 4 && args[0].equals("-decAESCTR") && args[1].length() == 32) {
			hexKey = args[1];
			inFile = args[2];
			outFile = args[3];
			decryptAESCTR();
		} else if (args.length == 4 && args[0].equals("-encAESCCM") && args[1].length() == 32) {
			hexKey = args[1];
			inFile = args[2];
			outFile = args[3];
			encryptAESCCM();
		} else if (args.length == 4 && args[0].equals("-decAESCCM") && args[1].length() == 32) {
			hexKey = args[1];
			inFile = args[2];
			outFile = args[3];
			decryptAESCCM();
		} else if (args.length == 5 && args[0].equals("-encRSA")) {
			keyStore = args[1];
			keyName = args[2];
			inFile = args[3];
			outFile = args[4];
			encryptRSA();
		} else if (args.length == 5 && args[0].equals("-decRSA")) {
			keyStore = args[1];
			keyName = args[2];
			inFile = args[3];
			outFile = args[4];
			decryptRSA();
		} else if (args.length == 1 && args[0].equals("-genAES")) {
			generateKey();
		} else {
			System.out.println("This is a simple program to encrypt and decrypt files");
			System.out.println("Usage: ");
			System.out.println(
					"    -encAESCTR <key:128 bits in as hex> <inputFile> <outputFile>  AES CTR mode encrypt");
			System.out.println(
					"    -decAESCTR <key:128 bits in as hex> <inputFile> <outputFile>  AES CTR mode decrypt");
			System.out.println(
					"    -encAESCCM <key:128 bits in as hex> <inputFile> <outputFile>  AES CCM mode encrypt");
			System.out.println(
					"    -decAESCCM <key:128 bits in as hex> <inputFile> <outputFile>  AES CCM modedecrypt");
			System.out.println(
					"    -encRSA <keyStore> <keyName> <inputFile> <outputFile>         RSA encrypt");
			System.out.println(
					"    -decRSA <keyStore> <keyName> <inputFile> <outputFile>         RSA decrypt");
			System.out.println("    -genAES     generate an AES key");
		}
	}

	private static void encryptRSA() {
		try {
			// Get the public key from the keyStore and set up the
			// Cipher object
			PublicKey publicKey = getPubKey(keyStore, keyName);
			Cipher rsaCipher = Cipher.getInstance("RSA");
			rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);

			// Read the plainText
			System.out.println("Loading plaintext file: " + inFile);
			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] plainText = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(plainText);

			// Generate a symmetric key to encrypt the data and
			// initiate the AES Cipher Object
			System.out.println("Generating AES key");
			KeyGenerator sKenGen = KeyGenerator.getInstance("AES");
			Key aesKey = sKenGen.generateKey();
			Cipher aesCipher = Cipher.getInstance("AES");
			aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);

			// Encrypt the symmetric AES key with the public RSA key
			System.out.println("Encrypting Data");
			byte[] encodedKey = rsaCipher.doFinal(aesKey.getEncoded());
			// Encrypt the plaintext with the AES key
			byte[] cipherText = aesCipher.doFinal(plainText);

			// Write the encrypted AES key and cipher text to the
			// file.
			System.out.println("Writting to file: " + outFile);
			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(encodedKey);
			outToFile.write(cipherText);

			System.out.println("Closing Files");
			rawDataFromFile.close();
			outToFile.close();
		} catch (Exception e) {
			System.out.println("Doh: " + e);
		}
	}

	/**
	 * Method to get the private key from the key store
	 * 
	 * @param keyStoreFile
	 *                key store file
	 * @param keyName
	 *                key name
	 * @return private key
	 */

	private static PrivateKey getPrivKey(String keyStoreFile, String keyName) {
		PrivateKey key = null;
		try {

			KeyStore myKeyStore = KeyStore.getInstance("JKS");
			FileInputStream inStream = new FileInputStream(keyStoreFile);

			// Get the keyStore password, using Console lets us mask
			// the password
			Console console = System.console();
			char[] password = console.readPassword("Enter your secret password: ");
			myKeyStore.load(inStream, password);

			key = (PrivateKey) myKeyStore.getKey(keyName, password);

		} catch (Exception e) {
			System.out.println("doh" + e);
		}
		return key;
	}

	private static PublicKey getPubKey(String keyStoreFile, String keyName) {
		PublicKey publicKey = null;
		try {
			// Load the keyStore
			KeyStore myKeyStore = KeyStore.getInstance("JKS");
			FileInputStream inStream = new FileInputStream(keyStoreFile);

			// Get the keyStore password, using Console lets us mask
			// the password
			Console console = System.console();
			char[] password = console.readPassword("Enter your secret password: ");
			myKeyStore.load(inStream, password);
			Certificate cert = myKeyStore.getCertificate(keyName);
			publicKey = cert.getPublicKey();
		} catch (Exception e) {
			System.out.println("Doh: " + e);
		}
		return publicKey;
	}

	private static void decryptRSA() {

		try {

			// Get the public key and initiate it with the Cipher
			// object

			PrivateKey privateKey = getPrivKey(keyStore, keyName);
			Cipher rsaCipher = Cipher.getInstance("RSA");
			rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);

			// Read the plain text

			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] inBytes = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(inBytes);
			rawDataFromFile.close();

			byte[] keyBytes = new byte[256];
			for (int i = 0; i < 256; i++) {
				keyBytes[i] = inBytes[i];
			}

			byte[] decryptedKey = rsaCipher.doFinal(keyBytes);

			SecretKeySpec key = new SecretKeySpec(decryptedKey, "AES");

			Cipher aesCipher = Cipher.getInstance("AES");
			aesCipher.init(Cipher.DECRYPT_MODE, key);

			byte[] cipherText = Arrays.copyOfRange(inBytes, 256, inBytes.length);

			byte[] original = aesCipher.doFinal(cipherText);

			String outString = byteArrayToHexString(original);
			System.out.println("Plain Text is : " + outString);
			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(original);
			outToFile.close();

		} catch (Exception e) {
			System.out.println("doh" + e);

		}
	}

	private static void decryptAESCTR() {

		try {

			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] inBytes = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(inBytes);
			rawDataFromFile.close();

			byte[] ivBytes = Arrays.copyOfRange(inBytes, 0, 16);

			SecretKeySpec key = new SecretKeySpec(hexStringToByteArray(hexKey), "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

			Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5Padding");

			byte[] cipherText = Arrays.copyOfRange(inBytes, 16, inBytes.length + 1);

			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

			byte[] original = cipher.doFinal(cipherText);
			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(original);
			outToFile.close();

		} catch (Exception e) {
			System.out.println("doh " + e);
		}

	}

	private static void decryptAESCCM() {

		try {

			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] inBytes = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(inBytes);
			rawDataFromFile.close();

			byte[] ivBytes = Arrays.copyOfRange(inBytes, 0, 10);
			System.out.println("Iv bytes");
			for (int i = 0; i < ivBytes.length; i++) {
				System.out.print(ivBytes[i] + ", ");
			}
			System.out.println();

			SecretKeySpec key = new SecretKeySpec(hexStringToByteArray(hexKey), "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

			Cipher cipher = Cipher.getInstance("AES/CCM/NoPadding", new BouncyCastleProvider());

			byte[] cipherText = Arrays.copyOfRange(inBytes, 10, inBytes.length);

			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

			byte[] original = cipher.doFinal(cipherText);

			String stringOut = byteArrayToHexString(original);
			System.out.println(stringOut);
			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(original);
			outToFile.close();

		} catch (Exception e) {
			System.out.println("doh " + e);

		}

	}

	private static void generateKey() {
		try {
			KeyGenerator sGen = KeyGenerator.getInstance("AES");
			Key aesKey = sGen.generateKey();
			System.out.println("Here are some bytes you can use as an AES key: "
					+ byteArrayToHexString(aesKey.getEncoded()));
		} catch (Exception e) {
			System.out.println("doh " + e);
		}
	}

	private static void encryptAESCTR() {
		try {
			// Open and read the input file
			// N.B. this program reads the whole file into memory,
			// not good for large programs!
			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] plainText = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(plainText);
			rawDataFromFile.close();

			// Set up the AES key & cipher object in CTR mode
			SecretKeySpec secretKeySpec = new SecretKeySpec(hexStringToByteArray(hexKey), "AES");
			Cipher encAESCTRcipher = Cipher.getInstance("AES/CTR/PKCS5Padding");
			SecureRandom random = new SecureRandom();
			byte iv[] = new byte[16];
			random.nextBytes(iv);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			encAESCTRcipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);

			// Encrypt the data
			byte[] cipherText = encAESCTRcipher.doFinal(plainText);

			// Write file to disk
			System.out.println("Openning file to write: " + outFile);
			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(iv);
			outToFile.write(cipherText);
			outToFile.close();
			System.out.println(inFile + " encrypted as " + outFile);
		} catch (Exception e) {
			System.out.println("doh " + e);
		}
	}

	private static void encryptAESCCM() {
		try {
			// Open and read the input file
			// N.B. this program reads the whole file into memory,
			// not good for large programs!
			RandomAccessFile rawDataFromFile = new RandomAccessFile(inFile, "r");
			byte[] plainText = new byte[(int) rawDataFromFile.length()];
			rawDataFromFile.read(plainText);
			rawDataFromFile.close();

			// Set up the AES key & cipher object in CCM mode
			SecretKeySpec secretKeySpec = new SecretKeySpec(hexStringToByteArray(hexKey), "AES");
			// Add a security provider that actually does provide
			// CCM mode
			Security.insertProviderAt(new BouncyCastleProvider(), 1);
			Cipher encAESCCMcipher = Cipher.getInstance("AES/CCM/NoPadding", "BC");
			SecureRandom random = new SecureRandom();
			byte iv[] = new byte[10]; // BC needs us to leave room
							// for the counter
			random.nextBytes(iv);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			encAESCCMcipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);

			// Encrypt the data
			byte[] cipherText = encAESCCMcipher.doFinal(plainText);

			// Write file to disk
			System.out.println("Openning file to write: " + outFile);
			FileOutputStream outToFile = new FileOutputStream(outFile);
			outToFile.write(iv);
			outToFile.write(cipherText);
			outToFile.close();
			System.out.println(inFile + " encrypted as " + outFile);
		} catch (Exception e) {
			System.out.println("doh " + e);
		}
	}

	// Code from
	// http://www.anyexample.com/programming/java/java%5Fsimple%5Fclass%5Fto%5Fcompute%5Fmd5%5Fhash.xml
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
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	// Code from http://javaconversions.blogspot.co.uk
	private static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
}