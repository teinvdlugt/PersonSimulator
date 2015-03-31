import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;


@SuppressWarnings("serial")
public class Drawer extends JComponent {
	
	private Placeble[][] placebles;
	
	public void setPlacebles(Placeble[][] placebles) {
		this.placebles = placebles;
	}
	
	public void paint(Graphics g) {

		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		for (int i = 0; i < placebles.length; i++) {
			for (int j = 0; j < placebles[i].length; j++) {
				if (placebles[i][j] != null) {
					Placeble placeble = placebles[i][j];

					int startX = j * World.PIXELS_PER_BLOCK;
					int startY = i * World.PIXELS_PER_BLOCK;

					Shape rectangle = new Rectangle2D.Double(startX, startY,
							World.PIXELS_PER_BLOCK, World.PIXELS_PER_BLOCK);
					graphics.setPaint(placeble.getColor());
					graphics.draw(rectangle);
					graphics.setColor(placeble.getColor());
					graphics.fill(rectangle);
				}
			}
		}
	}
	
}
