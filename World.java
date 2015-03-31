import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class World extends JFrame implements Runnable {
	public static void main(String[] args) {
		World world = new World();
		new Thread(world).start();
		
		Shell shell = new Shell(world);
		new Thread(shell).start();
	}
	
	public static final int PIXELS_PER_BLOCK = 2;
	
	private Square square;
	private Drawer drawer;
	private int sleepTime = 10;
	
	public Square getSquare() {
		return square;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	public Drawer getDrawer() {
		return drawer;
	}

	public void setDrawer(Drawer drawer) {
		this.drawer = drawer;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public World(){
		square = new Square(400, 400);
		
		// Place people
		for(int i = 0; i < 100; i++) {
			Random generator = new Random();
			Person.Gender gender = Person.Gender.values()[generator.nextInt(2)];
			int x = generator.nextInt(square.getSquareWidth());
			int y = generator.nextInt(square.getSquareHeight());
			square.placePlaceble(new Person(gender), x, y, Direction.EAST);
		}
		
		// Place buildings
		square.placePlaceble(new Hotel(100,100), 220, 250, Direction.SOUTH);
		square.placePlaceble(new Restaurant(100,20), 379, 160, Direction.SOUTH);
		square.placePlaceble(new Hotel(50,20), 50, 50, Direction.SOUTH);
		square.placePlaceble(new Restaurant(90,20), 3, 379, Direction.EAST);
		square.placePlaceble(new Restaurant(90,20), 300, 200, Direction.EAST);
		square.placePlaceble(new Restaurant(90,20), 30, 260, Direction.EAST);
		square.placePlaceble(new Restaurant(90,20), 350, 350, Direction.EAST);
		square.placePlaceble(new Restaurant(90,20), 24, 340, Direction.EAST);
		
		this.setSize(square.getSquareWidth() * PIXELS_PER_BLOCK + 20, square.getSquareHeight() * PIXELS_PER_BLOCK + 40);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Square");
		drawer = new Drawer();
		drawer.setPlacebles(square.getPlacebles());
		this.add(drawer, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	// The following method just makes sure the Shell can't 
	// modify the Placeble Array in Square while World is iterating
	// through it.
	public synchronized void doStuff(String input) {

		if (input.equalsIgnoreCase("refresh") || input.equalsIgnoreCase("")) {
			getSquare().refresh();
		} else if (input.equalsIgnoreCase("exit")) {
			System.exit(0);
		} else if (input.equalsIgnoreCase("delete restaurants")) {
			getSquare().deleteAllPlaceblesOfType(Restaurant.class);
			System.out.println("All Restaurants deleted");
		} else if (input.equalsIgnoreCase("delete hotels")) {
			getSquare().deleteAllPlaceblesOfType(Hotel.class);
			System.out.println("All Hotels deleted");
		} else if (input.equalsIgnoreCase("delete fences")) {
			getSquare().deleteAllPlaceblesOfType(Fence.class);
			System.out.println("All Fences deleted");
		} else if (input.equalsIgnoreCase("delete people")) {
			getSquare().deleteAllPlaceblesOfType(Person.class);
			System.out.println("All People deleted");
		} else if (input.equalsIgnoreCase("clear")) {
			getSquare().clear();
			System.out.println("Square cleared");
		} else if (input.startsWith("set sleep time to ")) {
			try {
				char[] sleepTimeStr = new char[input.length() - 18];
				input.getChars(18, input.length(), sleepTimeStr, 0);
				
				int newSleepTime = Integer.parseInt(String.valueOf(sleepTimeStr));
				setSleepTime(newSleepTime);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
			}
		}

		drawer.setPlacebles(square.getPlacebles());
		drawer.repaint();

	}

	@Override
	public void run() {
		while (1 + 1 == 2) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException ignored) {}
			
			doStuff("refresh");
		}
	}
}

