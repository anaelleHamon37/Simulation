package simulation;

import java.util.Random;

public class Probabilites {

    private static Random rand = new Random();
	
	public static int uniforme(int min, int max){
	    return rand.nextInt(max-min+1)+min;
	}
	
	public static int exponentielle(int esperance){
	   	return (int)Math.round(-esperance * Math.log( 1 - rand.nextDouble() ));
	}
}
