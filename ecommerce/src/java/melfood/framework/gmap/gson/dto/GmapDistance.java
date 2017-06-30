package melfood.framework.gmap.gson.dto;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GmapDistance {
    public static void main(String[] args) throws IOException {
        // Reference - https://developers.google.com/maps/documentation/distancematrix/
        //URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC|Seattle&destinations=San+Francisco|Victoria+BC");
        URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC&destinations=San+Francisco");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        String line, outputString = "";
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        while ((line = reader.readLine()) != null) {
            outputString += line;
        }
        System.out.println(outputString);
        GMapResult capRes = new Gson().fromJson(outputString, GMapResult.class);
        System.out.println(capRes);

        System.out.println("Distance : "  + capRes.getRows()[0].getElements()[0].getDistance().getValue());
        System.out.println("Duration : "  + capRes.getRows()[0].getElements()[0].getDuration().getValue());

    }
}
