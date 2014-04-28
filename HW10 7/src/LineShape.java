import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Point;


/** A line that may be drawn on the canvas. */
public class LineShape implements Shape {
	
	private Color color;
	private Stroke stroke;
	private Point start;
	private Point end;
	
	 /** Construct a line, given its color, line thickness, and the two 
		  end points.

		  The constructor assumes that all parameters are nonnull.
	 */
	public LineShape(Color c, Stroke s, Point s0, Point e0) {
		color  = c;
		stroke = s;
		start  = s0;
		end    = e0;
	}
	
	public void draw(Graphics2D gc) {
		gc.setColor(color);
		gc.setStroke(stroke);
		gc.drawLine(start.x, start.y, end.x, end.y);
	}
	

}