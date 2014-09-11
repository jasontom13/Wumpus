/* Name: HunterAndWumpusTextTest
 * Authors: Jason Tom, Trevor Fasulo
 * Class: CSc 335
 * Teacher: Rick Mercer
 * Due Date: 9/10/14 11:59PM
 * 
 * Description: This class is used to test a static version of the HunterAndWumpusText pre-made
 * dungeon.  
 */

import static org.junit.Assert.*;

import org.junit.Test;

//Class containing all the @Test methods for HunterAndWumpusText

public class HunterAndWumpusTextTest {


	
	@Test
	public void testPit() {
		
		/* Wumpus (1,7)
		 * Pits (0,9) (6,2) (9,3) (6,8)
		 * Hunter (6,4)
		 */
		int [] fixed = new int [] {1,7,0,9,6,2,9,3,6,8,6,4};
		HunterAndWumpusText testDung= new HunterAndWumpusText(fixed);
		System.out.println(testDung.toString());
		
		assertTrue(testDung.hasHunter(6,4));
		assertTrue(testDung.isVisible(6,4));
		
		assertTrue(testDung.hasWumpus(1,7));
		assertFalse(testDung.isVisible(1,7));
		
		assertTrue(testDung.hasBlood(0,6));
		assertTrue(testDung.hasBlood(0,7));
		assertTrue(testDung.hasBlood(1,5));
		assertTrue(testDung.hasBlood(1,6));
		assertTrue(testDung.hasBlood(1,8));
		assertTrue(testDung.hasBlood(2,6));
		assertTrue(testDung.hasBlood(2,7));
		assertTrue(testDung.hasBlood(2,8));
		assertTrue(testDung.hasBlood(3,7));
		assertTrue(testDung.hasBlood(9,7));
		assertFalse(testDung.isVisible(9,7));
		
		assertTrue(testDung.hasPit(0,9));
		assertTrue(testDung.hasPit(6,2));
		assertTrue(testDung.hasPit(9,3));
		assertTrue(testDung.hasPit(6,8));
		assertFalse(testDung.isVisible(6,8));
		
		assertTrue(testDung.hasSlime(0,0));
		assertTrue(testDung.hasSlime(9,9));
		assertFalse(testDung.isVisible(9,9));
	
		assertTrue(testDung.hasSlime(5,2));
		assertTrue(testDung.hasSlime(7,2));
		assertTrue(testDung.hasSlime(6,3));
		assertTrue(testDung.hasSlime(6,1));
		assertFalse(testDung.isVisible(6,1));
		
		assertTrue(testDung.hasSlime(9,2));
		assertTrue(testDung.hasSlime(9,4));
		assertTrue(testDung.hasSlime(8,3));
		assertTrue(testDung.hasSlime(0,3));
		
		assertTrue(testDung.hasSlime(6,7));
		assertTrue(testDung.hasSlime(6,9));
		assertTrue(testDung.hasSlime(5,8));
		assertTrue(testDung.hasSlime(6,1));
		
		assertTrue(testDung.hasGoop(0,8));
		assertTrue(testDung.hasGoop(1,9));
		assertFalse(testDung.isVisible(1,9));
		
		/* Testing the movement of the Hunter from the starting position (6,4) */
		
		testDung.move("up");
		assertTrue(testDung.hasHunter(5, 4));
		assertTrue(testDung.isVisible(5, 4));
		assertFalse(testDung.hasHunter(6,4));
		assertTrue(testDung.isVisible(6,4));
		
		testDung.move("left");
		testDung.move("left");
		assertTrue(testDung.isVisible(5,2));
		assertTrue(testDung.hasHunter(5,2));
		assertFalse(testDung.gameOver());
		
		testDung.move("down");
		assertTrue(testDung.gameOver());
		assertEquals(testDung.gameOverMessage(),"YOU LOSE...\nYou fell into a bottomless pit and died. get wrecked.");
	}
	
	@Test
	public void testWumpusShot() {
		
		/* Wumpus (1,7)
		 * Pits (0,9) (6,2) (9,3) (6,8)
		 * Hunter (6,4)
		 */
		int [] fixed = new int [] {1,7,0,9,6,2,9,3,6,8,6,4};
		HunterAndWumpusText testDung= new HunterAndWumpusText(fixed);
		System.out.println(testDung.toString());
		
		testDung.move("up");
		testDung.move("up");
		testDung.move("up");
		testDung.move("up");
		testDung.move("up");
		assertFalse(testDung.gameOver());
		testDung.shootArrow("right");
		assertTrue(testDung.gameOver());
		assertEquals("YOU WIN!\nYour arrow pierces the Wumpus' heart and slays it! You venture out of the dungeon a hero.", testDung.gameOverMessage());
	}
	
	@Test
	public void testWumpusEaten() {
		
		/* Wumpus (1,7)
		 * Pits (0,9) (6,2) (9,3) (6,8)
		 * Hunter (6,4)
		 */
		int [] fixed = new int [] {1,7,0,9,6,2,9,3,6,8,6,4};
		HunterAndWumpusText testDung= new HunterAndWumpusText(fixed);
		System.out.println(testDung.toString());
		
		testDung.move("up");
		testDung.move("up");
		testDung.move("up");
		testDung.move("up");
		testDung.move("up");
		assertFalse(testDung.gameOver());
		testDung.move("right");
		testDung.move("right");
		testDung.move("right");
		assertTrue(testDung.gameOver());
		assertEquals("YOU LOSE...\nYou've been eaten by the mighty Wumpus. gg.", testDung.gameOverMessage());
	}
	
	@Test
	public void testNoob() {
		
		/* Wumpus (1,7)
		 * Pits (0,9) (6,2) (9,3) (6,8)
		 * Hunter (6,4)
		 */
		int [] fixed = new int [] {1,7,0,9,6,2,9,3,6,8,6,4};
		HunterAndWumpusText testDung= new HunterAndWumpusText(fixed);
		System.out.println(testDung.toString());

		assertFalse(testDung.gameOver());
		testDung.shootArrow("left");
		assertTrue(testDung.gameOver());
		assertEquals("YOU LOSE...\nYour arrow hits you in the back. That takes skill...too bad you don't live to tell the tale.", testDung.gameOverMessage());

	}
	
	@Test
	public void testNoob2() {
		
		/* Wumpus (1,7)
		 * Pits (0,9) (6,2) (9,3) (6,8)
		 * Hunter (6,4)
		 */
		int [] fixed = new int [] {1,7,0,9,6,2,9,3,6,8,6,4};
		HunterAndWumpusText testDung= new HunterAndWumpusText(fixed);
		System.out.println(testDung.toString());

		assertFalse(testDung.gameOver());
		testDung.shootArrow("up");
		assertTrue(testDung.gameOver());
		assertEquals("YOU LOSE...\nYour arrow hits you in the back. That takes skill...too bad you don't live to tell the tale.",testDung.gameOverMessage());

	}
	
	@Test
	public void testNoob3() {
		
		/* Wumpus (1,7)
		 * Pits (0,9) (6,2) (9,3) (6,8)
		 * Hunter (6,4)
		 */
		int [] fixed = new int [] {1,7,0,9,6,2,9,3,6,8,6,4};
		HunterAndWumpusText testDung= new HunterAndWumpusText(fixed);
		System.out.println(testDung.toString());

		assertFalse(testDung.gameOver());
		testDung.shootArrow("down");
		assertTrue(testDung.gameOver());
		assertEquals("YOU LOSE...\nYour arrow hits you in the back. That takes skill...too bad you don't live to tell the tale.", testDung.gameOverMessage());

	}
	
	@Test
	public void getPitCount() {
		
		/* Wumpus (1,7)
		 * Pits (0,9) (6,2) (9,3) (6,8)
		 * Hunter (6,4)
		 */
		int [] fixed = new int [] {1,7,0,9,6,2,9,3,6,8,6,4};
		HunterAndWumpusText testDung= new HunterAndWumpusText(fixed);
		System.out.println(testDung.toString());

		assertEquals(4, testDung.getTotalPits());

	}
	
	@Test
	public void getHunterCount() {
		
		/* Wumpus (1,7)
		 * Pits (0,9) (6,2) (9,3) (6,8)
		 * Hunter (6,4)
		 */
		int [] fixed = new int [] {1,7,0,9,6,2,9,3,6,8,6,4};
		HunterAndWumpusText testDung= new HunterAndWumpusText(fixed);
		System.out.println(testDung.toString());

		assertEquals(1, testDung.getHunterCount());

	}
	
	
	
	

}
