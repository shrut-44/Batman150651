import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpResponse {
    private String responseMessage;
    private final OutputStream outputStream;
    public HttpResponse(Socket socket) throws IOException {
        this.outputStream = socket.getOutputStream();
    }
    public void buildResponse(String status, String length, String body){
        String response = "HTTP/1.1 %s\r\nContent-Type: text/plain\r\nContent-Length: %s\r\n\r\n%s";
        this.responseMessage = String.format(response,status,length,body);
    }
    public void buildResponse(String status){
        String response = "HTTP/1.1 %s\r\n\r\n";
        this.responseMessage = String.format(response,status);
    }
    public void write() throws IOException{
        outputStream.write(responseMessage.getBytes());
        outputStream.flush();
    }
    public void close() throws IOException {
        outputStream.close();
    }

}
