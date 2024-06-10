package HTML.Main.src.app;

import HTML.Main.src.html.Request;
import HTML.Main.src.html.response.Response;

public abstract class Controller {

    protected Request request;

    public Controller(Request request) {
        this.request = request;
    }

    public abstract Response doGet();
    public abstract Response doPost() throws InstantiationException, IllegalAccessException;
}
