package devhead.ru.jlaby;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Field {

	private String type;
	private ImageIcon imageIconBG; // back ground
	private ImageIcon imageIconMG; // middle ground
	private ImageIcon imageIconFG; // fore ground
	private boolean ant;
	private boolean obstacle;
	private boolean rock;
	private boolean web;
	final public static int width = 32;
	final public static int height = 32;
	
    public Field(String type) {
        ant = false;
        obstacle = false;
        rock = false;
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
		this.type = type;
		if (type.equals(".")) {
			filePathBG = Laby.IMAGES_PATH + "/void" + Laby.FILE_TYPE;
			 ant = false;
			 obstacle = false;
			 rock = false;
		} else if (type.equals("o")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + Laby.FILE_TYPE;
			filePathMG = "data/tiles/wall" + Laby.FILE_TYPE;
			obstacle = true;
		} else if (type.equals("r")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + Laby.FILE_TYPE;
			filePathFG = "data/tiles/rock" + Laby.FILE_TYPE;
			obstacle = true;
			rock = true;
		} else if (type.equals("R")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + Laby.FILE_TYPE;
			filePathMG = "data/tiles/nrock" + Laby.FILE_TYPE;
		} else if (type.equals("w")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + Laby.FILE_TYPE;
			filePathMG = "data/tiles/web" + Laby.FILE_TYPE;
			obstacle = false;
			web = true;
		} else if (type.equals("W")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + Laby.FILE_TYPE;
			filePathMG = "data/tiles/nweb" + Laby.FILE_TYPE;
		} else if (type.equals("x")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + Laby.FILE_TYPE;
			filePathMG = "data/tiles/exit" + Laby.FILE_TYPE;
			obstacle = true;
		} else if (type.equals("↑")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + Laby.FILE_TYPE;
			filePathMG = "data/tiles/ant-n" + Laby.FILE_TYPE;
			ant = true;
		} else if (type.equals("↓")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + Laby.FILE_TYPE;
			filePathMG = "data/tiles/ant-s" + Laby.FILE_TYPE;
			ant = true;
		} else if (type.equals("←")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + Laby.FILE_TYPE;
			filePathMG = "data/tiles/ant-w" + Laby.FILE_TYPE;
			ant = true;
		} else if (type.equals("→")) {
		    filePathBG = Laby.IMAGES_PATH + "/void" + Laby.FILE_TYPE;
			filePathMG = "data/tiles/ant-e" + Laby.FILE_TYPE;
			ant = true;
		}
        if (filePathBG != null) {
            imageIconBG = new ImageIcon(filePathBG);
        }
        if (filePathMG != null) {
            imageIconMG = new ImageIcon(filePathMG);
        }
        if (filePathFG != null) {
            imageIconFG = new ImageIcon(filePathFG);
        }
	}
	
    public void setFG(ImageIcon imageIconFG) {
        this.imageIconFG = imageIconFG;
    }

    public void setMG(ImageIcon imageIconMG) {
        this.imageIconMG = imageIconMG;
    }

    public void setBG(ImageIcon imageIconBG) {
        this.imageIconBG = imageIconBG;
    }

    public ImageIcon delFG() {
        ImageIcon delImg = imageIconFG;
        imageIconFG = null;
        return delImg;
    }

    public ImageIcon delMG() {
        ImageIcon delImg = imageIconMG;
        imageIconMG = null;
        return delImg;
    }

    public ImageIcon delBG() {
        ImageIcon delImg = imageIconBG;
        imageIconBG = null;
        return delImg;
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
     * @return the rock
     */
    public void setRock(boolean rock) {
        this.rock = rock;
        this.obstacle = rock;
    }
    
	
	/**
	 * @return the obstacle
	 */
	public boolean isObstacle() {
		return obstacle;
	}

    /**
     * @return the web
     */
    public boolean isWeb() {
        return web;
    }

    /**
     * @param web the web to set
     */
    public void setWeb(boolean web) {
        this.web = web;
    }

}
