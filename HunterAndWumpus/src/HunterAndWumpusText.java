/* Name: HunterAndWumpusText
 * Authors: Jason Tom, Trevor Fasulo
 * Class: CSc 335
 * Teacher: Rick Mercer
 * Due Date: 9/10/14 11:59PM
 * 
 * Description: This class is designed to create a dungeon (2D Array of Rooms), where the
 * Room objects are a private class within the HunterAndWumpusText class.  The Room objects
 * which populate the dungeon hold the state of each room, (i.e. if the room has blood, slime,
 * goop, the wumpus, a pit, or the hunter).  HunterAndWumpusText randomizes the locations of the
 * wumpus, hunter, and pits using the Random class and then fills the blood, slime, and goop based
 * on the locations of the pits and wumpus respectively.  This class also has a move() method
 * which gets user input and moves the hunter through the dungeon based on four move options:
 * up, down, left, right.
 */

import java.util.Random;
import java.util.Scanner;

public class HunterAndWumpusText {

	private class Room {
		
		private boolean hasBlood;
		private boolean hasSlime;
		private boolean hasGoop;
		private boolean hasWumpus;
		private boolean hasPit;
		private boolean hasHunter;
		private boolean isVisible;
		//private final int row;
		//private final int col;
		
		/* Room constructor: initializes the room's state to empty */
		public Room(int row, int col){
			
			//this.row = row;
			//this.col = col;
			hasBlood = false;
			hasSlime = false;
			hasGoop = false;
			hasWumpus = false;
			hasPit = false;
			hasHunter = false;
			isVisible = false;
		}
	}
	
	/* JASON: I changed randomRow/Col for setHunter() to hunterRow/Col and 
	 * made hunterRow/Col a global variable so that I could change the hunter's
	 * position with move(), I also made boardSize global so i could access the 
	 * size of the board before it got initialized.  I also updated the randompitnumber
	 * formula so it made it choose a random number between 3-5.  I also changed
	 * a couple other things, so if something looks different, it was probably me.
	 * The program should still run fine.  Run it to see how I changed it, all I really
	 * did was add a loop to keep running move() for testing purposes.  It moves the O
	 * around with wraparound.
	 */
	
	private static Room[][] dungeon;
	Random generator = new Random();
	static String arrowDirection;
	static int boardSize;
	static int hunterRow;
	static int hunterCol;
	static int arrowRow;
	static int arrowCol;
	static int wumpusRow;
	static int wumpusCol;
	
	/* HunterAndWumpusText Constructor: This method is used to create the dungeon using
	 * a predetermined dungeon size and known pit/wumpus/hunter/slime/blood/goop locations.
	 */
	public HunterAndWumpusText(String[][] map){
		
		dungeon = new Room[map.length][map[0].length];
		
		for(int i=0; i<map.length;i++){
			for(int j=0; j<map[0].length;j++){
				dungeon[i][j] = new Room(i, j);
			}
		}
		
		setWumpus(dungeon);
		setBlood(dungeon);
		setSlimePits(dungeon);
		setSlime(dungeon);
		setGoop(dungeon);
		setHunter(dungeon);
	}
	
	/* HunterAndWumpusText Constructor: This method is used to create a dungeon using
	 * user input for dungeon size and randomized pit/wumpus/hunter/slime/blood/goop locations.
	 */
	public HunterAndWumpusText(){
		
		Scanner keyBoard = new Scanner(System.in);
	
		System.out.println("Enter dungeon size 10 or greater:");
		boardSize = keyBoard.nextInt();
		while(boardSize < 10){
			System.out.println("Invalid size. Please enter a number 10 or greater:");
			boardSize = keyBoard.nextInt();
		}
		
		dungeon = new Room[boardSize][boardSize];
		
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				dungeon[i][j] = new Room(i, j);
			}
		}
		
		setWumpus(dungeon);
		setBlood(dungeon);
		setSlimePits(dungeon);
		setSlime(dungeon);
		setGoop(dungeon);
		setHunter(dungeon);
	}
	
	public String toString(){
		
		String mapString = "";
		String message = "";
		
		for(int i = 0; i < dungeon.length; i++){
			for(int j = 0; j < dungeon[0].length; j++){
				
				if(dungeon[i][j].isVisible){
					if(dungeon[i][j].hasHunter){
						mapString = mapString + "[O] ";
						
						if (dungeon[i][j].hasGoop && !gameOver())
						{
							message="Looks like there's goop on the floor...";
						}
						else if (dungeon[i][j].hasBlood && !gameOver())
						{
							message="Looks like there's blood on the floor...";
						}
						else if (dungeon[i][j].hasSlime && !gameOver())
						{
							message="Looks like there's slime on the floor...";
						}
					}
					else if(dungeon[i][j].hasWumpus){
						mapString = mapString + "[W] ";
					}
					else if(dungeon[i][j].hasPit){
						mapString = mapString + "[P] ";
					}
					else if(dungeon[i][j].hasGoop){
						mapString = mapString + "[G] ";
					}
					else if(dungeon[i][j].hasBlood){
						mapString = mapString + "[B] ";
					}
					else if(dungeon[i][j].hasSlime){
						mapString = mapString + "[S] ";
					}
					else{
						mapString = mapString + "[ ] ";
					}
				}
				else{
					mapString = mapString + "[X] ";
				}
			}
			
			mapString = mapString + "\n";
		}
		
		if (!message.equals(""))
		{
			mapString = mapString + "\n" + message + "\n";
		}
		
		return mapString;
	}
	
	public boolean hasBlood(int row, int col){
		return dungeon[row][col].hasBlood;
	}
	
	public boolean hasSlime(int row, int col){
		return dungeon[row][col].hasSlime;
	}
	
	public boolean hasGoop(int row, int col){
		return dungeon[row][col].hasGoop;
	}
	
	public boolean hasWumpus(int row, int col){
		return dungeon[row][col].hasWumpus;
	}
	
	public boolean hasPit(int row, int col){
		return dungeon[row][col].hasPit;
	}
	
	public boolean hasHunter(int row, int col){
		return dungeon[row][col].hasHunter;
	}
	
	public boolean isVisible(int row, int col){
		return dungeon[row][col].isVisible;
	}
	
	public void setHunter(Room[][] map){
		
		while(hunterCount()<1){
			hunterRow = generator.nextInt(dungeon.length);
			hunterCol = generator.nextInt(dungeon[0].length);
			if(dungeon[hunterRow][hunterCol].hasWumpus 
					|| dungeon[hunterRow][hunterCol].hasPit 
					|| dungeon[hunterRow][hunterCol].hasSlime 
					|| dungeon[hunterRow][hunterCol].hasGoop 
					|| dungeon[hunterRow][hunterCol].hasBlood){
				//do nothing try again to be on a safe spot
			}
			else{
				dungeon[hunterRow][hunterCol].isVisible = true;
				dungeon[hunterRow][hunterCol].hasHunter = true;
			}
		}
	}
	
	private int hunterCount() {
		int hunterCount = 0;
		for(int i=0; i<dungeon.length;i++){
			for(int j=0;j<dungeon[0].length;j++){
				if(dungeon[i][j].hasHunter){
					hunterCount++;
				}
			}
		}
		return hunterCount;
	}

	public void setWumpus(Room[][] map){
		
		wumpusRow = generator.nextInt(dungeon.length);
		wumpusCol = generator.nextInt(dungeon[0].length);
		dungeon[wumpusRow][wumpusCol].isVisible = true;
		dungeon[wumpusRow][wumpusCol].hasWumpus = true;
	}
	
	public void setBlood(Room[][] map){
		
		dungeon[(wumpusRow+2)%boardSize][wumpusCol].isVisible = true;
		dungeon[(wumpusRow+2)%boardSize][wumpusCol].hasBlood = true;
		dungeon[(wumpusRow+1)%boardSize][wumpusCol].isVisible = true;
		dungeon[(wumpusRow+1)%boardSize][wumpusCol].hasBlood = true;
		dungeon[wumpusRow][(wumpusCol+2)%boardSize].isVisible = true;
		dungeon[wumpusRow][(wumpusCol+2)%boardSize].hasBlood = true;
		dungeon[wumpusRow][(wumpusCol+1)%boardSize].isVisible = true;
		dungeon[wumpusRow][(wumpusCol+1)%boardSize].hasBlood = true;
		dungeon[(wumpusRow-1+boardSize)%boardSize][wumpusCol].isVisible = true;
		dungeon[(wumpusRow-1+boardSize)%boardSize][wumpusCol].hasBlood = true;
		dungeon[(wumpusRow-2+boardSize)%boardSize][wumpusCol].isVisible = true;
		dungeon[(wumpusRow-2+boardSize)%boardSize][wumpusCol].hasBlood = true;
		dungeon[wumpusRow][(wumpusCol-1+boardSize)%boardSize].isVisible = true;
		dungeon[wumpusRow][(wumpusCol-1+boardSize)%boardSize].hasBlood = true;
		dungeon[wumpusRow][(wumpusCol-2+boardSize)%boardSize].isVisible = true;
		dungeon[wumpusRow][(wumpusCol-2+boardSize)%boardSize].hasBlood = true;
		dungeon[(wumpusRow-1+boardSize)%boardSize][(wumpusCol-1+boardSize)%boardSize].isVisible = true;
		dungeon[(wumpusRow-1+boardSize)%boardSize][(wumpusCol-1+boardSize)%boardSize].hasBlood = true;
		dungeon[(wumpusRow+1)%boardSize][(wumpusCol+1)%boardSize].isVisible = true;
		dungeon[(wumpusRow+1)%boardSize][(wumpusCol+1)%boardSize].hasBlood = true;
		dungeon[(wumpusRow-1+boardSize)%boardSize][(wumpusCol+1)%boardSize].isVisible = true;
		dungeon[(wumpusRow-1+boardSize)%boardSize][(wumpusCol+1)%boardSize].hasBlood = true;
		dungeon[(wumpusRow+1)%boardSize][(wumpusCol-1+boardSize)%boardSize].isVisible = true;
		dungeon[(wumpusRow+1)%boardSize][(wumpusCol-1+boardSize)%boardSize].hasBlood = true;
	}
	
	public void setSlimePits(Room[][] map){
		
		Random generator = new Random();
		int randomPitNumber = generator.nextInt((5 - 3) + 1) + 3; //generate a random number between 3 and 5 pits
		int totalPits = 0;
		
		while(totalPits<randomPitNumber){
			int randomRow = generator.nextInt(dungeon.length);
			int randomCol = generator.nextInt(dungeon[0].length);
			if(dungeon[randomRow][randomCol].hasWumpus || dungeon[randomRow][randomCol].hasBlood){
				//do nothing, generate a new random location
			}
			else{
				dungeon[randomRow][randomCol].isVisible = true;
				dungeon[randomRow][randomCol].hasPit = true;
			}
			totalPits = getTotalPits();
		}
	}
	
	public void setSlime(Room[][] map){
		
		for(int i=0; i<dungeon.length;i++){
			for(int j=0;j<dungeon[0].length;j++){
				if(dungeon[i][j].hasPit){
					dungeon[(i-1+boardSize)%boardSize][j].isVisible = true;
					dungeon[(i-1+boardSize)%boardSize][j].hasSlime = true;
					dungeon[i][(j-1+boardSize)%boardSize].isVisible = true;
					dungeon[i][(j-1+boardSize)%boardSize].hasSlime = true;
					dungeon[(i+1)%boardSize][j].isVisible = true;
					dungeon[(i+1)%boardSize][j].hasSlime = true;
					dungeon[i][(j+1)%boardSize].isVisible = true;
					dungeon[i][(j+1)%boardSize].hasSlime = true;
				}
			}
		}
	}
	
	public void setGoop(Room[][] map){
		
		for(int i=0; i<dungeon.length;i++){
			for(int j=0;j<dungeon[0].length;j++){
				if(dungeon[i][j].hasBlood && dungeon[i][j].hasSlime){
					dungeon[i][j].hasGoop = true;
				}
			}
		}
	}

	private int getTotalPits() {
		
		int totalPits = 0;
		for(int i=0; i<dungeon.length;i++){
			for(int j=0;j<dungeon[0].length;j++){
				if(dungeon[i][j].hasPit){
					totalPits++;
				}
			}
		}
		return totalPits;
	}
	
	public static void move(){
		
		String direction;
		direction=getDirection();
		
		if (direction.equals("up"))
		{
			dungeon[hunterRow][hunterCol].hasHunter = false;
			hunterRow=(hunterRow-1+boardSize)%boardSize;
			
			//System.out.println(hunterRow);
			dungeon[hunterRow][hunterCol].isVisible = true;
			dungeon[hunterRow][hunterCol].hasHunter = true;
		}
		
		else if (direction.equals("down"))
		{
			dungeon[hunterRow][hunterCol].hasHunter = false;
			hunterRow=(hunterRow+1)%boardSize;
			
			//System.out.println(hunterRow);
			dungeon[hunterRow][hunterCol].isVisible = true;
			dungeon[hunterRow][hunterCol].hasHunter = true;
		}
		
		else if (direction.equals("right"))
		{
			dungeon[hunterRow][hunterCol].hasHunter = false;
			hunterCol=(hunterCol+1)%boardSize;
			
			//System.out.println(hunterCol);
			dungeon[hunterRow][hunterCol].isVisible = true;
			dungeon[hunterRow][hunterCol].hasHunter = true;
		}
		
		else if(direction.equals("left"))
		{
			dungeon[hunterRow][hunterCol].hasHunter = false;
			hunterCol=(hunterCol-1+boardSize)%boardSize;
			
			//System.out.println(hunterCol);
			dungeon[hunterRow][hunterCol].isVisible = true;
			dungeon[hunterRow][hunterCol].hasHunter = true;
		}
		
		else
		{
			shootArrow();
		}
	}
	
	private static void shootArrow() {
		
		if (arrowDirection.equals("up"))
		{
			arrowRow = (hunterRow-1+boardSize)%boardSize;
			arrowCol = hunterCol;
			while(arrowRow != hunterRow){
				if(dungeon[arrowRow][arrowCol].hasWumpus){
					break;
				}
				else{
					arrowRow = (arrowRow-1+boardSize)%boardSize;
				}
			}
		}
		
		else if (arrowDirection.equals("down"))
		{
			arrowRow = (hunterRow+1)%boardSize;
			arrowCol = hunterCol;
			while(arrowRow != hunterRow){
				if(dungeon[arrowRow][arrowCol].hasWumpus){
					break;
				}
				else{
					arrowRow = (arrowRow+1)%boardSize;
				}
			}
		}
		
		else if (arrowDirection.equals("right"))
		{
			arrowCol = (hunterCol+1)%boardSize;
			arrowRow = hunterRow;
			while(arrowCol != hunterCol){
				if(dungeon[arrowRow][arrowCol].hasWumpus){
					break;
				}
				else{
					arrowCol = (arrowCol+1)%boardSize;
				}
			}
		}
		
		else
		{
			arrowCol = (hunterCol-1+boardSize)%boardSize;
			arrowRow = hunterRow;
			while(arrowCol != hunterCol){
				if(dungeon[arrowRow][arrowCol].hasWumpus){
					break;
				}
				else{
					arrowCol = (arrowCol-1+boardSize)%boardSize;
				}
			}
		}
	}

	/* getDirection() prompts the player for a direction and then returns the direction
	 * that the user wants to go.  It checks that the direction is: up,down,left, or right.*/
	public static String getDirection(){
		
		Scanner keyboard = new Scanner(System.in);
		String direction="";
		
		System.out.print("Which direction would you like to go?\nOr would you like to shoot your arrow? (up, down, left, right, shoot arrow): ");
		direction=keyboard.nextLine();
		direction=direction.toLowerCase();
	
		
		while (!direction.equals("up") && !direction.equals("down") && !direction.equals("left") && !direction.equals("right") && !direction.equals("shoot arrow"))
		{
		
			System.out.println("That is not a valid command, please enter a valid command.");
			System.out.print("Which direction would you like to go? Would you like to shoot your arrow? (up, down, left, right, shoot arrow): ");
			direction=keyboard.nextLine();
			direction=direction.toLowerCase();
		}
		
		if(direction.equals("shoot arrow")){
			System.out.println("Which direction would you like to shoot the arrow? (up, down, left, right):");
			arrowDirection = keyboard.nextLine();
			arrowDirection = arrowDirection.toLowerCase();
			
			while (!arrowDirection.equals("up") && !arrowDirection.equals("down") && !arrowDirection.equals("left") && !arrowDirection.equals("right"))
			{
			
				System.out.println("That is not a valid direction, please enter a valid direction.");
				System.out.print("Which direction would you like to shoot the arrow? (up, down, left, right):");
				arrowDirection=keyboard.nextLine();
				arrowDirection=arrowDirection.toLowerCase();
			}
		}

		return direction;
	}
	
	public static boolean gameOver(){
		
		boolean gameover=false;
		
		if (dungeon[hunterRow][hunterCol].hasWumpus)
		{
			gameover=true;
		}
		
		else if (dungeon[hunterRow][hunterCol].hasPit)
		{
			gameover=true;
		}
		
		else if (arrowRow == wumpusRow && arrowCol == wumpusCol)
		{
			gameover=true;
		}
		
		else if (arrowRow == hunterRow && arrowCol == hunterCol)
		{
			gameover=true;
		}
		
		return gameover;
		
	}
	
	public static String gameOverMessage(){
		
		String message = "";
		
		if (dungeon[hunterRow][hunterCol].hasWumpus)
		{
			message="YOU LOSE...\nYou've been eaten by the mighty Wumpus. gg.";
		}
		
		else if (dungeon[hunterRow][hunterCol].hasPit)
		{
			message="YOU LOSE...\nYou fell into a bottomless pit and died. get wrecked.";
		}
		
		else if (arrowRow == wumpusRow && arrowCol == wumpusCol)
		{
			message="YOU WIN!\nYour arrow pierces the Wumpus' heart and slays it! You venture out of the dungeon a hero.";
		}
		
		else if (arrowRow == hunterRow && arrowCol == hunterCol)
		{
			message="YOU LOSE...\nYour arrow hits you in the back. That takes skill...too bad you don't live to tell the tale.";
		}
		
		else
		{
			message="WHYULOSE?";
		}
		
		return message;
	}
	
	public static String playAgain(HunterAndWumpusText testDung1){
		String playGameString = "yes";
		System.out.println("\n" + gameOverMessage() + "\n");

		for(int i=0; i<dungeon.length;i++){
			for(int j=0;j<dungeon[0].length;j++){
				dungeon[i][j].isVisible = true;
			}
		}
		System.out.println(testDung1.toString());
		System.out.println("Would you like to play again?(yes or no)");
		
		Scanner keyboard = new Scanner(System.in);
		playGameString = keyboard.nextLine();
		
		if(playGameString.equals("yes")){
			testDung1 = new HunterAndWumpusText();
			System.out.println(testDung1.toString());
		}
		else{
			System.out.println("Thanks for playing!");
		}
		
		return playGameString;
	}
	
	
	public static void main(String[] args){
		
		HunterAndWumpusText testDung1 = new HunterAndWumpusText();
		System.out.println(testDung1.toString());
		String playGameString = "yes";
			
		while(playGameString.equals("yes")){
			move();
			if (gameOver())
			{
				playGameString=playAgain(testDung1);
			}
			else
			{
				System.out.println(testDung1.toString());
			}
		}
	}

}
