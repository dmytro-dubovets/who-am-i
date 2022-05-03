package com.eleks.academy.whoami.core.impl;

import java.util.ArrayList;
import java.util.List;

import com.eleks.academy.whoami.core.Player;

public class RandomPlayer implements Player {

	private String name;
	private List<String> availableQuestions;
	private List<String> availableGuesses;
	
	public RandomPlayer(String name, List<String> availableQuestions, List<String> availableGuesses) {
		this.name = name;
		this.availableQuestions = new ArrayList<>(availableQuestions);
		this.availableGuesses = new ArrayList<>(availableGuesses);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getQuestion() {
		int randomPos;
		String question;
		if (this.availableGuesses.size() > 0) {
			randomPos = (int) Math.random() * this.availableQuestions.size();
			question = this.availableQuestions.remove(randomPos);
			System.out.println("Player: " + name + " Asks: " + question);
			return question;
		}
		return null;
	}

	@Override
	public String answerQuestion(String question, String character) {
		String answer = Math.random() < 0.5 ? "Yes" : "No";
		System.out.println("Player: " + name + " Answers: " + answer);
		return answer;
	}

	@Override
	public String answerGuess(String guess, String character) {
		String answerGuess = Math.random() < 0.5 ? "Yes" : "No";
		System.out.println("Player: " + name + " Answer guess: " + answerGuess);
		return answerGuess;
	}
	
	@Override
	public String getGuess() {
		int randomPos;
		String guess;
		if (this.availableGuesses.size() > 0) {
			randomPos = (int) Math.random() * this.availableGuesses.size();
			guess = this.availableGuesses.remove(randomPos);
			System.out.println("Player: " + name + " Guess: Am I - " + guess);
			return guess;
		}
		return null;
	}
	
	@Override
	public boolean isReadyForGuess() {
		return availableQuestions.isEmpty();
	}
	
}
