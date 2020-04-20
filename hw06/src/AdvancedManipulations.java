public class AdvancedManipulations {

    /**
     * Change the contrast of a picture.
     *
     * Your job is to change the intensity of the colors in the picture.
     * The simplest method of changing contrast is as follows:
     *
     * 1. Find the average color intensity of the picture.
     *    a) Sum the values of all the color components for each pixel.
     *    b) Divide the total by the number of pixels times the number of
     *       components (3).
     * 2. Subtract the average color intensity from each color component of
     *    each pixel. This will make the average color intensity zero.
     * 3. Scale the intensity of each pixel's color components by multiplying
     *    them by the "multiplier" parameter. Note that the multiplier is a
     *    double (a decimal value like 1.2 or 0.6) and color values are ints
     *    between 0 and 255.
     * 4. Add the original average color intensity back to each component of
     *    each pixel.
     * 5. Clip the color values so that all color component values are between
     *    0 and 255. (This should be handled by the Pixel class anyway!)
     *
     * Hint: You should use Math.round() before casting to an int for the average
     * color intensity and for the scaled RGB values.
     */
    public static PixelPicture adjustContrast(
            PixelPicture pic, double multiplier) {
        
    	int h = pic.getHeight();
    	int w = pic.getWidth();
    	
    	
    	Pixel[][] bmp = pic.getBitmap();
    	// Create a variable to store the average color values 
    	int avg_color = 0;
    	
    	for (int x = 0; x < w; x++) {
    		for (int y = 0; y < h; y++) {
    			Pixel p = bmp[x][y];
    			int r = p.getRed();
    			int g = p.getGreen();
    			int b = p.getBlue();
    			int s = r + g + b;
    			avg_color += s;
    		}
    	}
    	
    	
    	// find the average intensity of the picture 
    	int avg_intensity = (int) Math.round(avg_color / (w * h * 3));
    	
    	for (int i = 0; i < w; i++) {
    		for (int j = 0; j < h; j++) {
    			Pixel p = bmp[i][j];
    			int r = (int) Math.round((p.getRed() - avg_intensity) * multiplier) + avg_intensity;
    			int g = (int) Math.round((p.getGreen() - avg_intensity) * multiplier)+avg_intensity;
    			int b = (int) Math.round((p.getBlue() - avg_intensity) * multiplier)+ avg_intensity;
    			bmp[i][j] = new Pixel(r, g, b);
    		}
    	}
    	
    	return new PixelPicture(bmp);
    	
    }

    /**
     * Reduce a picture to its most common colors.
     *
     * You will need to make use of the ColorMap class to generate a map from
     * Pixels of a certain color to the frequency with which pixels of that color
     * appear in the image. If you go to the ColorMap class, you will notice 
     * that it does not have an explicitily declared constructor. In those cases,
     * Java provides a default constructor, which you can call with no arguments:
     * ColorMap m = new ColorMap();
     * Once you have generated your ColorMap, select your palette by retrieving
     * the pixels whose color appears in the picture with the highest frequency.
     * Then change each pixel in the picture to one with the closest matching
     * color from your palette.
     *
     * Note that if there are two different colors that are the *same* minimal
     * distance from the given color, your code should select the most
     * frequently appearing one as the new color for the pixel. If both colors
     * appear with the same frequency, your code should select the one that
     * appears *first* in the output of the colormap's getSortedPixels.
     *
     * Algorithms like this are widely used in image compression. GIFs in
     * particular compress the palette to no more than 255 colors. The variant
     * we have implemented here is a weak one, since it only counts color
     * frequency by exact match. Advanced palette reduction algorithms (known as
     * "indexing" algorithms) calculate color regions and distribute the palette
     * over the regions. For example, if our picture had a lot of shades of blue
     * and a little bit of red, our algorithm would likely choose a palette of
     * all blue colors. An advanced algorithm would recognize that blues look
     * similar and distribute the palette so that it would be possible to
     * display red as well.
     *
     * @param pic The Pixel for which the closest match should be found.
     * @return The most closely matched Pixel from the palette.
     */
    public static PixelPicture reducePalette(PixelPicture pic, int numColors) {
        
    	ColorMap m = new ColorMap();
    	
    	int h = pic.getHeight();
    	int w = pic.getWidth();
    	
    	Pixel[][] bmp = pic.getBitmap();
    	
    	for (int x = 0; x < w; x++) {
    		for (int y = 0; y < h; y++) {
    			
    			Pixel p = bmp[x][y];
    			
    			if (m.contains(p)) {
    				int v = m.getValue(p);
    				m.put(p, v + 1);
    			} else {
    				m.put(p, 1);    				
    			}
    		}
    	}
    	
    	
    	Pixel[] palette = m.getSortedPixels();
    	
    	for (int i = 0; i < w; i++) {
    		for (int j = 0; j < h; j++) {
    			
    			Pixel p = bmp[i][j];
    			
    			int dist = 255+255+255; 
    			Pixel closePixel = p; 
    			
    			for(int v = 0; v < numColors; v++) {
    				
    				if (p.distance(palette[v]) < dist) {
    					dist = p.distance(palette[v]);
    					closePixel = palette[v];
    				}
    			}
    			bmp[i][j] = closePixel;
    		}
    	}
    	
    	return new PixelPicture(bmp);
    }

    

    /**
     * Blur an image.
     *
     * There are different blurring algorithms, but we'll use the simplest:
     * box blur. Box blurring works by averaging the box-shaped neighborhood
     * around a pixel. The size of the box is configurable by setting the
     * radius, half the length of a side of the box. In the simplest case - a
     * radius of 1 - blurring just takes the average around a pixel. For
     * example, to blur around the pixel at (1,1) with radius 1, we take the
     * average value of the pixels of its neighborhood: (0,0) though (2,2),
     * including (1,1).
     *
     * NOTE: when blurring a pixel, make sure that you access the pixels in
     * its neighborhood in the original image. You shouldn't use already
     * "blurred" pixels to calculate the blur.
     *
     * Each Pixel is composed of 3 colors (red, green, and blue, or RGB) called
     * components. Each component should be averaged separately. For a radius
     * R, we set component c at location (x,y) according to the formula below:
     *
     * c = sum(c of each pixel within radius R) / ((2R + 1) * (2R + 1))
     *
     * Logically, it makes sense that this would be the formula for the
     * average. We sum the amount of each color in neighboring pixels and then
     * divide by the number of pixels we considered in the neighborhood.
     *
     * Note that this equation disregards corner cases. When blurring (0,0)
     * with radius 1, we only need to consider the top-left corner, (0,0)
     * through (1,1) - we'll divide by 4 at the end, not 9. You'll have to be
     * careful to only access bitmaps inside of their bounds.
     *
     * You can assume that you will not be given a radius less than 1.
     *
     * Note: In computing the average, you should be doing double division.
     * Use Math.round() before casting to an int when computing the average.
     * If you do not follow these specifications, the values you compute will
     * not pass our tests.
     *
     * @param pic The picture to be blurred.
     * @param radius The radius of the blurring box.
     * @return A blurred version of the original picture.
     */
    public static PixelPicture blur(PixelPicture pic, int radius) {
    	
    	int h = pic.getHeight();
    	int w = pic.getWidth();
    	
    	Pixel[][] bmp = pic.getBitmap();
    	Pixel[][] tgt = new Pixel[w][h];
    	
    	for (int k = 0; k < w; k++) {
    		for (int l = 0; l < h; l++) {
    			
    			tgt[k][l] = AveragePixel(bmp, k, l, radius, h, w);
    			
    		}
    	}
    	
    	
        return new PixelPicture(tgt);
    }

    // You may want to add a static helper function here to help find the
    // average color around the pixel you are blurring.

    private static Pixel AveragePixel (Pixel[][] p, int x, int y, int r, int h, int w) {
    
		int avgR = 0; 
		int avgG = 0;
		int avgB = 0;
		int boxCount = 0;
		
		for (int i = 0; i <= 2*r; i++) {
			for (int j = 0; j <= 2*r; j++) {
				
				if ((x - r + i) >= 0 && (y - r + j) >= 0 
						&& (x - r + i) < w  && (y - r + j) < h) {
					avgR += p[x - r + i][y - r + j].getRed();
					avgG += p[x - r + i][y - r + j].getGreen();
					avgB += p[x - r + i][y - r + j].getBlue();
					boxCount+=1;
				}
			}
		}
		
		int avR = (int)Math.round(avgR / (double)boxCount);
		int avG = (int)Math.round(avgG / (double)boxCount);
		int avB = (int)Math.round(avgB / (double)boxCount);
		
		return new Pixel(avR, avG, avB);
	
    }
    
    /**
     * Challenge Problem (this problem is worth 0 points):
     * Flood pixels of the same color with a different color.
     *
     * The name is short for flood fill, which is the familiar "paint bucket"
     * operation in graphics programs. In a paint program, the user clicks on a
     * point in the image. Every neighboring, similarly-colored point is then
     * "flooded" with the color the user selected.
     *
     * Suppose we want to flood color at (x,y). The simplest way to do flood
     * fill is as follows:
     *
     * 1. Let target be the color at (x,y).
     * 2. Create a set of points Q containing just the point (x,y).
     * 3. Take the first point p out of Q.
     * 4. Set the color at p to color.
     * 5. For each of p's non-diagonal neighbors - up, down, left, and right -
     *    check to see if they have the same color as target. If they do, add
     *    them to Q.
     * 6. If Q is empty, stop. Otherwise, go to 3.
     *
     * This is a naive algorithm that can be made significantly faster if you
     * wish to try.
     *
     * For Q, you should use the provided IntQueue class. It works very much
     * like the queues we implemented in OCaml.
     *
     * @param pic The original picture to be flooded.
     * @param c The pixel the user "clicked" (representing the color that should be flooded).
     * @param x The x-coordinate of the point on which the user "clicked."
     * @param y The y-coordinate of the point on which the user "clicked."
     * @return A new picture with the appropriate region flooded.
     */
    public static PixelPicture flood(PixelPicture pic, Pixel c, int x, int y) {
        return pic;
    }
}
