
import java.util.ArrayList;

import java.util.Collections;

import javax.swing.JOptionPane;

/* Author: Sinaya Nudel 203191663
 * Date: 28/03/18
 */
public class BullPgia {
	private String _numToGuess; //the random number which the user try to guess
	private boolean _isFound; //if the user guess the number - _isFound is true. else, false.
	private ArrayList<String> _results; //list of all the results for the past guesses
	private final int NUM_LEN = 4; // must be 1<=NUM_LEN<=10 (there is only 10 decimal unique digits)
	
	public BullPgia(){
		_numToGuess = randNum();
		_isFound = false;
		_results = new ArrayList<String>();
	}
	
	private boolean isFound() {
		return _isFound;
	}
	
	private String getResults() {
		String s = "";
		if(!_results.isEmpty()){
			for(int i = 0; i < _results.size(); i++) {
	            s +=_results.get(i) + "\n";
	        }
		}
		return s; //string which include the list of all the results for the past guesses
	}
	
	private int getCount() {
			return _results.size(); //the count of the guesses which the user has tried;
	}
	
	private boolean isInt(String s){ //returns true if the string contain only decimal digits, false if not.
		for(int i=0;i<s.length();i++){
			if((int)s.charAt(i)<48 ||(int)s.charAt(i)>57) //48-57 is the range of the chars '0' to '9' at the ASCII table.
				return false;
		}
		return true;
	}
	
	private boolean isUniqueDigits(String s){ //checks if each digit appears only once
		for(int i=0;i<s.length();i++){
			for(int j=0;j<s.length();j++){
				if(i!=j){
					if(s.charAt(i)==s.charAt(j))
						return false;
				}
		
			}
		}
		return true;
	}
	
	private boolean isLength(String s){ //checks if the string length, (=the number of digits) is valid.
		if(s.length()!=NUM_LEN)
			return false;
		return true;
	}
	
	private String randNum(){ //creates a valid random number (as a string) 
		String s = "";
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for(int i=0;i<10;i++) //create a list of number from 0 to 9
			numbers.add(i); 
		do{
			Collections.shuffle(numbers); //so the picking of the numbers will be random
			for(int i=1;i<=NUM_LEN;i++) 
				s+=numbers.get(i);
		}while(s.charAt(0)=='0'); //making sure that the number is not start with a '0'.
		
		return s;
		
	}
	private String tryNum(String other){
		int countHits = 0;
		int countAlmost = 0;
		for(int i=0;i<other.length();i++){
			for(int j=0;j<_numToGuess.length();j++){
				if(other.charAt(i)==_numToGuess.charAt(j)){
					if(i!=j){
						countAlmost++;
						break; // every digit is unique in both strings. Once digit was found- it will not found again. 
					}
					else{ //if i==j
						countHits++;
						break; // every digit is unique in both strings. Once digit was found- it will not found again. 
					}
						
				}
		
			}
		}
		if(countHits==NUM_LEN)
			_isFound=true;
		String s = "for guess " + other + " : "+ countHits + " hits and " + countAlmost + " almost.";
		_results.add(s);
		return s;
	}
	
	private void resetGame(){
		_results = new ArrayList<String>();
		_numToGuess = randNum();
		_isFound = false;
	}
	
	private boolean isValid(String s){
		if(!isInt(s)){
			JOptionPane.showMessageDialog(null,"Type only integers. try again", 
					"error",JOptionPane.ERROR_MESSAGE);

			return false;
		}
		if(!isUniqueDigits(s)){
			JOptionPane.showMessageDialog(null,"Each digit must be unique. try again", 
					"error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(!isLength(s)){
			JOptionPane.showMessageDialog(null,"The number is not " + NUM_LEN + " digits. try again", 
					"error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	protected void play(){
		String num;
		while(!isFound()){ // executes until the user guess the number
			do{
				num = JOptionPane.showInputDialog(null, //executes until the user's guess is valid
						getResults() + "Please Type a " + NUM_LEN + " digits number. Each digit must be unique:");
			}while(!isValid(num));	
			
			String guess = tryNum(num);
			if(isFound()){
				JOptionPane.showMessageDialog(null,guess + "\nCorrect!\nNumber of guessings: " + getCount(), 
						"winning message",JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(null,guess + "\ntry an another guess", 
						"message",JOptionPane.INFORMATION_MESSAGE);
			}
		}
			
		int reply = JOptionPane.showConfirmDialog(null,
				    "Do you want to start a new game?", "message",
				    JOptionPane.YES_NO_OPTION);
			
		if(reply == JOptionPane.YES_OPTION){
			resetGame();
			play();
		} 
	}
}
