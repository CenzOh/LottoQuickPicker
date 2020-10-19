/**
 * LAB 3 -  Lotto QuickPicker Game 
 */
package edu.cuny.csi.csc330.lab3;

import java.math.BigInteger; //needed fir calculateOdds
import java.text.NumberFormat;
import java.util.*;
import edu.cuny.csi.csc330.util.Randomizer;

public class LottoQuickPicker {
	
	// constants  specific to current game - BUT NOT ALL GAMES 
	public final static int DEFAULT_GAME_COUNT = 1; 
	private final static String GAME_NAME = "Lotto"; 
	private final static int SELECTION_POOL_SIZE = 59; 
	private final static int SELECTION_COUNT = 6; 


	public LottoQuickPicker() {
		init(DEFAULT_GAME_COUNT); 
	}
	
	public LottoQuickPicker(int games) {
		init(games); 
	}
  

	private void init(int games) {
		
		/**
		 * 
		 * Now what ... START FROM HERE 
		 * What additional methods do you need?
		 * What additional data properties/members do you need? 
		 */
		
		displayHeading();
		
		for (int i = DEFAULT_GAME_COUNT; i <= games; i++) {
			int low = 1;
			int numbers[] = new int[SELECTION_COUNT];
			
			if(i<10)
				System.out.printf("%-5s", "(" + " " + i + ")"); //white space for 10th digit
			else
				System.out.printf("%-5s", "(" + i + ")");
			
			for(int j = 0; j < SELECTION_COUNT; j++) {
				int pick = Randomizer.generateInt(low, SELECTION_POOL_SIZE);
				if(j == 0 || checkDuplicate(numbers, pick) == false) { 
					numbers[j] = pick;
				} else 
					j--;
			}
			Arrays.parallelSort(numbers);
			
			for(int k = 0; k < SELECTION_COUNT; k++) {
				if(numbers[k] < 10) {
					StringBuffer s = new StringBuffer();
					System.out.print(s.append(0));
					System.out.printf(numbers[k] + " ");
				} else
					System.out.printf(numbers[k] + " ");
			}
			System.out.println();
		}
	}
	
	private static boolean checkDuplicate(int[] numbers, int value) {
		for(int i = 0; i < numbers.length; i++) {
			if(numbers[i] == value)
				return true;
		}
		return false;
	}


	/**
	 * 
	 */
	public void displayTicket() {
		
		/**
		 * display heading 
		 * 
		 * for i in gameCount 
		 * 		generate selectionCount number of unique random selections in ascending order 
		 * 
		 * display footer 
		 */
		
		
		
		// display ticket heading //called earlier
		//displayHeading(); 
		
		
		
		/**
		 * Display selected numbers 
		 */
		//Calculate and display the odds before footer
		calculateOdds(); 
		
		// display ticket footer 
		displayFooter(); 
		
		
		
		return;
	}
	
	protected void displayHeading() {
		System.out.println(
			"---------------------------------\n----------- " + GAME_NAME.toUpperCase() + " ---------------");
		Date currentDate = new Date();
		System.out.println(currentDate);
		System.out.print('\n'); //gives space between dates and lotto numbers
		
	}
	
	protected void displayFooter() {
		System.out.print('\n'); //gives space between last lotto number and message
		System.out.println("----- (c) S.I. Corner Deli -----\n--------------------------------");
		
	}
	
	
	/**
	 * 
	 * @return
	 */
	private long calculateOdds() {
 
		/* This is the mathematical equation:
		 * 
		 * 	59! / ((59-6)!*6!)
		 * Note: 59-6! is essentially 53!
		 * 
		 */
		BigInteger num; //numerator 
		BigInteger den1; //denominator 1, these numbers will be huge!
		BigInteger den2; //denominator 2
		
		BigInteger finalNum;
		int number1 = 6; //pick 6 numbers
		int number2 = 59; //out of 59. Can swap these values to see any combination!!
		int number3 = number2-number1;
		
		
		
		
		/*		//did not work
		//calculating numerator
		for(int i = 1; i < number2; i++) {
			num+=num*i;
		}
		
		
		//calculating first part of denominator
		for(int i = 1; i < number2-number1; i++)
			den1+=den1*i;
		
		//calculating second part of denominator
		for(int i = 1; i < number1; i++)
			den2+=den2*i;
		*/
		num = factorial(number2);
		den1 = factorial(number1);
		den2 = factorial(number3);
		
		finalNum = num.divide(den2.multiply(den1)); //we get 45057474, let's format it
		
		long finalLong = finalNum.longValue();
		BigInteger integer = BigInteger.valueOf(finalLong); //convert BigInt to a long var
		
		String result = NumberFormat.getNumberInstance(Locale.US).format(integer); //formats number
		System.out.println();//gives some space
		System.out.print("Odds of Winning: 1 in " + result); //prints 45,057,474!!
		
		return 0;
	}
	
	public static BigInteger factorial(int number) { //BigInt for calculatingOdds
		BigInteger factorial = BigInteger.ONE;
		for(int i = number; i > 0; i--) {
			factorial = factorial.multiply(BigInteger.valueOf(i));
		}
		return factorial;
	}
  

	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		// takes an optional command line parameter specifying number of QP games to be generated
		//  By default, generate 1  
		int numberOfGames  = DEFAULT_GAME_COUNT; 
		
		if(args.length > 0) {  // if user provided an arg, assume it to be a game count
			numberOfGames = Integer.parseInt(args[0]);  // [0] is the 1st element!
		}
		
		LottoQuickPicker lotto = new LottoQuickPicker(numberOfGames);
		// now what 
		 
		lotto.displayTicket(); 
		
		// COMMENT THIS OUT WHEN YOU'RE DONE ... 
		//System.out.println("Leaving ...");

	}

}
