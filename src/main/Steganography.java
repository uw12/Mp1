package main;

import java.util.Arrays;

public class Steganography {

    /*
     * ********************************************
     * Part 1b: embed/reveal BW
     * image ********************************************
     */

    /*
     * Methods to deal with the LSB
     */
	

	// Méthode auxiliaire qui convertit un boolean en 0 ou 1
	public static int toInt(boolean m) {
		if(m) {
			return 1;
		}
		return 0;
	}
	
    /**
     * Inserts a boolean into the Least Significant Bit of an int
     * @param value The int in which to insert a boolean value
     * @param m The boolean value to be inserted into the int
     * @return An int corresponding to {@code value} with {@code m} inserted into its LSB
     */
	
	// On remplace le LSB par la valeur associée à m
    public static int embedInLSB(int value, boolean m) {
        return ((value >> 1) << 1)  + toInt(m);
    }

    /**
     * Extracts the Least Significant Bit of an integer
     * @param value The integer from which to extract the LSB value
     * @return A boolean corresponding to the value of {@code value}'s LSB
     */
    public static boolean getLSB(int value) {
        if(value%2 == 0) {
        	return false;
        }
        return true;
    }

    /*
     * Linear embedding
     */
    /**
     * Embeds a black and white image into a color image's LSB layer using linear embedding
     * @param cover The image in which to embed {@code message}
     * @param message The image to embed into {@code cover}
     * @return A <b>copy</b> of {@code cover} with {@code message}'s pixel values embedded in a linear fashion in the LSB layer
     */
    public static int[][] embedBWImage(int[][] cover, boolean[][] message) {
        int[][] embededInCover = cover.clone(); // on crée une copie de cover
        
        for(int i = 0; i < message.length; i++) {
        	for(int j = 0; j < message[0].length; j++) {
        		embededInCover[i][j] = embedInLSB(cover[i][j], message[i][j]); 
        		// on parcourt uniquement la partie de cover sur laquelle on va 
        		// cacher le message
        	}
        }
        
        return embededInCover;
    }
    
    /**
     * Reveals a black and white image which was embedded in the LSB layer of another
     * @param cover A color image containing an image embedded in its LSB layer
     * @return The image extracted from the LSB layer of {@code cover}
     */
    public static boolean[][] revealBWImage(int[][] cover) {
    	int height = cover.length, 
    		width = cover[0].length;
        boolean[][] revealedBW = new boolean[height][width];
        
        for(int i = 0; i < height; i++) {
        	for(int j = 0; j < width; j++) {
        		revealedBW[i][j] = getLSB(cover[i][j]);
        	}
        }
        
        return revealedBW;
    }

    /*
     * ********************************************
     * Part 2b: embed/reveal
     * BitArray (Text)
     ********************************************
     */

    /**
     * Embeds a boolean array into the LSB layer of a color image, in a linear fashion
     * @param cover The image in which to embed the bit array
     * @param message The boolean array to be embedded
     * @return A <b>copy</b> of {@code cover} with {@code message}'s values embedded in a linear fashion in the LSB layer
     */
    public static int[][] embedBitArray(int[][] cover, boolean[] message) {
    	//variables de la methode
    	int height = cover.length,
    		width = cover[0].length,
        	msgLg = message.length;
        int[][] response = new int[height][width];
        
        for(int i = 0; i < height; i++) {
        	for(int j = 0; j < width; j++) {
        		int k = (i * width) + j;
        		if(k < msgLg) {
        			response[i][j] = embedInLSB(cover[i][j], message[k]);
        		} else {        			
        			response[i][j] = cover[i][j];        			
        		}
        	}
        }        
        
        return response;
    }
    

    /**
     * Reveals a boolean array which was embedded in the LSB layer of an image
     * @param cover A color image containing an bit array embedded in its LSB layer
     * @return The bit array extracted from the LSB layer of {@code cover}
     */
    public static boolean[] revealBitArray(int[][] cover) {
    	//variables de la methode
    	int height = cover.length,
    		width = cover[0].length;
        boolean[] response = new boolean[width * height];
        
        for(int i = 0; i < height; i++) {
        	for(int j = 0; j < width; j++) {
        		response[((i * width) + j)] = getLSB(cover[i][j]);
        	}
        }
        return response;
    }

    /**
     * Embeds a String into the LSB layer of a color image, in a linear fashion
     * @param cover The image in which to embed the bit array
     * @param message The String to be embedded
     * @return A <b>copy</b> of {@code cover} with {@code message}'s binary representation embedded in a linear fashion in the LSB layer
     * @see TextMessage#stringToBitArray(String)
     * @see Steganography#embedBitArray(int[][], boolean[])
     */
    public static int[][] embedText(int[][] cover, String message) {
       return embedBitArray(cover, TextMessage.stringToBitArray(message));
    }

    /**
     * Reveals a String which was embedded in the LSB layer of an image
     * @param cover A color image containing a String embedded in its LSB layer
     * @return The String extracted from the LSB layer of {@code cover}
     * @see TextMessage#bitArrayToString(boolean[])
     */
    public static String revealText(int[][] cover) {    	
    	return TextMessage.bitArrayToString(revealBitArray(cover));        
    }

    /*
     * ********************************************
     * Part 3: embed/reveal bit
     * array with spiral embedding
     ********************************************
     */

    /**
     * Embeds a black and white image into a color image's LSB layer using spiral embedding
     * @param cover The image in which to embed {@code message}
     * @param message The image to embed into {@code cover}
     * @return A <b>copy</b> of {@code cover} with {@code message}'s pixel values embedded in a spiral fashion in the LSB layer
     * @see ImageMessage#bwImageToBitArray(boolean[][])
     * @see Steganography#embedSpiralBitArray(int[][], boolean[])
     */
    public static int[][] embedSpiralImage(int[][] cover, boolean[][] bwImage) {
        //TODO: implement me!
        return null;
    }

    /**
     * Reveals an image which was embedded in the LSB layer of an image in a spiral fashion
     * @param cover A color image containing an bit array embedded in its LSB layer
     * @return The image extracted from the LSB layer of {@code cover}
     * @see ImageMessage#bitArrayToImage(boolean[])
     * @see Steganography#revealSpiralBitArray(int[][])
     */
    public static boolean[][] revealSpiralImage(int[][] cover) {
        //TODO: implement me!
        return null;
    }

    /**
     * Embeds a bit array into a color image's LSB layer using linear embedding
     * @param cover The image in which to embed {@code message}
     * @param message The boolean array to embed into {@code cover}
     * @return A <b>copy</b> of {@code cover} with {@code message}'s values embedded in a spiral fashion in the LSB layer
     */
    public static int[][] embedSpiralBitArray(int[][] cover, boolean[] message) {
        //TODO: implement me!
		assert(Utils.isCoverLargeEnough(cover, message)); // example of how to use assertions
        return null;
    }

    /**
     * Reveals a boolean array which was embedded in the LSB layer of an image in a spiral fashion
     * @param cover A color image containing an bit array embedded in its LSB layer
     * @return The bit array extracted from the LSB layer of {@code cover}
     */
    public static boolean[] revealSpiralBitArray(int[][] hidden) {
        //TODO: implement me!
        return null;
    }

}

