package com.eleks.academy.whoami.networking.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.eleks.academy.whoami.core.Game;
import com.eleks.academy.whoami.core.Player;
import com.eleks.academy.whoami.core.impl.RandomGame;
import com.eleks.academy.whoami.core.impl.RandomPlayer;

public class ServerImpl implements Server {
	
	List<String> characters = List.of("Bot", "Batman", "Superman", "Sakura");
	
	List<String> availableQuestions = List.of("Am I a human?", "Am I character from a movie?");
	
    List<String> availableGuesses = List.of("Bot", "Batman", "Superman", "Sakura");
    
	private Game game = new RandomGame(characters);
	
	private final ServerSocket serverSocket;
	
	public ServerImpl(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
	}

	@Override
	public Game startGame() throws IOException {
		game.addPlayer(new RandomPlayer("Bot", availableQuestions, availableGuesses));
		System.out.println("Server starts");
		System.out.println("Waiting for a client connect...");
		return game;
	}

	@Override
	public Socket waitForPlayer(Game game) throws IOException {
		
		return serverSocket.accept();
	}

	@Override
	public void addPlayer(Player player) {
		game.addPlayer(player);
		System.out.println("Player: " + player.getName() + " Connected to the game!");	
	}

	@Override
	public void stopServer(Socket clientSocket, BufferedReader reader) throws IOException {
		clientSocket.close();
		reader.close();
	}

}
