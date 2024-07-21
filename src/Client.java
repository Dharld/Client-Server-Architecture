import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static final int PORT = 9000;
    static Socket client;

    Client() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        client = new Socket("localhost", PORT);
        BufferedWriter out = null;
        BufferedReader in = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while(true) {
                Scanner input = new Scanner(System.in);
                System.out.print("Client: ");
                String message = input.nextLine();

                out.write(message + "\n"); // Added newline character
                out.flush(); // Ensure the message is sent

                if(message == "F*CK YOU") {
                    break;
                }
                
                String response = in.readLine();
                System.out.println("Server: " + response);
            }
        } catch(IOException err) {
            System.out.println("Exception: " + err.getMessage());
        } finally {
            if (out != null) out.close();
            if (in != null) in.close();
            if (client != null) client.close();
        }
    }
}
