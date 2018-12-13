import java.util.Scanner;
public class FracCalc {

	/**
	 * Prompts user for input, passes that input to produceAnswer, then outputs the result.
	 * @param args - unused
	 */
	public static void main(String[] args) 
	{
		Scanner scanner =new Scanner(System.in);
		//Prompts the user to input their equation
		System.out.println("Input fraction equation. Make sure to separate mixed number with a _"); 
		String output;
		String input=scanner.nextLine();
		while(!input.equals("quit")) {
			output=produceAnswer(input);
			System.out.println(output);
			System.out.println("Input fraction equation. Make sure to separate mixed number with a _"); 
			input=scanner.nextLine();
		}
		// TODO: Read the input from the user and call produceAnswer with an equation
		// Checkpoint 1: Create a Scanner, read one line of input, pass that input to produceAnswer, print the result.
		// Checkpoint 2: Accept user input multiple times.
		scanner.close();
	}

	/**
	 * produceAnswer - This function takes a String 'input' and produces the result.
	 * @param input - A fraction string that needs to be evaluated.  For your program, this will be the user input.
	 *      Example: input ==> "1/2 + 3/4"
	 * @return the result of the fraction after it has been calculated.
	 *      Example: return ==> "1_1/4"
	 */

	public static String produceAnswer(String input)
	{ 
		String operator="";
		String operand1="";
		String operand2="";
		//Tests all indexes of the strings to detect operands and operator
		int test=0;
		int count=0;
		while(test==0) {
			if(input.charAt(count)==' '){
				test=1;
				operator=input.substring(count+1,count+2);
				operand1=input.substring(0,count);
				operand2=input.substring(count+3);
			}
			count++;
		}
		//Finds the whole number,numerator, and denominator of operand1
		int underscoreIndex1=operand1.indexOf("_");
		int slashIndex1=operand1.indexOf("/");
		int currentIndex1=0;
		int numerator1=0;
		int whole1=0; 
		int denominator1=1;

		if(slashIndex1!=-1){
			if(underscoreIndex1!=-1) {
				whole1=Integer.parseInt(operand1.substring(0,underscoreIndex1));
				currentIndex1=underscoreIndex1+1;
			}
			numerator1=Integer.parseInt(operand1.substring(currentIndex1,slashIndex1));
			denominator1=Integer.parseInt(operand1.substring(slashIndex1+1));
		}else {
			whole1=Integer.parseInt(operand1);
		}
		////Finds the whole number,numerator, and denominator of operand2
		int underscoreIndex2=operand2.indexOf("_");
		int slashIndex2=operand2.indexOf("/");
		int currentIndex2=0;
		int numerator2=0;
		int whole2=0; 
		int denominator2=1;

		if(slashIndex2!=-1){
			if(underscoreIndex2!=-1) {
				whole2=Integer.parseInt(operand2.substring(0,underscoreIndex2));
				currentIndex2=underscoreIndex2+1;
			}
			numerator2=Integer.parseInt(operand2.substring(currentIndex2,slashIndex2));
			denominator2=Integer.parseInt(operand2.substring(slashIndex2+1));
		}else {
			whole2=Integer.parseInt(operand2);
		}
		//Converts fractions to correct sign
		if(whole1<0) {
			numerator1*=-1;
		}
		if(whole2<0) {
			numerator2*=-1;
		}
		//Converts mixed numbers to improper fraction
		numerator1+=whole1*denominator1;
		numerator2+=whole2*denominator2;

		int numeratorFinal=0;
		int denominatorFinal=0;
		//Finds the result numerator and denominator depending on the sign of the operator
		if(operator.equals("*")) {
			numeratorFinal=numerator1*numerator2;
			denominatorFinal=denominator1*denominator2;
		}else if(operator.equals("/")) {
			numeratorFinal=numerator1*denominator2;
			denominatorFinal=numerator2*denominator1;
		}else if(operator.equals("+")) {
			numeratorFinal=numerator1*denominator2+numerator2*denominator1;
			denominatorFinal=denominator1*denominator2;
		}else if(operator.equals("-")){
			numeratorFinal=numerator1*denominator2-numerator2*denominator1;
			denominatorFinal=denominator1*denominator2;
		}
		//Ensures that the denominator is not negative
		if(denominatorFinal<0) {
			numeratorFinal*=-1;
			denominatorFinal*=-1;
		}
		//Reduces fraction by finding the greatest common divisor
		int gcd=greatestCommonDivisor(numeratorFinal,denominatorFinal);
		numeratorFinal/=gcd;
		denominatorFinal/=gcd;
		//Converts the improper fraction to a mixed number if necessary 
		int wholeFinal=0;
		if(numeratorFinal>0) {
			for(;numeratorFinal>denominatorFinal;numeratorFinal-=denominatorFinal) {
				wholeFinal++;
			}
			//Converts improper negative fraction into a mixed number
		}else{ 	
			for(;Math.abs(numeratorFinal)>denominatorFinal;numeratorFinal+=denominatorFinal) {
				wholeFinal--;
			}
		}
		//Checks if fraction is a whole number 
		if(denominatorFinal==1) {
			wholeFinal+=numeratorFinal;
			numeratorFinal=0;
		}
		//Returns final answer
		if(numeratorFinal==0) {
			return wholeFinal+"";
		}
		else if(wholeFinal!=0) {
			return wholeFinal+"_"+Math.abs(numeratorFinal)+ "/" + denominatorFinal;
		}else {
			return numeratorFinal + "/" + denominatorFinal;
		}
	}


	// TODO: Implement this function to produce the solution to the input
	// Checkpoint 1: Return the second operand.  Example "4/5 * 1_2/4" returns "1_2/4".
	// Checkpoint 2: Return the second operand as a string representing each part.
	//               Example "4/5 * 1_2/4" returns "whole:1 numerator:2 denominator:4".
	// Checkpoint 3: Evaluate the formula and return the result as a fraction.
	//               Example "4/5 * 1_2/4" returns "6/5".
	//               Note: Answer does not need to be reduced, but it must be correct.
	// Final project: All answers must be reduced.
	//               Example "4/5 * 1_2/4" returns "1_1/5".



	// TODO: Fill in the space below with helper methods

	/**
	 * greatestCommonDivisor - Find the largest integer that evenly divides two integers.
	 *      Use this helper method in the Final Checkpoint to reduce fractions.
	 *      Note: There is a different (recursive) implementation in BJP Chapter 12.
	 * @param a - First integer.
	 * @param b - Second integer.
	 * @return The GCD.
	 */
	public static int greatestCommonDivisor(int a, int b)
	{
		a = Math.abs(a);
		b = Math.abs(b);
		int max = Math.max(a, b);
		int min = Math.min(a, b);
		while (min != 0) {
			int tmp = min;
			min = max % min;
			max = tmp;
		}
		return max;
	}

	/**
	 * leastCommonMultiple - Find the smallest integer that can be evenly divided by two integers.
	 *      Use this helper method in Checkpoint 3 to evaluate expressions.
	 * @param a - First integer.
	 * @param b - Second integer.
	 * @return The LCM.
	 */
	public static int leastCommonMultiple(int a, int b)
	{
		int gcd = greatestCommonDivisor(a, b);
		return (a*b)/gcd;
	}
}
