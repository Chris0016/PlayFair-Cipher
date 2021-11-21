	
public class MainOne{

	public static void main(String[] args){
		String plaintext = "HELLO nun";
		String key = "PLAYFIR";
		/*
		//Create Table
		String[][] table = new String[5][5];
		String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
		alphabet = alphabet.replaceAll("["+ key +"]", "");
		alphabet =  key + alphabet;

		int count = 0;
		for(int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++){
				table[i][j] = Character.toString(alphabet.charAt(count));
				count++;
			}
		//Create Table


		printTable(table);
		char letter = 'A';
		printCoordinates(letter, table);//Remember coordinates are 0-based index
		System.out.println("Finished");
		*/


		PlayFair algo = new PlayFair(plaintext, key);
		algo.encrypt();
		System.out.println("Encrypted Text: " + algo.getEncryptedText());



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
	static void printCoordinates(char letter, String[][] table){
		for(int y = 0; y <  5; y++)
			for(int x = 0; x < 5; x++)
				if(table[y][x].equals(Character.toString(letter)))
					System.out.println("Coordinates: " + x + ", " + y);
	}

	static void printTable(String[][] table){
		for(int y = 0; y <  5; y++)
			for(int x = 0; x < 5; x++)
					System.out.print(table[y][x] + " ");
		System.out.println();
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