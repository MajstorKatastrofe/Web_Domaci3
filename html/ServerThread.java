package HTML.Main.src.html;

import HTML.Main.src.app.RequestHandler;
import HTML.Main.src.html.response.*;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerThread implements Runnable{

    private List<String> quotes = new CopyOnWriteArrayList<>();
    private static String newQuote;
    private String author;
    private String quote;
    private final Socket socket;
    BufferedReader in;
    PrintWriter out;

    public ServerThread(Socket socket, List<String> quotes) {
        this.socket = socket;
        this.quotes = quotes;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{

            String requestLine = in.readLine();

            StringTokenizer stringTokenizer = new StringTokenizer(requestLine);

            String method = stringTokenizer.nextToken();
            String path = stringTokenizer.nextToken();

            System.out.println("\nHTTP CLIENT REQUEST:\n");
            do{
                System.out.println(requestLine);
                requestLine = in.readLine();
            } while (!requestLine.trim().equals(""));

            if (method.equals(HTTPMethod.POST.toString())) {

                String contentLengthHeader = "Content-Length: ";
                int startIndex = requestLine.indexOf(contentLengthHeader) + contentLengthHeader.length();
                int endIndex = requestLine.indexOf("\r\n", startIndex);
                int contLength = Integer.parseInt(requestLine.substring(startIndex, endIndex).trim());


                char[] buf = new char[contLength];
                in.read(buf);
                String s = new String(buf);

                if (s.contains("author") && s.contains("quote")) {
                    String niz1[] = s.split("&");
                    String niz2[] = niz1[0].split("=");
                    String authorEncoded = niz2[1];
                    author = URLDecoder.decode(authorEncoded, "UTF-8");
                    String niz3[] = niz1[1].split("=");
                    quote = niz3[1].trim();
                    makeQuote(author, quote);
                }
                System.out.println(s);
            }

            Request request = new Request(HTTPMethod.valueOf(method), path);

            RequestHandler requestHandler = new RequestHandler(quotes);
            Response response = requestHandler.handle(request);

            System.out.println("\nHTTP response:\n");
            System.out.println(response.getResponseString());

            out.println(response.getResponseString());

            in.close();
            out.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeQuote(String author, String quote){
        newQuote = author + ": " + "\"" + quote.replace("+", " ") + "\"";
    }

    public static String getNewQuote() {
        return newQuote;
    }
}
