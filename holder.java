	public void encrypt(int gridLength, int gridWidth, boolean hasXX){
		//Block words that have "XX"
			//Prepare String
				//Exit if has XX
			//Prepare Table


		//Do Not Use before calling and testing prepareString()
		if (!isValidKey()){
			System.out.println("Not Valid Key. System Quit.");
			return;
		}

		proccessPlainText();
		preparePlainText();
		

		System.out.println("plaintext: " + plaintext);


		String[] table = createTable(key);
		int halfSize = plaintext.length()/2;
		
		String pair, letter1, letter2;
		char char1, char2;
		
		int bPos, cPos;
		String encrypted = "";
		
		Coordinates b = new Coordinates(-1, -1);
		Coordinates c = new Coordinates(-1, -1);			
				

		for(int i = 0; i < pTextLength - 1; i++){
			pair = plaintext.substring(i , i + 2);
			char1 = pair.charAt(0);
			char2 = pair.charAt(1);


			Coordinates x1y1Pair = toCoordinates(char1, gridWidth, gridLength);
			Coordinates x2y2Pair = toCoordinates(char2, gridWidth, gridLength);

			int x1 = x1y1Pair.getX(); 
			int y1 = x1y1Pair.getY();
			
			int x2 = x2y2Pair.getX(); 
			int y2 = x2y2Pair.getY();

			//Handle edge cases of being in the same row or column
			if(x1 == x2){
				b.setX(x1);
				b.setY(y1 + 1);

				c.setX(x1);
				c.setY((y2 + 1) % gridLength);


			}
			else if (y1 == y2) {
				b.setX(x1 + 1);
				b.setY(y1);

				c.setX((x1 + 1) % gridWidth);
				c.setY(y1);
				
			}
			else if (pair.charAt(0) == pair.charAt(1)){
				letter1 = Character.toString(char1);
				letter2 = Character.toString(char2);
				encrypted += (letter1 + letter2);
				System.out.println(encrypted);
				continue;
			}
			else {	
				b = new Coordinates(x2, y1);
				c = new Coordinates(x1, y2);			
				
				
			}

			bPos = calcLetterPos(b);
			cPos = calcLetterPos(c);

			System.out.println("B X "  + b.getX());
			System.out.println("B Y "  + b.getY());
			System.out.println("bPos: " + bPos);


			System.out.println("\nC X "  + c.getX());
			System.out.println("C Y "  + c.getY());
			System.out.println("bPos: " + bPos + "\n\n");

			letter1 = table[bPos];
			letter2 = table[cPos];


			encrypted += (letter1 + letter2);
		}

		System.out.println(encrypted);

	}