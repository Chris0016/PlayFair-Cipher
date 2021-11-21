/**
*@author Christopher Perez
*@version 1.0.0
* 11/21/2021
*/
import java.util.regex.*;  
import java.lang.IllegalArgumentException;

public class PlayFair{

	//Note the table for encryption is a 5x5 table. However it the possibility remains that it can be changed.
	// LENGTH * WIDTH = 25
	final int LENGTH = 5;
	final int WIDTH  = 5;

	private String plaintext;
	private String encryptedText;
	private String key;
	//private String alphabet; Maybe useful in decryption?
	private String[][] table;
	
	private int kLength;
	private int pTextLength;
	
	public PlayFair(String plaintext, String key) throws IllegalArgumentException{
		
		this.setKey(key);
		this.setPlaintext(plaintext);	

		table = new String[LENGTH][WIDTH];
		createTable();
	}


	public void encrypt(){	

		//encryptedText = ""; Safety Precaution

		String  pairPart1, pairPart2;
		Coordinates a, b, c, d; 
		int x1, x2, y1, y2;
		//String letter1, letter2;

		
		System.out.println(plaintext);

		for(int i = 0; i < pTextLength - 1; i+=2){
			pairPart1 = plaintext.substring(i, i + 1);
			pairPart2 = plaintext.substring(i + 1, i + 2);

			a = getCoordinates(pairPart1);
			d = getCoordinates(pairPart2);

			
			x1 = a.getX();
			y1 = a.getY();

			x2 = d.getX();
			y2 = d.getY();

			//Calculate coordinates of b and c based on coordinates of a and d

			//Edge Case: If same row
			if(y1 == y2){

				b = new Coordinates((x1 + 1) % WIDTH, y1);
				c = new Coordinates((x2 + 1) % WIDTH, y2);

			}
			//Edge Case: If same column
			else if(x1 == x2){
				b = new Coordinates(x1, (y1 + 1) % LENGTH);
				c = new Coordinates(x1, (y2 + 1) % LENGTH);
			}
			else{

				b = new Coordinates(x2, y1);
				c = new Coordinates(x1, y2);
			}
			

			/*
			For Debugging purposes
			letter1 = table[b.getY()][b.getX()];
			letter2 = table[c.getY()][c.getX()];

			System.out.println("Letter at B : " + letter1 );
			System.out.println("Coordinates of B : " + "(" + b.getX() + ", " + b.getY() + ")");

			System.out.println("Letter at C: " + letter2);	
			System.out.println("Coordinates of C : " + "(" + c.getX() + ", " + c.getY() + ")" + "\n\n");
			//append letters, based on their pos value in the table, to encrypt String			
			
			
			encryptedText += letter1 + letter2;
			*/

			encryptedText += table[b.getY()][b.getX()] +  table[c.getY()][c.getX()];

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
	private void proccessPlainText(){

		int idxDup = indexOfFirstDuplicateLetter(plaintext, pTextLength,  0);
		
		while( idxDup != -1){

			//Add an X to plaintest after that idx
			plaintext =  new StringBuffer(plaintext).insert(idxDup + 1, "X").toString();
			pTextLength += 1;

			//Look for next duplic
			idxDup = indexOfFirstDuplicateLetter(plaintext, pTextLength, idxDup);

		}

		this.plaintext = plaintext.toUpperCase().replace(" ", "");
		pTextLength = plaintext.length();
	}

	
	public Boolean isValidKey(String key, int kLength){
		return (indexOfFirstDuplicateLetter(key, kLength,  0) == -1);
	}

	public Boolean isValidPlaintext(String plaintext){
		return !(plaintext.contains("J"));
	}

	private int indexOfFirstDuplicateLetter(String plaintext, int size, int start){

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


	private Coordinates getCoordinates(String letter){
		
		for(int y = 0; y <  LENGTH; y++)
			for(int x = 0; x < WIDTH; x++)
				if(table[y][x].equals(letter))
					return new Coordinates(x, y);

		return new Coordinates(-1, -1);
	}

	
	//Rename this method to something more appropriate
	private void createTable(){
		String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
		alphabet = alphabet.replaceAll("["+ key +"]", "");
		alphabet =  key + alphabet;

		populateTable(alphabet);
	}

	private void populateTable(String text){
		/*
		if (tex.length() != LENGTH * WIDTH) //LENGTH * WIDTH == 25
			throw new IllegalArgumentException("Text Length Does Not Match LENGTH * WIDTH");
		This exveption should never happen because the string passed to it is only seen in createTable()

		*/

		int count = 0;
		for(int i = 0; i < LENGTH; i++)
			for (int j = 0; j < WIDTH; j++){
				this.table[i][j] = text.substring(count, count + 1);
				count++;
			}
	}


//--------- Setters and Getters ---------------------

	public void setKey(String key){
		if (!isValidKey(key, key.length()) )
			throw new IllegalArgumentException("Invalid Key: Non-distinct Letters");
		
		this.key = key.toUpperCase().trim();
		kLength = key.length();
		

	}

	public void setPlaintext(String plaintext){
		if(!isValidPlaintext(plaintext) )
			throw new IllegalArgumentException("Invalid plaintext: Contains letter J");

		this.plaintext = plaintext;
		pTextLength = plaintext.length();

		proccessPlainText();
		makeEvenLength();
		encryptedText = "";
	}

	public String getKey(){
		return this.key;
	}

	public String getPlaintext(){
		return this.plaintext;
	}

	public String getEncryptedText(){
		return  this.encryptedText;
	}

//--------- General Methods ---------------------

	private boolean isEven(int num){
		return (num % 2 == 0 && num != 1);
	
	}

	private <E> void  print2DArray(E[][] arr){
		for(E[] subArr : arr)
			for(E i : subArr )
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