package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MettlerScale implements IMettlerScale {

    private Socket pingSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public MettlerScale(String host, int port){
        try{
            pingSocket = new Socket(host, port);
            out = new PrintWriter(pingSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));
        } catch (IOException e){
            return;
        }
    }

    @Override
    public String requestUserInput(String text){
        // We already return a read line from the following command. RM20 uses #2 response
        scaleRequest(String.format("RM20 8 \"%s\" \"\" \"&3\"", text));
        String returnString = "";
        try{
            returnString = in.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }

        return returnString;
    }

    @Override
    public String taraWeight(){
        return scaleRequest("T").split(" ")[2];
    }

    @Override
    public String getWeight(){
        // Read the current weight number from the weight
        return scaleRequest("S").split(" ")[6];
    }

    @Override
    public boolean isConnected(){
        return pingSocket.isConnected();
    }

    private String scaleRequest(String text){
        out.println(text);
        String returnString = "";
        try{
            returnString = in.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }

        return returnString;
    }
}
