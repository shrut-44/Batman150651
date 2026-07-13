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
       String message;
       if((headerComponents[1].startsWith("/echo/"))){
           message = headerComponents[1].substring("/echo/".length());
           status = "200 OK";
       }
       else{
           message = "";
           status = "404 Not Found";
       }
       OutputStream outputStream = socket.getOutputStream();
       String response;
       String typeOfMessage = "text/plain";
       if(!message.isEmpty()){
           response = String.format("HTTP/1.1 %s\r\nContent-Type: %s\r\nContent-Length: %s\r\n\r\n%s",status,typeOfMessage,String.valueOf(message.length()),message);
       }
       else{
           response = String.format("HTTP/1.1 %s\r\n\r\n");
       }
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
