package com.eleks.academy.whoami;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import com.eleks.academy.whoami.core.Game;
import com.eleks.academy.whoami.networking.client.ClientPlayer;
import com.eleks.academy.whoami.networking.server.Server;
import com.eleks.academy.whoami.networking.server.ServerImpl;

public class App {

	public static int CLIENT_NUMBER = 3;
	
    public static void main(String[] args) throws IOException {
        System.out.println( "Game init!" );
        System.out.println("Number of players: " + CLIENT_NUMBER);
        
        Server server = new ServerImpl(888);
        
        Game game = server.startGame();
        
        Socket clientSocket;
        
        BufferedReader reader;
        
        List<Socket> clientSockets = new LinkedList<>();
        
        List<BufferedReader> readers = new LinkedList<>();
        
        while (clientSockets.size() < CLIENT_NUMBER) {
        	clientSocket = server.waitForPlayer(game);
        	clientSockets.add(clientSocket);
        }
        
        System.out.println("Read client names");
        
        for (int i = 0; i < clientSockets.size(); i++) {
        	reader = new BufferedReader(new InputStreamReader(clientSockets.get(i).getInputStream()));
            readers.add(reader);
        	String playerName = reader.readLine();
        	System.out.println("Player name: " + playerName);
        	server.addPlayer(new ClientPlayer(playerName, clientSockets.get(i)));
        }
        
        System.out.println("Clients are successfully connected!");
        boolean turnResult = true;        
        boolean gameStatus = true;
    	game.assignCharacters();
    	game.initGame();
    	
        	while (gameStatus) {
        		turnResult = game.makeTurn();
        	while (turnResult) {
        		turnResult = game.makeTurn();
        	}
        	game.changeTurn();
        	gameStatus = !game.isFinished();
        }
        	for (int i = 0; i < CLIENT_NUMBER; i++) {
                server.stopServer(clientSockets.get(i), readers.get(i));
        	}
        	
        System.out.println("Game is finished!");
    }

}
