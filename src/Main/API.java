package Main;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;

public class API {

    // Client ID și Secret Spotify
    private static final String CLIENT_ID = "122ea05192a3454099e15dcec1472267";
    private static final String CLIENT_SECRET = "105a6fea566e4c37997cb2b9d21c930c";

    public static void main(String[] args) {
        try {
            // Obține tokenul de acces
            String accessToken = getAccessToken(CLIENT_ID, CLIENT_SECRET);
            System.out.println("Access Token: " + accessToken);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metoda pentru obținerea tokenului OAuth Spotify
    public static String getAccessToken(String clientId, String clientSecret) throws Exception {
        String url = "https://accounts.spotify.com/api/token";
        String authString = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setDoOutput(true);

        con.setRequestProperty("Authorization", "Basic " + encodedAuth);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String urlParameters = "grant_type=client_credentials";
        con.getOutputStream().write(urlParameters.getBytes(StandardCharsets.UTF_8));

        int responseCode = con.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getString("access_token");
        } else {
            throw new Exception("Failed to get access token: HTTP error code: " + responseCode);
        }
    }

    // Metodă pentru obținerea top 5 melodii chiptune după popularitate
    public static List<TrackInfo> getTopChiptuneTrackInfos(String accessToken) throws Exception {
        String query = "genre:chiptune";
        int maxOffset = 1000; // Opțional, pentru shuffle
        int offset = new Random().nextInt(maxOffset);

        String url = "https://api.spotify.com/v1/search?q=" + query +
                "&type=track&limit=50&offset=" + offset;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<TrackInfo> trackInfos = new ArrayList<>();
        if (response.statusCode() == 200) {
            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray tracks = jsonResponse.getJSONObject("tracks").getJSONArray("items");

            List<JSONObject> trackList = new ArrayList<>();
            for (int i = 0; i < tracks.length(); i++) {
                trackList.add(tracks.getJSONObject(i));
            }

            Collections.shuffle(trackList);
            trackList = trackList.stream().limit(5).collect(Collectors.toList());

            for (JSONObject track : trackList) {

                String name = track.getString("name");
                int popularity = track.getInt("popularity");
                String artist = track.getJSONArray("artists").getJSONObject(0).getString("name");
                String spotifyUrl = track.getJSONObject("external_urls").getString("spotify");
                JSONArray images = track.getJSONObject("album").getJSONArray("images");
                String imageUrl = images.length() > 0 ? images.getJSONObject(0).getString("url") : "No image";
                trackInfos.add(new TrackInfo(name, popularity, artist, spotifyUrl, imageUrl));
            }
        }
        return trackInfos;
    }
}
