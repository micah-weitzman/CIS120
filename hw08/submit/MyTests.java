import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Set;
import java.util.TreeSet;

import org.junit.*;

/** Put your OWN test cases in this file, for all classes in the assignment. */
public class MyTests {
	
	
	@Test
	public void inputIsEmpty() throws IOException{
		Reader in = new StringReader(""); 
	    TokenScanner d = new TokenScanner(in);
	    try {
	        assertFalse("end of stream", d.hasNext());

	    } finally {
	        in.close();
	    }
	}
	
	
	@Test 
	public void inputIsOneWord() throws IOException {
		Reader in = new StringReader("One"); 
	    TokenScanner d = new TokenScanner(in);
	    try {
	        assertTrue("has next", d.hasNext());
	        assertEquals("One", d.next());
	        
	        assertFalse("end of stream", d.hasNext());

	    } finally {
	        in.close();
	    }
	}
	
	
	@Test
	public void inputIsSingleNonWord() throws IOException {
		Reader in = new StringReader("-"); 
	    TokenScanner d = new TokenScanner(in);
	    try {
	        assertTrue("has next", d.hasNext());
	        assertEquals("-", d.next());
	        
	        assertFalse("end of stream", d.hasNext());
	        
	    } finally {
	        in.close();
	    }
	}
	
	@Test
	public void inputEndsWithWord() throws IOException {
		Reader in = new StringReader("Hello World"); 
	    TokenScanner d = new TokenScanner(in);
	    try {
	        assertTrue("has next", d.hasNext());
	        assertEquals("Hello", d.next());
	        
	        assertTrue("has next", d.hasNext());
	        assertEquals(" ", d.next());
	        
	        assertTrue("has next", d.hasNext());
	        assertEquals("World", d.next());
	        
	        assertFalse("end of stream", d.hasNext());
	        
	    } finally {
	        in.close();
	    }
	}
	
	
	@Test
	public void inputEndsWithNonWord() throws IOException {
		Reader in = new StringReader("Hello World!"); 
	    TokenScanner d = new TokenScanner(in);
	    try {
	        assertTrue("has next", d.hasNext());
	        assertEquals("Hello", d.next());
	        
	        assertTrue("has next", d.hasNext());
	        assertEquals(" ", d.next());
	        
	        assertTrue("has next", d.hasNext());
	        assertEquals("World", d.next());
	        
	        assertTrue("has next", d.hasNext());
	        assertEquals("!", d.next());
	        
	        assertFalse("end of stream", d.hasNext());
	        
	    } finally {
	        in.close();
	    }
	}
	
	
	//////////////////////
	// Dictionary tests //
	//////////////////////

	@Test(timeout=600)
    public void testDictionaryContains() throws IOException {
        Dictionary d = Dictionary.make("files/dictionary.txt");
        assertTrue("'Adora' is in the dictionary", d.isWord("adora"));
    }
	
	@Test(timeout=600)
    public void testDictionaryDoesNotContains() throws IOException {
        Dictionary d = Dictionary.make("files/dictionary.txt");
        assertFalse("'--' is not in the dictionary", d.isWord("--"));
    }

	@Test(timeout=600)
    public void testDictionaryNumberOfWords() throws IOException {
        Dictionary d = Dictionary.make("files/dictionary.txt");
        assertTrue("Number of words in the dictionary", d.getNumWords() == 60822);
    }

	@Test(timeout=600)
    public void testDictionaryEmptyNotWord() throws IOException {
        Dictionary d = Dictionary.make("files/dictionary.txt");
        assertFalse("'' is not in the dictionary", d.isWord(""));
    }
	
	@Test(timeout=600)
    public void testDictionaryAllUpperCase() throws IOException {
        Dictionary d = Dictionary.make("files/dictionary.txt");
        assertTrue("'COURT' is in the dictionary", d.isWord("COURT"));
    }
	
	@Test(timeout=600)
    public void testDictionaryCamelCase() throws IOException {
        Dictionary d = Dictionary.make("files/test_dict.txt");
        assertTrue("'elephant' is in the dictionary", d.isWord("elephant"));
    }
	
	@Test(timeout=600)
    public void testDictionaryBlankLine() throws IOException {
        Dictionary d = Dictionary.make("files/test_dict.txt");
        assertFalse("Blank line is not in the dictionary", d.isWord("\n"));
    }
	
	@Test(timeout=600)
    public void testDictionaryWhiteSpace() throws IOException {
        Dictionary d = Dictionary.make("files/test_dict.txt");
        assertFalse("'white ' is in the dictionary", d.isWord("white "));
    }
	
	@Test(timeout=600)
    public void testDictionaryDuplicateWord() throws IOException {
        Dictionary d = Dictionary.make("files/test_dict.txt");
        assertTrue("Duplicate words only count once", d.getNumWords() == 3);
    }
	
	@Test(timeout=600)
    public void testDictionaryNullInput() throws IOException {
        Dictionary d = Dictionary.make("files/dictionary.txt");
        assertFalse("Null value is not in the dictionary", d.isWord(null));
    }
	
	
	
	/////////////////////////
	// File Corrector test //
	/////////////////////////
	
    private Set<String> makeSet(String[] strings) {
        Set<String> mySet = new TreeSet<String>();
        for (String s : strings) {
            mySet.add(s);
        }

        return mySet;
    }
	
	@Test
    public void testGetCorrectionWhitespace() throws IOException, FileCorrector.FormatException  {
        Corrector c = FileCorrector.make("files/smallMisspellings.txt");
        assertEquals("helo -> helllo", makeSet(new String[]{"hello"}), c.getCorrections("helol"));
        assertEquals("herro -> hello", makeSet(new String[]{"hello"}), c.getCorrections("herro"));
    }
	
	@Test
	public void testGetCorrectionNoCorrections() throws IOException, FileCorrector.FormatException {
        Corrector c = FileCorrector.make("files/smallMisspellings.txt");
        assertEquals("thsi -> ''", makeSet(new String[]{}), c.getCorrections("thsi"));
    }
	
	@Test
	public void testGetCorrectioMultipleCorrect() throws IOException, FileCorrector.FormatException{
        Corrector c = FileCorrector.make("files/smallMisspellings.txt");
        assertEquals("helo -> {hello, hell}", makeSet(new String[]{"hello","hell"}), 
        		c.getCorrections("helo"));
	}
	
	@Test
	public void testGetCorrectionCamelCase() throws IOException, FileCorrector.FormatException {
        Corrector c = FileCorrector.make("files/smallMisspellings.txt");
        assertEquals("myke -> mike", makeSet(new String[]{"mike"}), c.getCorrections("myke"));
    }
	
	@Test
	public void testGetCorrectionUPPERCASE() throws IOException, FileCorrector.FormatException {
        Corrector c = FileCorrector.make("files/smallMisspellings.txt");
        assertEquals("HELO -> Hello", makeSet(new String[]{"Hell", "Hello"}),
        		c.getCorrections("HELO"));
    }
	
	@Test
	public void testGetCorrectionLowercase() throws IOException, FileCorrector.FormatException {
        Corrector c = FileCorrector.make("files/smallMisspellings.txt");
        assertEquals("Teh -> The", makeSet(new String[]{"The"}), c.getCorrections("Teh"));
    }


}
