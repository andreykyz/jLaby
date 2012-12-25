package devhead.ru.jlaby;

import javax.swing.ImageIcon;

public class Field {

	private String type;
	private String fileType = ".png";
	private String filePath;
	private ImageIcon imageIcon;
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
		ant = false;
		obstacle = false;
		rock = false;
		this.type = type;
		if (type.equals(".")) {
			filePath = "data/tiles/void" + fileType;
		} else if (type.equals("o")) {
			filePath = "data/tiles/wall" + fileType;
		} else if (type.equals("r")) {
			filePath = "data/tiles/rock" + fileType;
			obstacle = true;
			rock = true;
		} else if (type.equals("R")) {
			filePath = "data/tiles/nrock" + fileType;
		} else if (type.equals("w")) {
			filePath = "data/tiles/web" + fileType;
			obstacle = true;
		} else if (type.equals("W")) {
			filePath = "data/tiles/nweb" + fileType;
		} else if (type.equals("x")) {
			filePath = "data/tiles/exit" + fileType;
			obstacle = true;
		} else if (type.equals("↑")) {
			filePath = "data/tiles/ant-n" + fileType;
			ant = true;
		} else if (type.equals("↓")) {
			filePath = "data/tiles/ant-s" + fileType;
			ant = true;
		} else if (type.equals("←")) {
			filePath = "data/tiles/ant-w" + fileType;
			ant = true;
		} else if (type.equals("→")) {
			filePath = "data/tiles/ant-e" + fileType;
			ant = true;
		}
		imageIcon = new ImageIcon(filePath);
	}

	/**
	 * @return the image
	 */
	public ImageIcon getImageIcon() {
		return imageIcon;
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
