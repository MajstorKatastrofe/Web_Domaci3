package HTML.Main.src.html.response;

public class HTMLResponse extends Response{

    private final String html;

    public HTMLResponse(String html) {
        this.html = html;
    }

    @Override
    public String getResponseString() {
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
        response += html;
        return response;
    }
}
