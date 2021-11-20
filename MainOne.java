	
public class MainOne{

	public static void main(String[] args){
		String plaintext = "AB";
		String key = "ABCDEE";
		

		PlayFair algo = new PlayFair(plaintext, key);
		algo.encrypt();



		//String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
		//for(int i = 0; i < alphabet.length(); i++){
		//	toCoordinates(alphabet.charAt(i));
		//}



		//algo.getEncryptedText();
		//System.out.println("Plaintext altered: " + preparePlainText(plaintext));
		//System.out.println("Process (adds X): " + proccessPlainText(plaintext));
		//System.out.println("Valid key(True): " + isValidKey(key));
		//System.out.println("First Duplicate(6): " + indexOfFirstDuplicateLetter(key, key.length(), 0));
		//printArray(createTable(key));

		//System.out.println(isEven(num));
		//PlayFair(plaintext, key, false);

		//System.out.println("Test Char Position: " + getPos('B'));
	}

	static void toCoordinates(char letter){
		int pos = letter - 64; 

		pos = (pos >= 10)? (pos - 1) : pos; //No J in PlayFair so Shift letters over one spot to the left
		
		int x = pos - floor(pos, 5);
		x = (x == 0)? 5 : x;

		int y = ceiling(pos, 5);
		y = (y % 5 == 0 && y > 5)? y/5 : y;

		System.out.println("Letter :" + letter);
		System.out.println("X: " + x);
		System.out.println("Y: " + y);	
	}

	static int ceiling(int num, int stepNum){
		if (num <= 5)
			return 1;
		int holder = ((int)(num/stepNum));
		return (num % stepNum == 0 && num >= stepNum)? holder : ((holder) + 1 ) * 5;
		 
	}

	static int floor(int num, int stepNum){
		return ((int)(num/stepNum)) * 5;
	}

}