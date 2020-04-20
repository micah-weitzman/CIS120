import static org.junit.Assert.*;
import org.junit.*;

import java.io.*;
import java.util.TreeSet;
import java.util.Set;



public class SwapCorrectorTest {

    /**
     * This is a helper method that helps you make a set for comparison. To use:
     *
     * Set<String> blah = makeSet(new String[]{"firstString", "secondString", "nthString"});
     * assertEquals("!!!", blah, someFunction.call());
     * 
     * WARNING: make sure you don't accidently create a set containing a single string.
     *  i.e. this is a common source of bugs: 
     *     makeSet(new String[]{"firstString, secondString"})
     */
    private Set<String> makeSet(String[] strings) {
        Set<String> mySet = new TreeSet<String>();
        for (String s : strings) {
            mySet.add(s);
        }

        return mySet;
    }

    
    @Test
    public void testSwapCorrections() throws IOException {
        Reader reader = new FileReader("files/smallDictionary.txt");
        try {
            Dictionary d = new Dictionary(new TokenScanner(reader));
            SwapCorrector swap = new SwapCorrector(d);
            assertEquals("cya -> {cay}", makeSet(new String[]{"cay"}), swap.getCorrections("cya"));
            assertEquals("oYurs -> {yours}", makeSet(new String[]{"yours"}),
                swap.getCorrections("oYurs"));
        } finally {
            reader.close();
        }
    }

    // Do NOT add your own tests here. Put your test cases in MyTest.java
}
