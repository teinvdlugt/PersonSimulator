import java.awt.Color;


public class Hotel implements Placeble {

	private int width, height;
	
	public Hotel() {
		this.width = 8;
		this.height = 2;
	}
	
	public Hotel(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Color getColor() {
		return Color.RED;
	}

	@Override
	public Position update(Placeble[][] placebles, Position initialPosition) {
		return initialPosition;
	}

	@Override
	public boolean writeToLog() {
		return false;
	}

	@Override
	public boolean mustBeCached() {
		return false;
	}

	@Override
	public Position getCachedPosition() {
		// A Hotel is never cached
		return null;
	}

}
