package util;

/*
 * 
 * Vector2f
 * - a 2D float vector
 * 
 */

public class Vector2f implements Comparable<Vector2f> {
	
	public float x, y;
	
	/*
	 * OVERLOADING Constructor
	 */
	public Vector2f() {
		this(0.f, 0.f);
	}
	
	public Vector2f(Vector2f vec) {
		this(vec.x, vec.y);
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(float angle) {
		this.x = (float) Math.cos(angle / 180.f * Math.PI);
		this.y = (float) Math.sin(angle / 180.f * Math.PI);
	}
	
	/*
	 * Addition
	 */
	public Vector2f addEqual(Vector2f other) {
		x += other.x;
		y += other.y;
		
		return this;
	}
	
	public Vector2f add(Vector2f other) {
		return new Vector2f(x + other.x, y + other.y);
	}
	
	public Vector2f add(float other) {
		return new Vector2f(x + other, y + other);
	}
	
	/*
	 * Substraction 
	 */
	public Vector2f subtractEqual(Vector2f other) {
		x -= other.x;
		y -= other.y;
		
		return this;
	}
	
	public Vector2f subtract(Vector2f other) {
		return new Vector2f(x - other.x, y - other.y);
	}
	
	public Vector2f subtract(float other) {
		return new Vector2f(x - other, y - other);
	}
	
	/*
	 * Multiplication
	 */
	public Vector2f multiplyEqual(float other) {
		x *= other;
		y *= other;
		
		return this;
	}
	
	public Vector2f multiply(float other) {
		return new Vector2f(x * other, y * other);
	}
	
	/*
	 * Vector operations
	 */
	public float getSize() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public Vector2f normalize() {
		float amount = x * x + y * y;
		
		// Copied from Internet
		float xhalf = 0.5f * amount;
	    int i = Float.floatToIntBits(amount);
	    i = 0x5f3759df - (i >> 1);
	    amount = Float.intBitsToFloat(i);
	    amount *= (1.5f - xhalf * amount * amount);
		
		x *= amount;
		y *= amount;
		
		return this;
	}
	
	public void setZero() {
		x = 0;
		y = 0;
	}
	
	public Vector2f round() {
		return new Vector2f(Math.round(x), Math.round(y));
	}
	
	@Override
	public String toString() {
		return String.format("[%.2f %.2f]", x, y);
	}

	@Override
	public int compareTo(Vector2f other) {
		return Float.compare(getSize(), other.getSize());
	}
	
	public boolean isZero() {
		return (x == 0.f && y == 0.f);
	}
	
	/*
	 * Vector direction
	 * - is 'other' Vector2f is in certain directions
	 */
	public boolean isInNorth(Vector2f other) {
		float sy = Math.abs(other.y - y);
		float sx = Math.abs(other.x - x);
		return other.y < y && sy >= sx;
	}
	
	public boolean isInSouth(Vector2f other) {
		float sy = Math.abs(other.y - y);
		float sx = Math.abs(other.x - x);
		return other.y > y && sy >= sx;
	}
	
	public boolean isInWest(Vector2f other) {
		float sy = Math.abs(other.y - y);
		float sx = Math.abs(other.x - x);
		return other.x < x && sx >= sy;
	}
	
	public boolean isInEast(Vector2f other) {
		float sy = Math.abs(other.y - y);
		float sx = Math.abs(other.x - x);
		return other.x > x && sx >= sy;
	}
	
	public boolean[] getDirection(Vector2f other) {
		return new boolean[] {
			isInNorth(other),
			isInEast(other),
			isInSouth(other),
			isInWest(other)
		};
	}
	
	public boolean[] toDirection() {
		return new boolean[] {
			y < 0.f,
			x > 0.f,
			y > 0.f,
			x < 0.f
		};
	}
	
}
