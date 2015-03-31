import java.util.ArrayList;
import java.util.Random;

public class Square {

	private Placeble[][] placebles;
	private ArrayList<Placeble> cache = new ArrayList<Placeble>();
	private int squareWidth, squareHeight;

	public Square(int squareWidth, int squareHeight) {
		placebles = new Placeble[squareHeight][squareWidth];
		this.squareWidth = squareWidth;
		this.squareHeight = squareHeight;
	}

	public int getSquareWidth() {
		return this.squareWidth;
	}

	public int getSquareHeight() {
		return this.squareHeight;
	}

	public Placeble[][] getPlacebles() {
		return placebles;
	}

	public void placePlaceble(Placeble placeble, int startX, int startY,
			Direction direction) {
		try {
			switch (direction) {
			case EAST:
				for (int i = startY; i < startY + placeble.getHeight(); i++) {
					for (int j = startX; j < startX + placeble.getWidth(); j++) {
						placebles[i][j] = placeble;
					}
				}
				break;
			case SOUTH:
				for (int i = startY; i < startY + placeble.getWidth(); i++) {
					for (int j = startX; j < startX + placeble.getHeight(); j++) {
						placebles[i][j] = placeble;
					}
				}
			default:
				System.out.println("Use EAST or SOUTH next time");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Too little room for this placeble");
		}
	}

	public void deletePlaceble(Placeble placeble) {
		for (int i = 0; i < placebles.length; i++) {
			for (int j = 0; j < placebles[i].length; j++) {
				if (placebles[i][j].equals(placeble)) {
					placebles[i][j] = null;
				}
			}
		}
	}
	
	public void clear() {
		for (int i = 0; i < placebles.length; i++) {
			for (int j = 0; j < placebles[i].length; j++) {
				placebles[i][j] = null;
			}
		}
	}
	
	public void deleteAllPlaceblesOfType(Class<? extends Placeble> placebleClass) {
		for (int i = 0; i < placebles.length; i++) {
			for (int j = 0; j < placebles[i].length; j++) {
				if (placebles[i][j] != null && placebles[i][j].getClass() == placebleClass) {
					placebles[i][j] = null;
				}
			}
		}
	}
	
	public void refresh() {
		Placeble[][] placeblesBackUp = new Placeble[squareHeight][squareWidth];
		for (int i = 0; i < placebles.length; i++) {
			for (int j = 0; j < placebles[i].length; j++) {
				placeblesBackUp[i][j] = placebles[i][j];
			}
		}

		for (int i = 0; i < placebles.length; i++) {
			for (int j = 0; j < placebles[i].length; j++) {
				if (placebles[i][j] != null) {
					Position newPosition = placebles[i][j].update(
							placeblesBackUp, new Position(j, i));

					if (newPosition == null) {
						if (!placebles[i][j].mustBeCached()) {
							placebles[i][j] = null;
							placeblesBackUp[i][j] = null;
							continue;
						} else if (placebles[i][j].mustBeCached()) {
							cache.add(placebles[i][j]);
							placebles[i][j] = null;
							placeblesBackUp[i][j] = null;
							continue;
						}
					}

					int oldX = j;
					int oldY = i;
					int newX = newPosition.getX();
					int newY = newPosition.getY();

					//if (placebles[oldY][oldX].writeToLog()) {
					//	System.out
					//			.println("Old Position: " + oldX + "," + oldY);
					//	System.out
					//			.println("New position: " + newX + "," + newY);
					//}

					placeblesBackUp[newY][newX] = placebles[oldY][oldX];

					if ((oldX != newX || oldY != newY)
							&& (placeblesBackUp[oldY][oldX]
									.equals(placeblesBackUp[newY][newX]))) {
						placeblesBackUp[oldY][oldX] = null;
					}
				}
			}
		}

		for (int i = 0; i < cache.size(); i++) {
			Position pos = cache.get(i).update(placeblesBackUp, null);
			if (!cache.get(i).mustBeCached() && pos != null) {
				if (pos.isValid(placeblesBackUp) && placeblesBackUp[pos.getY()][pos.getX()] == null) {
					placeblesBackUp[pos.getY()][pos.getX()] = cache.get(i);
				}
				cache.remove(i);
			}
		}

		for (int i = 0; i < placeblesBackUp.length; i++) {
			for (int j = 0; j < placeblesBackUp[i].length; j++) {
				placebles[i][j] = placeblesBackUp[i][j];
			}
		}
	}

	public static Position findFreePlaceAroundPosition(Placeble[][] placebles,
			Position pos) {
		// Check if the given position is a valid place inside of the placebles
		if (!pos.isValid(placebles)) {
			System.out.println("Invalid position");
			return null;
		}

		if (pos.isFull(placebles)) {
			return pos;
		}

		ArrayList<Position> positionsPossible = new ArrayList<Position>();

		if (!new Position(pos.getX() + 1, pos.getY()).isFull(placebles))
			positionsPossible.add(new Position(pos.getX() + 1, pos.getY()));
		if (!new Position(pos.getX() - 1, pos.getY()).isFull(placebles))
			positionsPossible.add(new Position(pos.getX() - 1, pos.getY()));
		if (!new Position(pos.getX(), pos.getY() + 1).isFull(placebles))
			positionsPossible.add(new Position(pos.getX(), pos.getY() + 1));
		if (!new Position(pos.getX(), pos.getY() - 1).isFull(placebles))
			positionsPossible.add(new Position(pos.getX(), pos.getY() - 1));
		if (!new Position(pos.getX() - 1, pos.getY() - 1).isFull(placebles))
			positionsPossible.add(new Position(pos.getX() - 1, pos.getY() - 1));
		if (!new Position(pos.getX() + 1, pos.getY() - 1).isFull(placebles))
			positionsPossible.add(new Position(pos.getX() + 1, pos.getY() - 1));
		if (!new Position(pos.getX() - 1, pos.getY() + 1).isFull(placebles))
			positionsPossible.add(new Position(pos.getX() - 1, pos.getY() + 1));
		if (!new Position(pos.getX() + 1, pos.getY() + 1).isFull(placebles))
			positionsPossible.add(new Position(pos.getX() + 1, pos.getY() + 1));

		if (positionsPossible.isEmpty()) {
			return null;
		} else if (positionsPossible.size() == 1) {
			return positionsPossible.get(0);
		}

		int random = new Random().nextInt(positionsPossible.size() - 1);

		return positionsPossible.get(random);

	}
}