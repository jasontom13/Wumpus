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
	
	private Room[][] dungeon;
	
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
		
		int boardSize;
		Scanner keyBoard = new Scanner(System.in);
	
		System.out.println("Enter dungeon size 10 or greater:");
		boardSize = keyBoard.nextInt();
		while(boardSize < 10){
			System.out.println("Invalid size. Please enter a number 10 or greater:");
			boardSize = keyBoard.nextInt();
		}
		
		keyBoard.close();
		
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
	}
	
	public void setHunter(Room[][] map){
		
		Random generator = new Random();
		int randomRow = generator.nextInt(dungeon.length);
		int randomCol = generator.nextInt(dungeon[0].length);
		while(hunterCount()<1){
			if(dungeon[randomRow][randomCol].hasWumpus 
					|| dungeon[randomRow][randomCol].hasPit 
					|| dungeon[randomRow][randomCol].hasSlime 
					|| dungeon[randomRow][randomCol].hasGoop 
					|| dungeon[randomRow][randomCol].hasBlood){
				//do nothing try again to be on a safe spot
			}
			else{
				dungeon[randomRow][randomCol].isVisible = true;
				dungeon[randomRow][randomCol].hasHunter = true;
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
		int randomPitNumber = generator.nextInt(3) + 3; //generate a random number between 3 and 5 pits
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
}
