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
 * up, down, left, right. And you can also use the shootArrow() method to shoot the Wumpus, or yourself.
 */

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/* HunterAndWumpusText class contains the methods necessary to play the game
 * and create the dungeon, made up of room objects
 */
public class HunterAndWumpusText {

	private class Room {
		
		private boolean hasBlood;
		private boolean hasSlime;
		private boolean hasGoop;
		private boolean hasWumpus;
		private boolean hasPit;
		private boolean hasHunter;
		private boolean isVisible;
		
		/* Room constructor: initializes the room's state to empty */
		public Room(int row, int col){
			
			hasBlood = false;
			hasSlime = false;
			hasGoop = false;
			hasWumpus = false;
			hasPit = false;
			hasHunter = false;
			isVisible = false;
		}
	}

	//Global variables used throughout the game.
	private Room[][] dungeon;
	Random generator = new Random();
	String arrowDirection;
	int boardSize;
	int hunterRow;
	int hunterCol;
	int arrowRow;
	int arrowCol;
	int wumpusRow;
	int wumpusCol;
	
	/* HunterAndWumpusText Constructor: This method is used to create the dungeon using
	 * a predetermined dungeon size and known pit/wumpus/hunter/slime/blood/goop locations.
	 */
	public HunterAndWumpusText(int [] fixed){
		
		boardSize=10;
		dungeon = new Room[boardSize][boardSize];
		
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				dungeon[i][j] = new Room(i, j);
			}
		}
		
		wumpusRow=fixed[0];
		wumpusCol=fixed[1];
		hunterRow=fixed[10];
		hunterCol=fixed[11];
		
		dungeon[fixed[0]][fixed[1]].hasWumpus=true;
		
		dungeon[fixed[2]][fixed[3]].hasPit=true;
		dungeon[fixed[4]][fixed[5]].hasPit=true;
		dungeon[fixed[6]][fixed[7]].hasPit=true;
		dungeon[fixed[8]][fixed[9]].hasPit=true;
		
		dungeon[fixed[10]][fixed[11]].hasHunter=true;
		dungeon[fixed[10]][fixed[11]].isVisible=true;
		dungeon[7][4].isVisible=true;
		
		setBlood(dungeon);
		setSlime(dungeon);
		setGoop(dungeon);
		dungeon.toString();
	
	}
	
	
	/* HunterAndWumpusText Constructor: This method is used to create a dungeon using
	 * user input for dungeon size and randomized pit/wumpus/hunter/slime/blood/goop locations.
	 */
	public HunterAndWumpusText(){
		
		Scanner keyBoard = new Scanner(System.in);
	
		System.out.println("Enter dungeon size 10 or greater:");
		try {
			boardSize = keyBoard.nextInt();
			while(boardSize < 10){
				System.out.println("Invalid size. Please enter a number 10 or greater:");
				boardSize = keyBoard.nextInt();
			}
		} catch (InputMismatchException e)
		{
			System.out.println("NAN dumbass.");
			System.exit(1);
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
	
	/* toString creates a string that contains the string representation of the dungeon,
	 * showing each room's current state. (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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
	
	/* has methods returns the boolean value of whether or not a specific room
	 * has blood, slime, goop, the wumpus, a pit, the hunter, or is visible.
	 */
	
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
	
	// Random selects a starting position for the hunter, that is safe.
	public void setHunter(Room[][] map){
		
		while(getHunterCount()<1){
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
	
	/* getHunterCount() is a helper method for setHunter() that checks
	 * if the hunter is placed or not.
	 */
	public int getHunterCount() {
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

	// setWumpus picks a random location for the Wumpus and puts it in the randomly selected room.
	public void setWumpus(Room[][] map){
		
		wumpusRow = generator.nextInt(dungeon.length);
		wumpusCol = generator.nextInt(dungeon[0].length);
		dungeon[wumpusRow][wumpusCol].hasWumpus = true;
	}
	
	// setBlood places blood 2 spaces around the Wumpus in Manhattan distance.
	public void setBlood(Room[][] map){
		
		dungeon[(wumpusRow+2)%boardSize][wumpusCol].hasBlood = true;
		dungeon[(wumpusRow+1)%boardSize][wumpusCol].hasBlood = true;
		dungeon[wumpusRow][(wumpusCol+2)%boardSize].hasBlood = true;
		dungeon[wumpusRow][(wumpusCol+1)%boardSize].hasBlood = true;
		dungeon[(wumpusRow-1+boardSize)%boardSize][wumpusCol].hasBlood = true;
		dungeon[(wumpusRow-2+boardSize)%boardSize][wumpusCol].hasBlood = true;
		dungeon[wumpusRow][(wumpusCol-1+boardSize)%boardSize].hasBlood = true;
		dungeon[wumpusRow][(wumpusCol-2+boardSize)%boardSize].hasBlood = true;
		dungeon[(wumpusRow-1+boardSize)%boardSize][(wumpusCol-1+boardSize)%boardSize].hasBlood = true;
		dungeon[(wumpusRow+1)%boardSize][(wumpusCol+1)%boardSize].hasBlood = true;
		dungeon[(wumpusRow-1+boardSize)%boardSize][(wumpusCol+1)%boardSize].hasBlood = true;
		dungeon[(wumpusRow+1)%boardSize][(wumpusCol-1+boardSize)%boardSize].hasBlood = true;
	}
	
	/* setSlimePits generates a random number of pits, and then places the pits 
	 * in random locations.  There will always be 3-5 pits when using the random
	 * number generator.
	 */
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
				dungeon[randomRow][randomCol].hasPit = true;
			}
			totalPits = getTotalPits();
		}
	}
	
	/* setSlime checks the map for pit locations, and then sets slime
	 * directly in each room around the pit. (i.e. above, below, and to
	 * both sides).
	 */
	public void setSlime(Room[][] map){
		
		for(int i=0; i<dungeon.length;i++){
			for(int j=0;j<dungeon[0].length;j++){
				if(dungeon[i][j].hasPit){
					dungeon[(i-1+boardSize)%boardSize][j].hasSlime = true;
					dungeon[i][(j-1+boardSize)%boardSize].hasSlime = true;
					dungeon[(i+1)%boardSize][j].hasSlime = true;
					dungeon[i][(j+1)%boardSize].hasSlime = true;
				}
			}
		}
	}
	
	/* setGoop checks the map for any space that has slime and blood, and then
	 * sets the variable hasGoop for the room to be true.
	 */
	public void setGoop(Room[][] map){
		
		for(int i=0; i<dungeon.length;i++){
			for(int j=0;j<dungeon[0].length;j++){
				if(dungeon[i][j].hasBlood && dungeon[i][j].hasSlime){
					dungeon[i][j].hasGoop = true;
				}
			}
		}
	}

	/* getTotalPits is a helper method for setSlimePits to find how many
	 * random number generated pits there are.
	 */
	public int getTotalPits() {
		
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
	
	/* The move method moves the hunter based on input from the getDirection() method
	 * from user input.  The four directions are up, down, left, right.  It updates
	 * the position of the hunter as well.
	 */
	public void move(String direction){
		
		if (direction.toLowerCase().equals("up"))
		{
			dungeon[hunterRow][hunterCol].hasHunter = false;
			hunterRow=(hunterRow-1+boardSize)%boardSize;

			dungeon[hunterRow][hunterCol].isVisible = true;
			dungeon[hunterRow][hunterCol].hasHunter = true;
		}
		
		else if (direction.toLowerCase().equals("down"))
		{
			dungeon[hunterRow][hunterCol].hasHunter = false;
			hunterRow=(hunterRow+1)%boardSize;

			dungeon[hunterRow][hunterCol].isVisible = true;
			dungeon[hunterRow][hunterCol].hasHunter = true;
		}
		
		else if (direction.toLowerCase().equals("right"))
		{
			dungeon[hunterRow][hunterCol].hasHunter = false;
			hunterCol=(hunterCol+1)%boardSize;

			dungeon[hunterRow][hunterCol].isVisible = true;
			dungeon[hunterRow][hunterCol].hasHunter = true;
		}
		
		else if(direction.toLowerCase().equals("left"))
		{
			dungeon[hunterRow][hunterCol].hasHunter = false;
			hunterCol=(hunterCol-1+boardSize)%boardSize;

			dungeon[hunterRow][hunterCol].isVisible = true;
			dungeon[hunterRow][hunterCol].hasHunter = true;
		}
		
		else if(direction.toLowerCase().equals("shoot arrow"))
		{
			shootArrow(getArrowDirection());
		}
		
	}
	
	/* shootArrow fires the arrow and determines whether or not the arrow hits the Wumpus
	 * or comes back and hits the hunter. It uses a string that is received from user input
	 * via the getArrowDirection() method.
	 */
	public void shootArrow(String arrowDirection) {
		
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
	 * that the user wants to go.  It checks that the direction is: up,down,left, or right or
	 * asks the user if they want to shoot their arrow.
	 */
	 
	public String getDirection(){
		
		
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
	
		return direction;
	}
	
	/*This methods gets the direction that the user wants to shoot the arrow. The user
	 * can shoot the arrow left, right, up, or down.
	 */
	public String getArrowDirection() {
		Scanner keyboard = new Scanner(System.in);

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
			
			return arrowDirection;
	}
	
	/* This method determines whether or not a winning or losing condition has been met
	 * by determining whether or not the hunter was eaten, the hunter fell into a pit, or 
	 * the arrow hit the wumpus or hunter.
	 */
	public boolean gameOver(){
		
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
	
	//This method prints out a unique string telling the user how they won or lost the game.
	 
	public String gameOverMessage(){
		
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
			message="WHYULOSE? --- INTERNAL ERROR"; //If this executes we fail at life.
		}
		
		return message;
	}
	
	/*This method is super cool and asks the user if they want to play the super awesome game again
	 * instead of having to be super lame and restart the program every time.
	 */
	
	public String playAgain(HunterAndWumpusText testDung1){
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
		
		return playGameString;
	}
	
	/*This method is where all the fun happens....literally. It runs the game until the user 
	 * doesn't want to play anymore. So basically runs forever.
	 */
	public static void main(String[] args){
		
		HunterAndWumpusText testDung1 = new HunterAndWumpusText();
		System.out.println(testDung1.toString());
		String playGameString = "yes";
			
		while(playGameString.equals("yes")){
			testDung1.move(testDung1.getDirection());
			if (testDung1.gameOver())
			{
				playGameString=testDung1.playAgain(testDung1);
				if(playGameString.equals("yes")){
					testDung1 = new HunterAndWumpusText();
					System.out.println(testDung1.toString());
				}						
			}
			else
			{
				System.out.println(testDung1.toString());
			}
		}
		
		System.out.println("Thanks for playing!");
	}

}
