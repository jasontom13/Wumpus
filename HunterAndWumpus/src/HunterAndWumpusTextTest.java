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


public class HunterAndWumpusTextTest {

	@Test
	public void test() {
		
		String[][] testDung =

		    { { "[ ]", "[ ]", "[ ]", "[ ]", "[ ]" },
		      { "[ ]", "[ ]", "[ ]", "[ ]", "[ ]" },
		      { "[ ]", "[ ]", "[ ]", "[ ]",  "[ ]" }, };
		
		HunterAndWumpusText test = new HunterAndWumpusText(testDung);
		System.out.println(testDung.toString());
		assertFalse(test.hasBlood(0, 0));
		
	}
	
	@Test
	public void testDungeon() {
		
		/* Wumpus (1,7)
		 * Pits (0,0) (6,2) (9,3) (6,8)
		 * Hunter (6,4)
		 */
		int [] fixed = new int [] {1,7,0,0,6,2,9,3,6,8,6,4};
		HunterAndWumpusText testDung= new HunterAndWumpusText(fixed);
		System.out.println(testDung.toString());

		
	}

}
