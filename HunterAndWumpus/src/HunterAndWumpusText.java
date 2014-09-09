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
		//private String displayValue;
		private final int row;
		private final int col;
		
		public Room(/*String displayValue, */int row, int col){
			
			this.row = row;
			this.col = col;
			hasBlood = false;
			hasSlime = false;
			hasGoop = false;
			hasWumpus = false;
			hasPit = false;
			hasHunter = false;
			isVisible = false;
			//this.displayValue = displayValue;
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
	static int boardSize;
	Random generator = new Random();
	static int hunterRow;
	static int hunterCol;
	
	public HunterAndWumpusText(String[][] map){
		
		dungeon = new Room[map.length][map[0].length];
		
		for(int i=0; i<map.length;i++){
			for(int j=0; j<map[0].length;j++){
				dungeon[i][j] = new Room(i, j);
			}
		}
		
		setWumpus(dungeon);
		setSlimePits(dungeon);
		setHunter(dungeon);
	}
	
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
		setSlimePits(dungeon);
		setHunter(dungeon);
	}
	
	public String toString(){
		
		String mapString = "";
		
		for(int i = 0; i < dungeon.length; i++){
			for(int j = 0; j < dungeon[0].length; j++){
				
				if(dungeon[i][j].isVisible){
					if(dungeon[i][j].hasBlood){
						mapString = mapString + "[B] ";
					}
					else if(dungeon[i][j].hasGoop){
						mapString = mapString + "[G] ";
					}
					else if(dungeon[i][j].hasHunter){
						mapString = mapString + "[O] ";
					}
					else if(dungeon[i][j].hasSlime){
						mapString = mapString + "[S] ";
					}
					else if(dungeon[i][j].hasWumpus){
						mapString = mapString + "[W] ";
					}
					else if(dungeon[i][j].hasPit){
						mapString = mapString + "[P] ";
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
	
	public static void main(String[] args){
		
			HunterAndWumpusText testDung1 = new HunterAndWumpusText();
			System.out.println(testDung1.toString());
			int i=0;
			
			while(i<100){
				move();
				System.out.println(testDung1.toString());
			}
				
	}
	
	public void setHunter(Room[][] map){
		
		hunterRow = generator.nextInt(dungeon.length);
		hunterCol = generator.nextInt(dungeon[0].length);
		while(hunterCount()<1){
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
		
		Random generator = new Random();
		int randomRow = generator.nextInt(dungeon.length);
		int randomCol = generator.nextInt(dungeon[0].length);
		dungeon[randomRow][randomCol].isVisible = true;
		dungeon[randomRow][randomCol].hasWumpus = true;
	}
	
	public void setBlood(Room[][] map){
		
		
	}
	
	public void setSlimePits(Room[][] map){
		
		Random generator = new Random();
		int randomPitNumber = generator.nextInt((5 - 3) + 1) + 3;
		//int randomPitNumber = generator.nextInt(3) + 3; //generate a random number between 3 and 5 pits
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

	private static boolean gameOver() {
		
		
		return true;
	}
	
	public static void move(){
		
		String direction;
		direction=getDirection();
		
		if (direction.equals("up"))
		{
			dungeon[hunterRow][hunterCol].hasHunter = false;
			hunterRow=(hunterRow-1+boardSize)%boardSize;
			
			System.out.println(hunterRow);
			dungeon[hunterRow][hunterCol].isVisible = true;
			dungeon[hunterRow][hunterCol].hasHunter = true;
			
		}
		
		else if (direction.equals("down"))
		{
			dungeon[hunterRow][hunterCol].hasHunter = false;
			hunterRow=(hunterRow+1)%boardSize;
			System.out.println(hunterRow);
			dungeon[hunterRow][hunterCol].isVisible = true;
			dungeon[hunterRow][hunterCol].hasHunter = true;
		}
		
		else if (direction.equals("right"))
		{
			dungeon[hunterRow][hunterCol].hasHunter = false;
			hunterCol=(hunterCol+1)%boardSize;
			System.out.println(hunterCol);
			dungeon[hunterRow][hunterCol].isVisible = true;
			dungeon[hunterRow][hunterCol].hasHunter = true;
		}
		
		else
		{
			dungeon[hunterRow][hunterCol].hasHunter = false;
			hunterCol=(hunterCol-1+boardSize)%boardSize;
			System.out.println(hunterCol);
			dungeon[hunterRow][hunterCol].isVisible = true;
			dungeon[hunterRow][hunterCol].hasHunter = true;
		}

		
		
	}
	
	/* getDirection() prompts the player for a direction and then returns the direction
	 * that the user wants to go.  It checks that the direction is: up,down,left, or right.
	 */
	public static String getDirection(){
		
		Scanner keyboard = new Scanner(System.in);
		String direction="";
		
		
		System.out.print("Which direction would you like to go? (up, down, left, right): ");
		direction=keyboard.nextLine();
		direction=direction.toLowerCase();
	
		
		while (!direction.equals("up") && !direction.equals("down") && !direction.equals("left") && !direction.equals("right"))
		{
		
			System.out.println("That is not a valid direction, please enter a valid direction.");
			System.out.print("Which direction would you like to go? (up, down, left, right): ");
			direction=keyboard.nextLine();
			direction=direction.toLowerCase();
		}

		return direction;
		
	}
	
	
}