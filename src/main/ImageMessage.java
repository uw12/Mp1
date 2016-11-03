package main;
/**
 * @author
 */
public final class ImageMessage {


    /*
     * ********************************************
     * Part 1a: prepare image message (RGB image <-> BW image)
     * ********************************************
     */
    /**
     * Returns red component from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
	
    public static int getRed(int rgb) {
    	
    	// On décalle de 16 bits vers la droite pour récupérer la valeur rouge
    	// Et on ne garde que les 8 premiers bits 
    	return (rgb >> 16) & 0xff;		// renvoi direct, pas besoin de passer par des variables
    }

    /**
     * Returns green component from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
    public static int getGreen(int rgb) {
    	
    	// On décalle de 8 bits vers la droite pour récupérer la valeur verte
    	// Et on ne garde que les 8 premiers bits 
    	return (rgb >> 8) & 0xff;		// renvoi direct, pas besoin de passer par des variables
    }

    /**
     * Returns blue component from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getRGB(int, int, int)
     */
    public static int getBlue(int rgb) {
    	
    	// On n'a pas besoin de décaller pour récupérer la valeur bleue
    	// Et on ne garde que les 8 premiers bits 
    	return rgb & 0xff;		// renvoi direct, pas besoin de passer par des variables
    }

    /**
     * Returns the average of red, green and blue components from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(int)
     */
    public static int getGray(int rgb) {
    	
    	// On fait la moyenne des 3 valeurs (r, g, b) pour obtenir la nuance de gris
        return (getRed(rgb) + getBlue(rgb) + getGreen(rgb)) / 3;		// renvoi direct, pas besoin de passer par des variables
    }

    /**
     * @param gray an integer between 0 and 255
     * @param threshold
     * @return true if gray is greater or equal to threshold, false otherwise
     */
    public static boolean getBW(int gray, int threshold) {
    	
    	// conversion de gray en booléen ("noir" ou "blanc")
    	// renvoie vrai si gray >= threshold, faux sinon 
        return (gray >= threshold);		// renvoi direct, pas besoin de passer par des variables
    }

    /**
     * Returns packed RGB components from given red, green and blue components.
     * @param red an integer between 0 and 255
     * @param green an integer between 0 and 255
     * @param blue an integer between 0 and 255
     * @return 32-bits RGB color
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     */
    public static int getRGB(int red, int green, int blue) {
    	
    	// "vérification/correction" des arguments
    	// On récupère une valeur de r, g, b entre 0 et 255
    	red = Math.min(Math.max(0, red), 255);
    	green = Math.min(Math.max(0, green), 255);
    	blue = Math.min(Math.max(0, blue), 255); 
    	
    	// conversion du triplet (r,g,b) en valeur rgb
    	// On fait la somme en décallant red de 16 bits à gauche et green de 8 bits
    	// (représentation de la couleur en binaire)
    	return (red << 16) + (green << 8) + blue;
    }

    /**
     * Returns packed RGB components from given grayscale value.
     * @param gray an integer between 0 and 255
     * @return 32-bits RGB color
     * @see #getGray
     */
    public static int getRGB(int gray) {

    	// "vérification/correction" des arguments
    	// on récupère une valeur de gris entre 0 et 255
    	gray = Math.min(Math.max(0,  gray), 255);
    	
    	// conversion de gray en valeur rgb
    	return getRGB(gray, gray, gray);	// on renvoie la valeur de rgb avec r = g = b = gris (nuance de gris)
    }


    /**
    * Returns packed RGB components from a boolean value.
    * @param value a boolean
    * @return 32-bits RGB encoding of black if value is false
    * and encoding of white otherwise
    */
    public static int getRGB(boolean value) {
    	
    	// conversion de value en rgb 
        if(value) {
        	return 0xffffff;	// getRGB(255, 255, 255);
        } else {
        	return 0;	// getRGB(0, 0, 0);
        }
    }


    /**
     * Converts packed RGB image to grayscale image.
     * @param image a HxW int array
     * @return a HxW int array
     * @see #encode
     * @see #getGray
     */
    public static int[][] toGray(int[][] image) {
    	
    	// variables de la méthode :
        int height = image.length,
        	width = image[0].length; 
        int[][] gray = new int[height][width];		// On crée un tableau de la même taille que image
        
        // conversion de image en tableau de nuances de gris
        for(int i = 0; i < height; i++) {
        	for(int j = 0; j < width; j++) {
        		gray[i][j] = getGray(image[i][j]);		// On remplit le tableau de la nuance de gris correspondant à chaque pixel
        	}
        }
        
        return gray;
    }

    /**
     * Converts grayscale image to packed RGB image.
     * @param channels a HxW float array
     * @return a HxW int array
     * @see #decode
     * @see #getRGB(float)
     */
    public static int[][] toRGB(int[][] gray) {
    	
    	// variables de la méthode :
    	int height = gray.length,
    		width = gray[0].length; 
        int[][] image = new int[height][width];		// On crée un tableau de la même taille que gray
        
        // conversion de gray en tableau rgb de nuances de gris
        for(int i = 0; i < height; i++) {
        	for(int j = 0; j < width; j++) {
        		image[i][j] = getRGB(gray[i][j]);	// On remplit le tableau de la valeur rgb correspondant à chaque pixel
        	}
        }
        return image;
    }

    /**
     * Converts grayscale image to a black and white image using a given threshold
     * @param gray a HxW int array
     * @param threshold an integer threshold
     * @return a HxW int array
     */
    public static boolean[][] toBW(int[][] gray, int threshold) {
    	
    	// variables de la méthode :
    	int height = gray.length,
    		width = gray[0].length; 
        boolean[][] BW = new boolean[height][width];	// On crée un tableau de la même taille que gray
        
        // conversion de gray en tableau de booléens ("noir" ou "blanc")
        for(int i = 0; i < height; i++) {
        	for(int j = 0; j < width; j++) {
        		BW[i][j] = getBW(gray[i][j], threshold);	// On remplit le tableau du boolean correspondant à chaque nuance de gris
        	}
        }
        
        return BW;
    }

    /**
     * Converts a black and white image to packed RGB image
     * @param image a HxW boolean array (false stands for black)
     * @return a HxW int array
     */
    public static int[][] toRGB(boolean[][] image) {
    	
    	// variables de la méthode :
    	int height = image.length,
    		width = image[0].length; 
        int[][] RGB = new int[height][width];
        
        // conversion de image en tableau rgb de noir et blanc
        for(int i = 0; i < height; i++) {
        	for(int j = 0; j < width; j++) {
        		RGB[i][j] = getRGB(image[i][j]);	// On remplit le tableau de la valeur rgb (noir ou blanc) correspondant à chaque "pixel" booléen
        	}
        }
        
        return RGB;
    }

    /*
     * ********************************************
     * Part 3: prepare image message for spiral encoding (image <-> bit array)
     * ********************************************
     */
    /**
     * Converts a black-and-white image to a bit array
     * @param bwImage A black and white (boolean) image
     * @return A boolean array containing the binary representation of the image's height and width (32 bits each), followed by the image's pixel values
     * @see ImageMessage#bitArrayToImage(boolean[])
     */
    public static boolean[] bwImageToBitArray(boolean[][] bwImage) {
        //TODO: implement me!
        return null;
    }

    /**
     * Converts a bit array back to a black and white image
     * @param bitArray A boolean array containing the binary representation of the image's height and width (32 bits each), followed by the image's pixel values
     * @return The reconstructed image
     * @see ImageMessage#bwImageToBitArray(boolean[][])
     */
    public static boolean[][] bitArrayToImage(boolean[] bitArray) {
        //TODO: implement me!
        return null;
    }

}
