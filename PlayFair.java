import java.util.regex.*;  
import java.lang.IllegalArgumentException;

public class PlayFair{

	//Note the table for encryption is a 5x5 table. However it the possibility remains that it can be changed.
	final int LENGTH = 5;
	final int WIDTH  = 5;

	private String plaintext;
	private String key;
	private String alphabet;
	private String[] table;
	
	private int kLength;
	private int pTextLength;
	
	public PlayFair(String plaintext, String key) throws IllegalArgumentException{
		if (!isValidKey(key, key.length()) )
			throw new IllegalArgumentException("Invalid Key: Non-distinct Letters");
		else if(!isValidPlaintext(plaintext) )
			throw new IllegalArgumentException("Invalid plaintext: Contains letter J");
		

		this.plaintext = plaintext;
		this.key = key;

		pTextLength = plaintext.length();
		kLength = key.length();

		alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		table = (isValidKey(key, kLength))? createTable() : null;
	}

	public void encrypt(){		

		char char1, char2;
		Coordinates a, b, c, d; 
		int x1, x2, y1, y2;
		int bPos, cPos;
		String letter1, letter2;

		proccessPlainText();
		makeEvenLength();
		
		System.out.println(plaintext);

		for(int i = 0; i < pTextLength - 1; i+=2){
			char1 = plaintext.charAt(i);
			char2 = plaintext.charAt(i + 1);

			a = toCoordinates(char1);
			d = toCoordinates(char2);

			
			/*
			System.out.println("\nCoordinates for " + char1 + ": ");
			System.out.println("A: " + a.getX() + ", "+ a.getY());

			System.out.println("\nCoordinates for " + char2 + ": ");
			System.out.println("D: " + d.getX() + ", "+ d.getY());
			*/

			x1 = a.getX();
			y1 = a.getY();

			x2 = d.getX();
			y2 = d.getY();

			//Calculate coordinates of b and c based on coordinates of a and d

			//Edge Case: If same row
			if(y1 == y2){

				b = new Coordinates(x1 + 1, y1);
				c = new Coordinates((x2 + 1) % WIDTH, y2);

			}
			//Edge Case: If same column
			else if(x1 == x2){
				b = new Coordinates(x1, y1 + 1);
				c = new Coordinates(x1, (y2 + 1) % LENGTH);
			}
			else{

				b = new Coordinates(x2, y1);
				c = new Coordinates(x1, y2);
			}
			
			
			
			//Calculate the position of b and c based on their respective coordinates

			bPos = coordToPos(b);
			cPos = coordToPos(c);

			letter1 = table[bPos - 1];
			letter2 = table[cPos - 1];

			System.out.println("Poistion Calculated for B : " + bPos);
			System.out.println("Letter at " + bPos + ": " + letter1 );
			System.out.println("Coordinates of B : " + "(" + b.getX() + ", " + b.getY() + ")");

			System.out.println("\nPoistion Calculated for C : " + cPos);
			System.out.println("Letter at " + cPos + ": " + letter2);
			System.out.println("Coordinates of C : " + "(" + c.getX() + ", " + c.getY() + ")" + "\n\n");
			//append letters, based on their pos value in the table, to encrypt String
			

			

		}
	

	}
	

	
	//Odd number of letters add a Z(or A) to the
	private void makeEvenLength(){
		
		if (!isEven(pTextLength)){
			if (plaintext.substring(pTextLength - 2, pTextLength-1).equals("Z"))
				plaintext += "Q";
			else 
				plaintext += "Z";

			pTextLength += 1;
		}

	}
	
	//Filter plaintext to make sure that consecutive letters don't repeat
	// If so replace with a random letter
	private String proccessPlainText(){
		int idxDup = indexOfFirstDuplicateLetter(plaintext, pTextLength,  0);
		
		while( idxDup != -1){

			//Add an X to plaintest after that idx
			plaintext =  new StringBuffer(plaintext).insert(idxDup + 1, "X").toString();
			pTextLength += 1;

			//Look for next duplic
			idxDup = indexOfFirstDuplicateLetter(plaintext, pTextLength, idxDup);

			
		}
		return plaintext;
	}

	
	public Boolean isValidKey(String key, int kLength){

		return (indexOfFirstDuplicateLetter(key, kLength,  0) == -1);
	}

	public Boolean isValidPlaintext(String plaintext){
		return !(plaintext.contains("J"));
	}



	private int indexOfFirstDuplicateLetter(String plaintext, int size, int start){
		/*
		if (start >= size)
			//Throw an error
		*/

		if ( !isEven(size)  )
			plaintext += "0";

		String curr, next;
		for (int i = start; i < size - 1; i++){
			curr = plaintext.substring(i , i + 1);
			next = plaintext.substring(i + 1, i + 2);
			 
			if (curr.equals(next))
				return i;
		}

		return -1;
	}



	private int coordToPos(Coordinates coordinate){
		return (WIDTH * (coordinate.getY() - 1)) + coordinate.getX();
	}
	
	private Coordinates toCoordinates(char letter){
		int pos = letter - 64; 

		pos = (pos >= 10)? (pos - 1) : pos; //No J in PlayFair so Shift letters over one spot to the left
		
		int x = pos - floor(pos, WIDTH);
		x = (x == 0)? WIDTH : x;

		int y = ceiling(pos, WIDTH);
		y = (y % 5 == 0 && y > WIDTH)? y/5 : y;

		return new Coordinates(x, y);	
	}


	//A custom ceiling and floor function based on a given "floor/ceiling" the stepNum
	// e.g. ceiling(3, 5) -> 5  ceiling(32, 5)  -> 35 
	// e.g. floo(3, 5) -> 0 	floor(32, 5) 	-> 30
	private int ceiling(int num, int stepNum){
		if (num <= 5)
			return 1;
		int holder = ((int)(num/stepNum));
		return (num % stepNum == 0 && num >= stepNum)? holder : ((holder) + 1 ) * 5;
		 
	}

	private int floor(int num, int stepNum){
		return ((int)(num/stepNum)) * 5;
	}

	
	private String[] createTable(){
		String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
		alphabet = alphabet.replaceAll("["+ key +"]", "");
		alphabet =  key + alphabet;

		return toArray(alphabet);
	}





//--------- Setters and Getters ---------------------

	public boolean setKey(String key){
		if(!isValidKey(key, key.length()))
			return false;
		this.key = key;
		kLength = key.length();
		return true;
	}

	public boolean setPlaintext(String plaintext){
		if(!isValidPlaintext(plaintext))
			return false;

		this.plaintext = plaintext;
		pTextLength = plaintext.length();
		
		return true;
	}

	public String getKey(){
		return this.key;
	}

	public String getPlaintext(){
		return this.plaintext;
	}

//--------- General Methods ---------------------

	private String[] toArray(String text){
		int size = text.length();
		String[] arr = new String[size];

		for(int i = 0; i < size; i++)
			arr[i] = text.substring(i , i+1);
		
		return arr;
	}

	private boolean isEven(int num){
		return (num % 2 == 0 && num != 1);
	
	}

	private <E> void  printArray(E[] arr){
		for(E i : arr)
			System.out.print(i + " ");
		System.out.println();
	}




	class Coordinates{
		private int x, y;

		public Coordinates(int x, int y){
			this.x = x;
			this.y = y;
		}

		int getX(){
			return this.x;
		}
		int getY(){
			return this.y;
		}

		void setX(int x){
			this.x = x;
		}
		void setY(int y){
			this.y = y;
		}

		
	}

}