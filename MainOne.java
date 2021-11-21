/**
*@author Christopher Perez
*@version 1.0.0
* 11/21/2021
*/
public class MainOne{

	public static void main(String[] args){
		String plaintext = "SECRETMESSAGE";
		String key = "KEYWORD";
		
		PlayFair algo = new PlayFair(plaintext, key);
		algo.encrypt();
		System.out.println("Encrypted Text: " + algo.getEncryptedText());

	}
	

}