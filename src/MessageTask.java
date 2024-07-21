import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageTask implements Runnable {
    Lock lock = new ReentrantLock();
    private BufferedReader reader = null;
    private BufferedWriter writer = null;
    private Socket client;

   public MessageTask(Socket client) {
       this.client = client;
   }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));

            // Reading from the client
            InetAddress inetAddress = client.getInetAddress();
            String hostName = inetAddress.getHostName();
            String message = reader.readLine();
            System.out.println(hostName + ": " + message);

            lock.lock();

            // Writing back to the client
            Scanner scanner = new Scanner(System.in);
            System.out.print("Server: ");
            String response = scanner.nextLine();
            writer.write(response + "\n");
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
