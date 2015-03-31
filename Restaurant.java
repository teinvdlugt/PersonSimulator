import java.awt.Color;


public class Restaurant implements Placeble {

	private int width, height;
	
	public Restaurant(){
		this.width = 8;
		this.height = 1;
	}
	
	public Restaurant(int width, int height) {
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
		return Color.GREEN;
	}

	@Override
	public Position update(Placeble[][] placebles, Position initialPosition) {
		// Nothing happens
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
		// A Restaurant is never cached
		return null;
	}
}
