import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Demo {
	
	public static void main(String[]args) {
		
		Scanner scnr = new Scanner (System.in);
		
		//A few variables
		boolean flag = true;
		String secret = "default";
		String guess = "???";
		List<Character> previousGuesses = new ArrayList<>();
		
		//Welcome text
		System.out.println("Hello! Thanks for playing this demo version of hang man!");
		
		System.out.println("To start, enter the secret string you'd like to guess.");
		
		//I imagine we'll get whitespace issues eventually but I'll sort that out later.
		secret = scnr.nextLine();
		
		System.out.println("Great! Each dash represents a letter that needs to be found.");
		
		//variable to count wrong guesses.
		int wrong = 0;
		
		//Main logic is running in a while loop.
		while(flag) {
			
			//Prints Hangman Drawing.
			printHangedMan(wrong);
			
			if(wrong >= 6) {
				System.out.print("You lost this game!");
				break;
			}
			
			//Calling the function that checks guesses and prints dashes
			printWordState(secret, previousGuesses);
			
			//Calling function that prompts user to guess letters.
			if(!getGuess(scnr, secret, previousGuesses)) {
				++wrong;
			}
			
			//Lets user know if they win by guessing letters.
			if(printWordState(secret, previousGuesses)) {
				System.out.println("You win!");
				break;
				
			}
			
		}	
		
	}

	private static void printHangedMan(int wrong) {
		//Draws the "rope"
		System.out.println(" xxxxxxx");
		System.out.println(" |     |");
		
		//Draws the head
		if(wrong >= 1) {
			System.out.println(" O");
		}
		
		//Draws one or both arms.
		if(wrong >= 2) {
			System.out.print("\\ ");
			
			if(wrong >= 3) {
				System.out.println("/");
			}
			else {
				System.out.println("");
			}
		}
		
		//Draws the chest.
		if(wrong >= 4) {
			System.out.println(" |");
		}
		
		//Draws one or both legs.
		if(wrong >= 5) {
			System.out.print("/ ");
			
			if(wrong >=6) {
				System.out.println("\\");
			}
			else {
				System.out.println("");
			}
		}
		System.out.println("");
	}
	
	//Prompts user to guess letters. Returns true if letter found.
	private static boolean getGuess(Scanner scnr, String secret, List<Character> previousGuesses) {
		String guess;
		System.out.println("Enter a letter to see if it's found in the secret string.");
		
		//This gets the first character of input and adds it to the list of guesses.
		guess = scnr.nextLine();
		previousGuesses.add(guess.charAt(0));
		
		return (secret.contains(guess));
		
	}
	
	//Checks player guesses verses secret word and prints dashes when not found.
	//Returns true if all letters are guessed.
	private static boolean printWordState(String secret, List<Character> previousGuesses) {
		int correct = 0;
		for (int i = 0; i < secret.length(); ++i) {
			
			if(previousGuesses.contains(secret.charAt(i))) {
				
				System.out.print(secret.charAt(i));
				correct++;
			}
			else {
				System.out.print("-");
			}
		}
		System.out.println();
		
		return (secret.length() == correct);
		
	}	
}
