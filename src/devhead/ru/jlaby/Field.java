package devhead.ru.jlaby;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Field {

	private String type;
	private String fileType = ".png";
	private ImageIcon imageIconBG; // back ground
	private ImageIcon imageIconMG; // middle ground
	private ImageIcon imageIconFG; // fore ground
	private boolean ant;
	private boolean obstacle;
	private boolean rock;
	public static int dim = 32;

	public Field(String type) {
		this.setType(type);
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
	    String filePathBG = null;
	    String filePathMG = null;
	    String filePathFG = null;
		ant = false;
		obstacle = false;
		rock = false;
		this.type = type;
		if (type.equals(".")) {
			filePathBG = Laby.IMAGES_PATH + "/void" + fileType;
		} else if (type.equals("o")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + fileType;
			filePathMG = "data/tiles/wall" + fileType;
			obstacle = true;
		} else if (type.equals("r")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + fileType;
			filePathFG = "data/tiles/rock" + fileType;
			obstacle = true;
			rock = true;
		} else if (type.equals("R")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + fileType;
			filePathMG = "data/tiles/nrock" + fileType;
		} else if (type.equals("w")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + fileType;
			filePathMG = "data/tiles/web" + fileType;
			obstacle = true;
		} else if (type.equals("W")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + fileType;
			filePathMG = "data/tiles/nweb" + fileType;
		} else if (type.equals("x")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + fileType;
			filePathMG = "data/tiles/exit" + fileType;
			obstacle = true;
		} else if (type.equals("↑")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + fileType;
			filePathMG = "data/tiles/ant-n" + fileType;
			ant = true;
		} else if (type.equals("↓")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + fileType;
			filePathMG = "data/tiles/ant-s" + fileType;
			ant = true;
		} else if (type.equals("←")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + fileType;
			filePathMG = "data/tiles/ant-w" + fileType;
			ant = true;
		} else if (type.equals("→")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + fileType;
			filePathMG = "data/tiles/ant-e" + fileType;
			ant = true;
		}
        if (filePathBG != null) {
            imageIconBG = new ImageIcon(filePathBG);
        } else {
            imageIconBG = null;
        }
        if (filePathMG != null) {
            imageIconMG = new ImageIcon(filePathMG);
        } else {
            imageIconMG = null;
        }
        if (filePathFG != null) {
            imageIconFG = new ImageIcon(filePathFG);
        } else {
            imageIconFG = null;
        }
	}

	/**
	 * Draw the Field
	 */
    public void draw(Component c, Graphics g, int x, int y) {
        if (imageIconBG != null) {
            imageIconBG.paintIcon(c, g, x, y);
        }
        if (imageIconMG != null) {
            imageIconMG.paintIcon(c, g, x, y);
        }
        if (imageIconFG != null) {
            imageIconFG.paintIcon(c, g, x, y);
        }
    }

	/**
	 * @return the ant
	 */
	public boolean isAnt() {
		return ant;
	}

	/**
	 * @return the rock
	 */
	public boolean isRock() {
		return rock;
	}
	
	/**
	 * @return the obstacle
	 */
	public boolean isObstacle() {
		return obstacle;
	}

}
