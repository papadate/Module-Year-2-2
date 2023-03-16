public class Client2 {
    public static void main(String[] args) throws IOException {
        String serverName = "127.0.0.1";
        int port = 11338;

        System.out.println("Connecting to " + serverName + " on port " + port);
        Socket client = new Socket(serverName, port);

        System.out.println("Connected to " + client.getRemoteSocketAddress());
        DataInputStream in = new DataInputStream(client.getInputStream());

        // Receive the Diffie-Hellman key from the server
        int keySize = in.readInt();
        byte[] keyBytes = new byte[keySize];
        in.readFully(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("DiffieHellman");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            System.out.println("Received Diffie-Hellman key: " + publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Close the socket
        client.close();
    }
}
