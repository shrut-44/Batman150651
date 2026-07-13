import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
  public static void main(String[] args) {

     try {
       ServerSocket serverSocket = new ServerSocket(4221);

       // Since the tester restarts your program quite often, setting SO_REUSEADDR
       // ensures that we don't run into 'Address already in use' errors
       serverSocket.setReuseAddress(true);
       Socket socket = serverSocket.accept();
       BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       String header = reader.readLine();
       String[] headerComponents = header.split(" ");
       String status;
       if((headerComponents[1]).equals("/")){
           status = "200 OK";
       }
       else{
           status = "404 Not Found";
       }
       OutputStream outputStream = socket.getOutputStream();
       String response = String.format("HTTP/1.1 %s\r\n\r\n",status);
       outputStream.write(response.getBytes());
       outputStream.flush();
       outputStream.close();
       // Wait for connection from client.
       System.out.println("accepted new connection");
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
