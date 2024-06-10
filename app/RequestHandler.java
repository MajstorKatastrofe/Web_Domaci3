package HTML.Main.src.app;

import HTML.Main.src.html.HTTPMethod;
import HTML.Main.src.html.Request;
import HTML.Main.src.html.response.Response;

import java.util.List;

public class RequestHandler {

    private List<String> quotes;

    public RequestHandler(List<String> quotes) {
        this.quotes = quotes;
    }

    public Response handle(Request request) throws Exception{
        if(request.getPath().equals("/quotes") && request.getHttpMethod().equals(HTTPMethod.GET)){
            return (new QuotesController(request, quotes)).doGet();
        }else if (request.getPath().equals("/save-quote") && request.getHttpMethod().equals(HTTPMethod.POST)){
            return (new QuotesController(request, quotes)).doPost();
        }

        throw new Exception("Page: " + request.getPath() + ". Method: " + request.getHttpMethod() + "not found!");
    }

}
