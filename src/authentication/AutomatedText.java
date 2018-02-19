package authentication;

import java.util.Random;

public class AutomatedText {
	
	private String word;
	
	String[] arrayString = {"a","b","c","d","e","f","g","h","i","j","k",
			"m","n","o","p","q","r","s","t","u","v","w","x","y","z"," ",
			"A","B","C","D","E","F","G","H","J","K","L",
			"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"," "};

	Random rn ;

	//Method to get a arbitrary word or sequene of word for display
	public String getWord(int length){
		word = "";
		for (int x = 0; x<=length; x++){
			word += wordChar();
		}
		return word;
	}
	
	//Method to get a arbitrary character for create a arbitrary word
	private String wordChar(){
		rn = new Random();
		int x = rn.nextInt(52);
		String character =arrayString[x] ;
		return character;
	}
}
