import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;




import org.junit.Test;

public class DictionaryTest {

    
    @Test(timeout=600)
    public void testDictionaryContainsSimple() throws IOException {
        Dictionary d = Dictionary.make("files/smallDictionary.txt");
        assertTrue("'apple' -> should be true ('apple' in file)", d.isWord("apple"));
        assertTrue("'Banana' -> should be true ('banana' in file)", d.isWord("Banana"));
        assertFalse("'pineapple' -> should be false", d.isWord("pineapple"));
    }

    
    @Test(timeout=600)
    public void testDictionaryContainsApostrophe() throws IOException {
        Dictionary d = Dictionary.make("files/smallDictionary.txt");
        assertTrue("'it's' -> should be true ('it's' in file)", d.isWord("it's"));
    }

    
    @Test(timeout=600)
    public void testConstructorInvalidTokenScanner() {
        try {
            TokenScanner ts = null;
            new Dictionary(ts);
            fail("Expected IllegalArgumentException - null TokenScanner");
        } catch (IllegalArgumentException e){
            //Do nothing - it's supposed to throw this
        }
    }

    

    // Do NOT add your own tests here. Put your tests in MyTest.java
}
