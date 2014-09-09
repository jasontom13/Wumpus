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
