package main;
import java.util.ArrayList;

public class TextMessage {

    /*
     * ********************************************
     * Part 2a: prepare text message (text <-> bit array)
     * ********************************************
     */
	
	// création d'une variable globale qui spécifie le nombre de bits utilise pour les conversion
	// parce que Character.SIZE c'est trop long a écrire a chaque fois
	// et aussi pour éviter d'évaluer la valeur à chaque appel (et pas juste parce que Môssieur a la flemme ! ) 
	public static final int UTF = Character.SIZE;
		
    /**
     * Converts an integer to its binary representation
     * @param value The integer to be converted
     * @param bits The number of bits for {@code value}'s binary representation
     * @return A boolean array corresponding to {@code value}'s {@code bits}-bit binary representation
     */
    public static boolean[] intToBitArray(int value, int bits) {
    	
    	// variables de la méthode :
    	ArrayList<Integer> binaryValue = new ArrayList<Integer>();
    	boolean[] response = new boolean[bits];
    	int binValLen; //binaryValue length
    		
    	// conversion de value en tableau de binaires : 
    	binaryValue = intToBinaryArray(value);
    	binValLen = binaryValue.size();
    	
    	// conversion du tableau binaire en int et stockage en booléens :
    	for(int i = 0; i < bits; i++) {
    		// on change a true seulement si le binaire vaut 1
    		if(i < binValLen && binaryValue.get(i) == 1) {
    			response[i] = true;
    		}
    	}
    	
        return response;
    }

    /**
     * Converts a bit array to it's integer value
     * @param bitArray A boolean array corresponding to an integer's binary representation
     * @return The integer that the array represented
     */
    public static int bitArrayToInt(boolean[] bitArray) {
    	
    	// variables de la méthode :
        int response = 0,
        	bitArrayLen = bitArray.length;
        
        // on va ajouter la puissance de 2 correspondante a chaque 1 du nombre binaire
        for(int i = 0; i < bitArrayLen; i++) {
        	
        	// bitArray etant un tableau de boolean, on peut directement utiliser
        	// ses cases comme test pour if
        	if(bitArray[i]) {
        		// donc on arrive ici seulement si le contenu de la case traitée vaut true
        		// soit 1 dans notre nombre en binaire
        		// on calcule alors 2 puissance i, i correspondant a la position du 1
        		// dans le nombre binaire,
        		// grace a Math.pow(x,y), 
        		// fonction native de java, qui calcule x puissance y
        		response += Math.pow(2,i);
        	}
        }
        
        return response;
    }

    /**
     * Converts a String to its binary representation, i.e. the sequence of the 16-bit binary representations of its chars' integer values
     * @param message The String to be converted
     * @return A boolean array corresponding to the String's binary representation
     */
    public static boolean[] stringToBitArray(String message) {
    	
    	// variables de la méthode :
        int msgLen = message.length(),	// Le, est utilise comme diminutif pour 'length'
        	responseLen  = msgLen * UTF;		// response length
        boolean[] response  = new boolean[responseLen],
        	tempArray = new boolean[UTF];
        
        for(int i = 0; i < msgLen; i++) {
        	
        	// on place le i-eme char sous forme de tableau de booléens
        	// dans un array temporaire
        	tempArray = intToBitArray((int) message.charAt(i), UTF);
        	
        	// on ajoute le contenu du tableau temporaire dans le tableau final
        	for(int j = 0; j < UTF; j++) {
        		response[(i * UTF ) + j] = tempArray[j];
        		
        	}
        	
        	// puis on passe au char suivant
        	// note: dans response, les boolean seront inscrits par groupe de 16
        	// et chaque groupe aura la meme forme que la reponse de la fonction intToBitArray()
        }
        
        return response;
    }

    /**
     * Converts a boolean array to the String of which it is the representation
     * @param bitArray A boolean array representing a String
     * @return The String that the array represented
     * @see TextMessage#stringToBitArray(String)
     */
public static String bitArrayToString(boolean[] bitArray) {
    	
    	// variables de la méthode :
        String response = "";
        int strLen = bitArray.length/UTF;	// string length : longueur prévue de response
        boolean[] tempArray = new boolean[UTF];
        
        // on va faire plus ou moins l'operation inverse que pour stringToBitArray()
        // avec le même genre de boucle dans une boucle et pratiquement les mêmes variables
        // la 1ère boucle for va s'exécuter pour chaque char de la response attendue
        for(int i = 0; i < strLen; i++) {

        	// la 2ème boucle for va servir a extraire les bits 16 par 16
        	for(int j = 0; j < UTF; j++) {
        		tempArray[j] = bitArray[( i * UTF) + j];
        	}
        	
        	// on ajoute le char correspondant a response
        	// c'est la que l'ordre des bits de bitArray prend tout son sens :
        	// nous n'avons pas eu besoin de retrier les bits
        	response += (char) bitArrayToInt(tempArray);
        }
        
        return response;
    }
    
    /**
     * The following methods are those used above
     * and created by myself, the almighty 
     * Skeetis
     */
    public static ArrayList<Integer> intToBinaryArray(int val) {
    	
    	// variables de la méthode :
    	int division = val, 
    		modulo = val%2; 
    	ArrayList<Integer> binary = new ArrayList<Integer>();
    	
    	// on va diviser le nombre décimal par 2 autant que possible pour passer en binaire
    	do {    		
    		binary.add(modulo);

    		division /= 2;
    		modulo = division%2;
    	} while(!(division==0 && modulo==0));
    	
    	return binary;    	
    }
}