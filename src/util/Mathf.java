package util;

public class Mathf {
	
	public static float invSqrt(float x) {
	    float xhalf = 0.5f * x;
	    int i = Float.floatToIntBits(x);
	    i = 0x5f3759df - (i >> 1);
	    x = Float.intBitsToFloat(i);
	    x *= (1.5f - xhalf * x * x);
	    return x;
	}
	
	public static Vector2 round(Vector2 vec) {
		return new Vector2(
				Math.round(vec.x),
				Math.round(vec.y));
	}
	
}