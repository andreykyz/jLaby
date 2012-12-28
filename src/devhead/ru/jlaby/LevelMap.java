package devhead.ru.jlaby;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class LevelMap extends JComponent implements Ant {

	private HashMap<Point, Field> levelMap;
	private Dimension dimension;
	private Point antPosition;
	private JTextArea feedBack;
	private boolean end;
	private static ImageIcon thumbUp = new ImageIcon(Laby.IMAGES_PATH + "/ThumbsUp" + Laby.FILE_TYPE);
	   
	public LevelMap(String levelPath, JTextArea feedBack) {
	    this.feedBack = feedBack;
	    LoadLevel(levelPath);
	    this.setMinimumSize(new Dimension(600,400));
	}

	public void LoadLevel(String levelPath) {
	    boolean oneWeb = false;
	    boolean oneRock = false;
	    end = false;
		levelMap = new HashMap<Point, Field>();
		dimension = new Dimension(0, 0);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(levelPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		String flag = "";
		try {
			while ((line = reader.readLine()) != null) {
				if (line.contains("#")) {
					continue;
				} else if (line.equalsIgnoreCase("Map:")) {
					flag = "map";
					continue;
				} else if (line.equals("")) {
					flag = "";
					continue;
				}
				if (flag.equals("map")) {
					String mapLine[] = line.split(" ");
					int x;
					for (x = 0; x < mapLine.length; x++) {
						Point point = new Point(x, dimension.height);
						if (mapLine[x].equals("R")) {
						    if (oneRock) {
						        levelMap.put(point, new Field("r"));
						    } else {
						        levelMap.put(point, new Field("R"));
	                            oneRock = true;
						    }
						} if (mapLine[x].equals("W")) {
						    if (oneWeb) {
						        levelMap.put(point, new Field("w"));
						    } else {
						        levelMap.put(point, new Field("W"));
	                            oneWeb = true;
						    }
                        } else {
						    levelMap.put(point, new Field(mapLine[x]));
						}
						if (levelMap.get(point).isAnt()) {
							antPosition = point;
						}
					}
					if (dimension.width < mapLine.length) {
						dimension.width = mapLine.length;
					}
					dimension.height++;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        feedBack.insert("I'm ready.\n", 0);
        feedBack.select(0,0);
        this.repaint();
	}
	
	public void paintComponent(Graphics g) {
        Dimension rv = null;
        rv = this.getSize(rv);
		for (int y = 0; y < dimension.height; y++) {
			for (int x = 0; x < dimension.width; x++) {
                levelMap.get(new Point(x, y)).draw(this, g, x * Field.width, y * Field.height);
			}
		}
		if (end) {
            thumbUp.paintIcon(this, g,
                    (rv.width / 2) - (thumbUp.getIconWidth() / 2), (rv.height / 2) - (thumbUp.getIconHeight()) / 2);
		}
	}

	@Override
	public void right() {
	    if (end) return;
		Field antField = levelMap.get(antPosition);
		if (antField.getType().equals("↑")) {
			antField.setType("→");
		} else if (antField.getType().equals("↓")) {
			antField.setType("←");
		} else if (antField.getType().equals("←")) {
			antField.setType("↑");
		} else if (antField.getType().equals("→")) {
			antField.setType("↓");
		}
		this.repaint();
	}

	@Override
	public void left() {
	    if (end) return;
		Field antField = levelMap.get(antPosition);
		if (antField.getType().equals("↑")) {
			antField.setType("←");
		} else if (antField.getType().equals("↓")) {
			antField.setType("→");
		} else if (antField.getType().equals("←")) {
			antField.setType("↓");
		} else if (antField.getType().equals("→")) {
			antField.setType("↑");
		}
		this.repaint();
	}
	
/**
 * Calculate next position and is doing step if possible.
 */
	@Override
	public void forward() {
	    if (end) return;
		Field antField = levelMap.get(antPosition);
		Point nextPoint = null;
/*      ------------> x
 *      | 
 *      |    O_o
 *      |
 *     \/ y
 */
		if (antField.getType().equals("→")) {
			nextPoint = new Point(antPosition.x + 1, antPosition.y);
		} else if (antField.getType().equals("←")) {
			nextPoint = new Point(antPosition.x - 1, antPosition.y);
		} else if (antField.getType().equals("↑")) {
			nextPoint = new Point(antPosition.x, antPosition.y - 1);
		} else if (antField.getType().equals("↓")) {
			nextPoint = new Point(antPosition.x, antPosition.y + 1);
		}
		Field nextField = levelMap.get(nextPoint);
        if (antField.isWeb()) {
            feedBack.insert("I can't move anymore.\n", 0);
            feedBack.select(0,0);
        } else if (nextField.getType().equals("r")) {
			feedBack.insert("I can't go through this rock.\n", 0);
			feedBack.select(0,0);
        } else if (nextField.getType().equals("o")) {
            feedBack.insert("I can't go through this the wall.\n", 0);
            feedBack.select(0,0);
        } else if (nextField.getType().equals("x")) {
            feedBack.insert("I can't go through this the door.\n", 0);
            feedBack.select(0,0);
        } else if (nextField.isWeb()) {
            feedBack.insert("Oops, a spider web.", 0);
            feedBack.select(0,0);
            antField.setWeb(true);
            antField.setFG(nextField.delMG());
            nextField.setType(".");
            levelMap.remove(nextPoint);
            levelMap.remove(antPosition);
            levelMap.put(antPosition, nextField);
            levelMap.put(nextPoint, antField);
            antPosition = nextPoint;
        } else {
			levelMap.remove(nextPoint);
			levelMap.remove(antPosition);
			levelMap.put(antPosition, nextField);
			levelMap.put(nextPoint, antField);
			nextField.delMG();
			antPosition = nextPoint;
		}
		this.repaint();
	}

    @Override
    public void take() {
        if (end) return;
        Field antField = levelMap.get(antPosition);
        Point checkPosition = null;
        if (antField.getType().equals("→")) {
            checkPosition = new Point(antPosition.x + 1, antPosition.y);
        } else if (antField.getType().equals("←")) {
            checkPosition = new Point(antPosition.x - 1, antPosition.y);
        } else if (antField.getType().equals("↑")) {
            checkPosition = new Point(antPosition.x, antPosition.y - 1);
        } else if (antField.getType().equals("↓")) {
            checkPosition = new Point(antPosition.x, antPosition.y + 1);
        }
        Field checkField = levelMap.get(checkPosition);
        if (antField.isRock()){
            feedBack.insert("I can't take one more rock.\n", 0);
            feedBack.select(0,0);
        } else if (!checkField.isRock()) {
            feedBack.insert("There's no rock to take here.\n", 0);
            feedBack.select(0,0);
        } else {
            antField.setRock(true);
            antField.setFG(checkField.delFG());
            checkField.setType(".");
        }
        this.repaint();
    }

    @Override
    public void drop() {
        if (end) return;
        Field antField = levelMap.get(antPosition);
        Point checkPosition = null;
        if (antField.getType().equals("→")) {
            checkPosition = new Point(antPosition.x + 1, antPosition.y);
        } else if (antField.getType().equals("←")) {
            checkPosition = new Point(antPosition.x - 1, antPosition.y);
        } else if (antField.getType().equals("↑")) {
            checkPosition = new Point(antPosition.x, antPosition.y - 1);
        } else if (antField.getType().equals("↓")) {
            checkPosition = new Point(antPosition.x, antPosition.y + 1);
        }
        Field checkField = levelMap.get(checkPosition);
        if (!antField.isRock()) {
            feedBack.insert("I have nothing to drop.\n", 0);
            feedBack.select(0, 0);
        } else if (checkField.isObstacle()){
            feedBack.insert("I can't drop the rock here.\n", 0);
            feedBack.select(0, 0);
        } else if (checkField.isWeb()) {
            antField.setRock(false);
            checkField.setWeb(false);
            antField.delFG();
            checkField.setType("W");
            checkField.setType("r");
        } else {
            antField.setRock(false);
            antField.delFG();
            checkField.setType("r");
        }
        this.repaint();
    }

    @Override
    public void escape() {
        if (end) return;
        Field antField = levelMap.get(antPosition);
        Point checkPosition = null;
        if (antField.getType().equals("→")) {
            checkPosition = new Point(antPosition.x + 1, antPosition.y);
        } else if (antField.getType().equals("←")) {
            checkPosition = new Point(antPosition.x - 1, antPosition.y);
        } else if (antField.getType().equals("↑")) {
            checkPosition = new Point(antPosition.x, antPosition.y - 1);
        } else if (antField.getType().equals("↓")) {
            checkPosition = new Point(antPosition.x, antPosition.y + 1);
        }
        Field checkField = levelMap.get(checkPosition);
        if (!checkField.getType().equals("x")) {
            feedBack.insert("I can't find a door to open.\n", 0);
            feedBack.select(0, 0);
        } else if (antField.isRock()) {
            feedBack.insert("I can't go out carrying a rock.\n", 0);
            feedBack.select(0, 0);
        } else {
            feedBack.insert("Wohoo, the exit!\n", 0);
            feedBack.select(0, 0);
            levelMap.remove(checkPosition);
            levelMap.remove(antPosition);
            levelMap.put(antPosition, checkField);
            levelMap.put(checkPosition, antField);
            antField.setFG(antField.delMG());
            antField.setMG(checkField.delMG());
            antPosition = checkPosition;
            end = true;
        }
        this.repaint();
    }

}
