package HTML.Main.src.app;

import HTML.Main.src.html.ServerThread;
import HTML.Main.src.html.Request;
import HTML.Main.src.html.response.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class QuotesController extends Controller{
    private List<String> quotes = new CopyOnWriteArrayList<>();
    public QuotesController(Request request, List<String> quotes) {
        super(request);
        this.quotes = quotes;
    }

    @Override
    public Response doGet() {
        String htmlBody = "" +
                "<form method=\"POST\" action=\"/save-quote\">" +
                "<div class=\"form-group\">" +
                "<label for=\"author\">Author</label>" +
                "<input id=\"author\" name=\"author\" type=\"text\" class=\"form-control\">" +
                "</div>" +
                "<div class=\"form-group\">" +
                "<label for=\"quote\">Quote</label>" +
                "<input id=\"quote\" name=\"quote\" type=\"text\" class=\"form-control\">" +
                "</div>" +
                "<button class=\"btn btn-primary\">Save quote</button>" +
                "</form>";

        String famousQuotes = QuoteOfTheDayService.getQuoteOfTheDay();
        String s = "<div class=\"quote-of-the-day\"><b>Quote of the day:</b><br>\"" + famousQuotes + "\"</div>";
        String s1 = "<div class=\"saved-quotes\"><b>Saved Quotes</b><br>";

        if(quotes.isEmpty()){
            s1 += "<p>There are no saved quotes.</p>";
        }
        for (int i = 0; i < quotes.size(); i++) {
            s1 += "<p>" + quotes.get(i) + "</p>";
        }
        s1 += "</div>";

        String content = "<html><head><title>Main Server</title>" +
                "<style>" +
                "body { font-family: 'Arial', sans-serif; background-color: #f8f9fa; margin: 0; padding: 20px; display: flex; flex-direction: column; align-items: center; }" +
                "form { max-width: 500px; width: 100%; padding: 20px; background: #fff; border: 1px solid #dee2e6; border-radius: 5px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }" +
                ".form-group { margin-bottom: 15px; }" +
                "label { display: block; margin-bottom: 5px; font-weight: bold; }" +
                ".form-control { width: 100%; padding: 10px; border: 1px solid #ced4da; border-radius: 4px; }" +
                ".btn { display: inline-block; font-weight: 400; color: #fff; text-align: center; vertical-align: middle; cursor: pointer; background-color: #007bff; border: 1px solid #007bff; padding: 10px 20px; border-radius: 4px; text-decoration: none; }" +
                ".btn-primary:hover { background-color: #0056b3; border-color: #0056b3; }" +
                ".quote-of-the-day, .saved-quotes { max-width: 500px; width: 100%; margin-top: 20px; background: #fff; padding: 20px; border: 1px solid #dee2e6; border-radius: 5px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }" +
                ".quote-of-the-day { text-align: center; font-style: italic; }" +
                ".saved-quotes p { margin: 0 0 10px; }" +
                "</style>" +
                "</head>\n";
        content += "<body>" + htmlBody + s + s1 + "</body></html>";
        return new HTMLResponse(content);
    }

    @Override
    public Response doPost() {
        System.out.println("Quote saved.");
        String quote = ServerThread.getNewQuote();
        System.out.println(quote);

        quotes.add(quote);
        return new RedirectResponse("/quotes");
    }
}
