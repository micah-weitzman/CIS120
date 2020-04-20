import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.*;
import java.util.TreeSet;
import java.util.Set;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LevenshteinTest {
    private Levenshtein corr;

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

    /* This runs before every test method. */
    @Before
    public void setUp() throws IOException {
        Dictionary dict = Dictionary.make("files/smallDictionary.txt");
        corr = new Levenshtein(dict);
    }

    /* This runs after every test method. */
    @After
    public void tearDown() {
        corr = null;
    }

    
    @Test
    public void testConstructorInvalid() throws IOException {
        try{
            new Levenshtein(null);
            fail("Expected an IllegalArgumentException - null dictionary.");
        }
        catch (IllegalArgumentException ex){
            //Do nothing - its supposed to throw an exception!
        }
    }

    
    @Test
    public void testDeletion() throws IOException {
      assertEquals("teh -> {eh,th,te}", makeSet(new String[]{"eh","th","te"}),
          corr.getDeletions("teh"));
    }

    
    @Test
    public void testInsert() throws IOException {
        assertEquals("ay -> {bay, cay, day, any, aye}",
            makeSet(new String[]{"bay", "cay", "day", "any", "aye"}), corr.getInsertions("ay"));
     }

    
    @Test
    public void testSubstitution() throws IOException {
      assertEquals("teh -> {heh, meh, tah, tea, tee, ten, tex}",
        makeSet(new String[]{"heh", "meh", "tah", "tea", "tee", "ten", "tex"}),
        corr.getSubstitutions("teh"));
     }

    
    @Test
    public void testCorrections() throws IOException {
        assertEquals("h -> {a, i, ah, eh, th}", makeSet(new String[]{"a", "i", "ah", "eh", "th"}),
            corr.getCorrections("h"));
     }

    
    @Test
    public void testCorrectionsCase() throws IOException {
        assertEquals("H -> {A, I, Ah, Eh, Th}", makeSet(new String[]{"A", "I", "Ah", "Eh", "Th"}),
            corr.getCorrections("H"));
     }

    
    @Test
    public void testNull() throws IOException {
        try {
            assertEquals(" null -> illegal argument", new TreeSet<String>(),
                corr.getCorrections(null));
            fail("Should have thrown an illegal argument exception");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }
}