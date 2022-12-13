package util.math;

/*
 * 
 * Mathf
 * - fast inverse square root
 * 
 */

public class Mathf {
	
	// Copied from Internet
	public static float invSqrt(float x) {
	    float xhalf = 0.5f * x;
	    int i = Float.floatToIntBits(x);
	    i = 0x5f3759df - (i >> 1);
	    x = Float.intBitsToFloat(i);
	    x *= (1.5f - xhalf * x * x);
	    return x;
	}
	
}
