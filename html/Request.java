package HTML.Main.src.html;

public class Request {
    private final HTTPMethod httpMethod;
    private final String path;

    public Request(HTTPMethod httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }

    public HTTPMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }
}
