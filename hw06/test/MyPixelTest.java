import org.junit.Test;
import static org.junit.Assert.*;


/** 
 *  Use this file to test your implementation of Pixel.
 * 
 *  We will manually grade this file to give you feedback
 *  about the completeness of your test cases.
 */

public class MyPixelTest {

    /*
     * Remember, UNIT tests should ideally have one point of failure. Below we
     * give you an example of a unit test for the Pixel constructor that takes
     * in three ints as arguments. We use the getRed(), getGreen(), and
     * getBlue() methods to check that the values were set correctly. This one
     * test does not comprehensively test all of Pixel so you must add your own.
     *
     * You might want to look into assertEquals, assertTrue, assertFalse, and
     * assertArrayEquals at the following:
     * http://junit.sourceforge.net/javadoc/org/junit/Assert.html
     *
     * Note, if you want to add global variables so that you can reuse Pixels
     * in multiple tests, feel free to do so.
     */

    @Test
    public void testConstructInBounds() {
        Pixel p = new Pixel(40, 50, 60);
        assertEquals(40, p.getRed());
        assertEquals(50, p.getGreen());
        assertEquals(60, p.getBlue());
    }

    /* ADD YOUR OWN TESTS BELOW */
    
    @Test
    public void testListConstructorAllValues() {
    	int[] l = {40,50,60}; 
        Pixel p = new Pixel(l);
        assertEquals(40, p.getRed());
        assertEquals(50, p.getGreen());
        assertEquals(60, p.getBlue());
    }
    
    @Test 
    public void testListConstructorPartialValues() {
    	int[] l = {40};
    	Pixel p = new Pixel(l);
    	assertEquals(40, p.getRed());
    	assertEquals(0, p.getGreen());
    	assertEquals(0, p.getBlue());
    }
    
    @Test 
    public void testListConstructor2PartialVlaues() {
    	int[] l = {40, 50};
    	Pixel p = new Pixel(l);
    	assertEquals(40, p.getRed());
    	assertEquals(50, p.getGreen());
    	assertEquals(0, p.getBlue());
    }
    
    @Test 
    public void testConstructorOutOfBounds() {
    	Pixel p = new Pixel(260,-12,500);
    	assertEquals(255, p.getRed());
    	assertEquals(0, p.getGreen());
    	assertEquals(255, p.getBlue());
    }
    
    @Test
    public void testGetComponents() {
    	Pixel p = new Pixel(40,50,0);
    	int[] l = {40,50,0};
    	assertEquals(p.getComponents()[1], l[1]);
    }
    
    @Test
    public void testDistanceNull() {
    	Pixel p = new Pixel(40,50,0);
    	assertEquals(-1,p.distance(null));
    }
    
    @Test 
    public void testDianceNotNull() {
    	Pixel p = new Pixel(40,50,0);
    	Pixel pn = new Pixel(30,40,10);
    	assertEquals(30, p.distance(pn));
    }
    
    @Test
    public void testToString() {
    	Pixel p = new Pixel(40,50,0);
    	assertEquals("(40, 50, 0)", p.toString());
    }
    
    @Test
    public void testEquals() {
    	Pixel p = new Pixel(40,50,0);
    	int[] l = {40,50,0};
    	Pixel pn = new Pixel(l);
    	assertEquals(true, p.equals(pn));
    }
}
