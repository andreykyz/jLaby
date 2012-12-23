package devhead.ru.jlaby;

import javax.swing.ImageIcon;

public class Field {

	private String type;
	private String fileType = ".png";
	private String filePath;
	private ImageIcon imageIcon;
	public static int dim = 32;

	public Field(String type) {
		this.type = type;
		if (type.equals(".")) {
			filePath = "data/tiles/void" + fileType;
		} else if (type.equals("o")) {
			filePath = "data/tiles/wall" + fileType;
		} else if (type.equals("r")) {
			filePath = "data/tiles/rock" + fileType;
		} else if (type.equals("R")) {
			filePath = "data/tiles/nrock" + fileType;
		} else if (type.equals("w")) {
			filePath = "data/tiles/web" + fileType;
		} else if (type.equals("W")) {
			filePath = "data/tiles/nweb" + fileType;
		} else if (type.equals("x")) {
			filePath = "data/tiles/exit" + fileType;
		} else if (type.equals("↑")) {
			filePath = "data/tiles/ant-n" + fileType;
		} else if (type.equals("↓")) {
			filePath = "data/tiles/ant-s" + fileType;
		} else if (type.equals("←")) {
			filePath = "data/tiles/ant-w" + fileType;
		} else if (type.equals("→")) {
			filePath = "data/tiles/ant-e" + fileType;
		}
		imageIcon = new ImageIcon(filePath);
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
		this.type = type;
	}

	/**
	 * @return the image
	 */
	public ImageIcon getImageIcon() {
		return imageIcon;
	}

}
