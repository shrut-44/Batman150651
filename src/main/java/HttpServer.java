import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private final ServerSocket serverSocket;
    public HttpServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setReuseAddress(true);
    }
    public Socket accept() throws IOException {
        return serverSocket.accept();
    }
    public void close() throws IOException {
        serverSocket.close();
    }
}
