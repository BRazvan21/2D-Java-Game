package Main;

import object.OBJ_Heart;
import object.SuperObject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class UI {
    GamePanel qp;
    Graphics2D g2;
    Font maruMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank;
    BufferedImage image, image2, image3, image4, image5;
    private int imgX, imgY;
    public static String[] ImageURL;

    UtilityTool uTool = new UtilityTool();
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean GameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; //0: first screen 1:second screen

    private static final String CLIENT_ID = "122ea05192a3454099e15dcec1472267";
    private static final String CLIENT_SECRET = "105a6fea566e4c37997cb2b9d21c930c";
    List<TrackInfo> trackInfos = new ArrayList<>();
    public UI(GamePanel qp) throws Exception {
        this.qp = qp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //CREATE HUD OBJECT
        SuperObject heart = new OBJ_Heart(qp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        String accessToken = getAccessToken(CLIENT_ID, CLIENT_SECRET);
        System.out.println("Access Token: " + accessToken);
        getTopChiptuneTrackInfos(accessToken);
    }

    public void showMessage(String text){

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        //title state
        if(qp.gameState == qp.titleState){
            drawTitleScreen();
        }
        //play state
        if(qp.gameState == qp.playState){
            drawPlayerLife();
        }
        //pause state
        if(qp.gameState == qp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        //dialogue state
        if(qp.gameState == qp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }
    }
    public void drawPlayerLife(){

        //qp.player.life = 3;

        int x = qp.tileSize/2;
        int y = qp.tileSize/2;
        int i = 0;
        //DRAW BLANK HEART
        while(i < qp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += qp.tileSize;
        }
        x = qp.tileSize/2;
        y = qp.tileSize/2;
        i = 0;

        //DRAW CURRENT LIFE
        while(i < qp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < qp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += qp.tileSize;
        }

    }
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
    public void getTopChiptuneTrackInfos(String accessToken) throws Exception {
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
                String imageUrl = !images.isEmpty() ? images.getJSONObject(0).getString("url") : "No image";
                this.trackInfos.add(new TrackInfo(name, popularity, artist, spotifyUrl, imageUrl));
            }
        }
    }

    public void drawTitleScreen() {
        //Title name
        if(titleScreenState == 0){
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0,0,qp.screenWidth, qp.screenHeight);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
            String text = "My 2D Game";
            int x = getXforCenteredText(text);
            int y = qp.tileSize*3;
            //Shadows
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);
            //Main Color
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //BLUE BOY IMAGE
            x = qp.screenWidth/2 - (qp.tileSize*2)/2;
            y += qp.tileSize*2;
            g2.drawImage(qp.player.down1,x,y, qp.tileSize*2, qp.tileSize*2, null);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += qp.tileSize*3.5;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "OPTIONS";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x-qp.tileSize, y);
            }
        }
        else if(titleScreenState == 1){
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class";
            int x = getXforCenteredText(text);
            int y = qp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += qp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "Thief";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "BACK";
            x = getXforCenteredText(text);
            y += qp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x-qp.tileSize, y);
            }
        }
        else if(titleScreenState == 2){
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Options";
            int x = getXforCenteredText(text);
            int y = qp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Sound vol";
            x = getXforCenteredText(text);
            y += qp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "Music vol";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "Spotify Playlist";
            x = getXforCenteredText(text);
            y += qp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-qp.tileSize, y);
            }

            text = "BACK";
            x = getXforCenteredText(text);
            y += qp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x-qp.tileSize, y);
            }
        }
        else  if (titleScreenState == 3) {
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));
            String title = "Spotify Playlist";
            int titleX = getXforCenteredText(title);
            int titleY = qp.tileSize + qp.tileSize / 2;
            g2.drawString(title, titleX, titleY);

            int imgX = 50; // Left margin for images
            int imgWidth = 70;
            int imgHeight = 70;
            int imgGapY = 10; // Space between images
            int topY = titleY + qp.tileSize;

            for (int i = 0; i < trackInfos.size() && i < 5; i++) {
                TrackInfo track = trackInfos.get(i);
                int currImgY = topY + i * (imgHeight + imgGapY);
                try {
                    URL imageUrl = new URL(track.getImageUrl());
                    BufferedImage image = ImageIO.read(imageUrl);
                    g2.drawImage(image, imgX, currImgY, imgWidth, imgHeight, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Draw track name to the right of the image
                int textX = imgX + imgWidth + 30;
                int textY = currImgY + imgHeight / 2 + g2.getFont().getSize() / 2;
                g2.drawString(track.getName(), textX, textY);

                // Draw '>' selector if needed
                if (commandNum == i) {
                    g2.drawString(">", textX - 30, textY); // 30 is marker offset, can adjust
                }
            }

            // Draw BACK option below the last track
            String backText = "BACK";
            int backX = 7*qp.tileSize;
            int backY = topY + (imgHeight + imgGapY) * 5 + 20;
            g2.drawString(backText, backX, backY);
            if (commandNum == 5) {
                g2.drawString(">", backX - 30, backY);
            }
        }
    }
    public void drawPauseScreen(){

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = qp.screenHeight/2;

        g2.drawString(text,x,y);
    }

    public void drawDialogueScreen(){

        int x = qp.tileSize*2;
        int y = qp.tileSize/2;
        int width = qp.screenWidth - (qp.tileSize*4);
        int height = qp.tileSize*4;

        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
        x += qp.tileSize;
        y += qp.tileSize;

        for(String line : currentDialogue.split("/n")){
            g2.drawString(line,x,y);
            y += 40;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }

    public int getXforCenteredText(String text){

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = qp.screenWidth/2 - length/2;
        return x;
    }
}
