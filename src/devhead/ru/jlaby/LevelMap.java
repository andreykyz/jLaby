package devhead.ru.jlaby;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class LevelMap extends JComponent implements Ant {

	private HashMap<Point, Field> levelMap;
	private Dimension dimension;
	private Point antPosition;
	private JTextArea feedBack;

	public LevelMap(String levelPath, JTextArea feedBack) {
	    this.feedBack = feedBack;
	    LoadLevel(levelPath);
	    this.setMinimumSize(new Dimension(600,400));
	}

	public void LoadLevel(String levelPath) {
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
						levelMap.put(point, new Field(mapLine[x]));
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
		for (int y = 0; y < dimension.height; y++) {
			for (int x = 0; x < dimension.width; x++) {
                levelMap.get(new Point(x, y)).draw(this, g, x * 32, y * 32);
			}
		}
	}

	@Override
	public void right() {
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
		if (nextField.getType().equals("r")) {
			feedBack.insert("I can't go through this rock.\n", 0);
			feedBack.select(0,0);
        } else if (nextField.getType().equals("o")) {
            feedBack.insert("I can't go through this the wall.\n", 0);
            feedBack.select(0,0);
        } else if (nextField.getType().equals("x")) {
            feedBack.insert("I can't go through this the door.\n", 0);
            feedBack.select(0,0);
        } else {
			levelMap.remove(nextPoint);
			levelMap.remove(antPosition);
			levelMap.put(antPosition, nextField);
			levelMap.put(nextPoint, antField);
			antPosition = nextPoint;
		}
		this.repaint();
	}

    @Override
    public void take() {
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
        } else {
            antField.setRock(false);
            antField.delFG();
            checkField.setType("r");
        }
        this.repaint();
    }

    @Override
    public void escape() {
        // TODO Auto-generated method stub
        
    }

}
