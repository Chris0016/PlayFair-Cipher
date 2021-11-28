/**
*@author Christopher Perez
*@version 1.0.1
* 11/21/2021
*/
public class Main{

	public static void main(String[] args){
		String plaintext = "SECRETMESSAGE";
		String key = "KEYWORD";
		
		PlayFair algo = new PlayFair();
		algo.encrypt(plaintext, key);
		System.out.println("Encrypted Text: " + algo.getEncryptedText());

	}
	

}