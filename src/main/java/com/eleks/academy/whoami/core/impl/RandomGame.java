package com.eleks.academy.whoami.core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.eleks.academy.whoami.core.Game;
import com.eleks.academy.whoami.core.Player;
import com.eleks.academy.whoami.core.Turn;

public class RandomGame implements Game {
	
	private Map<String, String> playersCharacter = new HashMap<>();
	private List<Player> players = new ArrayList<>();
	private List<String> availableCharacters;
	private Turn currentTurn;
	private boolean win;
	private boolean action;
	private static final String YES = "Yes";
	private static final String NO = "No";
	
	public RandomGame(List<String> availableCharacters) {
		this.availableCharacters = new ArrayList<>(availableCharacters);
	}

	@Override
	public void addPlayer(Player player) {
		this.players.add(player);		
	}

	@Override
	public boolean makeTurn() {
		
		Player currentGuesser = currentTurn.getGuesser();
		List<String> answers;
		
		if (currentGuesser.isReadyForGuess()) {
			String guess = currentGuesser.getGuess();
			
			answers = currentTurn.getOtherPlayers()
					   .stream()
					   .map(player -> player.answerGuess(guess, this.playersCharacter.get(currentGuesser.getName())))
					   .collect(Collectors.toCollection(LinkedList::new));
			if (answers.size() > 0) {
				if (answers.get(0).equals(YES)) {
					System.out.println("General answer is: " + YES);
					win = true;
				} else {
					System.out.println("General answer is: " + NO);
					win = false;
				}
				if (win) {
					System.out.println("The winner is: " + currentGuesser.getName());
					players.remove(currentGuesser);
				}
			}

		return win;
		} else {
			String question = currentGuesser.getQuestion();
			answers = currentTurn.getOtherPlayers()
					   .stream()
					   .map(player -> player.answerQuestion(question, this.playersCharacter.get(currentGuesser.getName())))
					   .collect(Collectors.toCollection(LinkedList::new));
			if (answers.size() > 0) {
				if (answers.get(0).equals(YES)) {
					System.out.println("General answer is: " + YES);
					action = true;
				} else {
					System.out.println("General answer is: " + NO);
					action = false;
				}
			}
			return action;
		}
	}

	@Override
	public void assignCharacters() {
		players.stream()
			   .forEach(player -> this.playersCharacter.put(player.getName(), this.getRandomCharacter()));
	}
	
	@Override
	public void initGame() {
		this.currentTurn = new TurnImpl(this.players);
	}

	@Override
	public boolean isFinished() {
		return players.size() == 1;
	}
	
	private String getRandomCharacter() {
		int randomPos = (int) Math.random() * this.availableCharacters.size();
		return this.availableCharacters.remove(randomPos);
	}

	@Override
	public void changeTurn() {
		this.currentTurn.changeTurn();
	}

}
