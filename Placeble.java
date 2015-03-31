import java.awt.Color;

public interface Placeble {
	
	// How much 'pixels' the Placeble will take
	public int getWidth();
	public int getHeight();
	
	// The color in which the Placeble will be 
	// represented on the Square
	public Color getColor();
	
	// If it can't move, for instance a Restaurant, 
	// return the initial position
	public Position update(Placeble[][] placebles, Position initialPosition);
	// If this returns null and mustBeCached returns false, the 
	// placeble must be deleted
	
	// When a Placeble moved and this is true,
	// in the console will be printed the initial position 
	// and the final position
	public boolean writeToLog();

	// For instance, when a Person got in to a 
	// Restaurant, the Person doesn't have a 
	// Position on the Square, but must not be 
	// deleted.
	public boolean mustBeCached();
	
	// The position from which the Placeble, if it has been
	// cached for some time, must resume when it comes out of
	// cache again
	public Position getCachedPosition();

}