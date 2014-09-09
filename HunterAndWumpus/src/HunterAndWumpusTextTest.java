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

}
