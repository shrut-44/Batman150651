import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String requestLine;
    private final Map<String,String> requestHeaders;
    private final byte[] body;
    private final String method;
    private final String path;
    public HttpRequest(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        String requestHeader = readHeaders(inputStream);
        String[] lines = requestHeader.split("\r\n");
        this.requestLine = lines[0];
        this.method = requestLine.split(" ")[0];
        this.path = requestLine.split(" ")[1];
        this.requestHeaders = parseHeaders(lines);
        int contentLength = 0;
        if (requestHeaders.containsKey("content-length")) {
            contentLength = Integer.parseInt(
                    requestHeaders.get("content-length")
            );
        }
        this.body = inputStream.readNBytes(contentLength);
        inputStream.close();
    }

    private String readHeaders(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int b;
        int[] lastFour = new int[4];
        int index = 0;
        while ((b = input.read()) != -1) {
            buffer.write(b);
            lastFour[index % 4] = b;
            index++;
            if (index >= 4 &&
                    lastFour[(index - 4) % 4] == '\r' &&
                    lastFour[(index - 3) % 4] == '\n' &&
                    lastFour[(index - 2) % 4] == '\r' &&
                    lastFour[(index - 1) % 4] == '\n') {
                break;
            }
        }
        return buffer.toString(java.nio.charset.StandardCharsets.ISO_8859_1);
    }

    private Map<String, String> parseHeaders(String[] lines) {
        Map<String, String> headers = new HashMap<>();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                String name = parts[0].trim();
                String value = parts[1].trim();
                headers.put(name.toLowerCase(), value);
            }
        }
        return headers;
    }
    public String getRequestLine() {
        return requestLine;
    }
    public String getMethod(){
        return this.method;
    }
    public String getPath(){
        return this.path;
    }
    public String getHeader(String name) {
        return requestHeaders.get(name.toLowerCase());
    }
    public byte[] getBody() {
        return body;
    }
}
