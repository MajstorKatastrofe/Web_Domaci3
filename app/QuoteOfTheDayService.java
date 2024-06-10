package HTML.Main.src.app;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class QuoteOfTheDayService {

    public static String getQuoteOfTheDay(){
        String famousQoutes = "";
        //The only limit to our realization of tomorrow is our doubts of today. - Franklin D. Roosevelt
        try{
            Socket socket = new Socket("localhost", 8081);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("GET /quote-of-the-day");

            String jsonResponse = in.readLine();
            famousQoutes = parseJson(jsonResponse);

            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return famousQoutes;
    }

    private static String parseJson(String jsonResponse){
        try{
            JSONObject jsonObject = new JSONObject(jsonResponse);
            String quote = jsonObject.getString("quote");
            return quote;
        } catch (JSONException e){
            e.printStackTrace();
            return "JSON not parsed.";
        }
    }
}


