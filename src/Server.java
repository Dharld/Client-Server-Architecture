import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static int PORT = 9000;
    static ServerSocket server;
    static Socket client;
    static BufferedReader input = null;
    static BufferedWriter out = null;


    Server() throws IOException {
    }

    public static void main(String[] args) throws IOException {
            try {
                server = new ServerSocket(PORT);
                ExecutorService executorService = Executors.newCachedThreadPool();

                while(true) {
                    Socket client = server.accept();
                    InetAddress inetAddress = client.getInetAddress();
                    System.out.println("Connected to client: " + inetAddress.getHostName() + " at address: " + inetAddress.getHostAddress());
                    MessageTask task = new MessageTask(client);
                    executorService.execute(task);
                }

            } catch(IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                if (out != null) out.close();
                if (input != null) input.close();
                if (client != null) client.close();
                if (server != null) server.close();
            }
    }
}
