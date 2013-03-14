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
    private String levelPath;
    
	public LevelMap(String levelPath, JTextArea feedBack) {
	    this.feedBack = feedBack;
	    this.levelPath = levelPath;
	    LoadLevel(levelPath);
	    this.setMinimumSize(new Dimension(600,400));
	}
	
    public void reset() {
        LoadLevel(levelPath);
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
        say("I'm ready.");
        this.repaint();
	}
	
	public void paintComponent(Graphics g) {
        Dimension rv = null;
        rv = this.getSize(rv);
		for (int y = 0; y < dimension.height; y++) {
            for (int x = 0; x < dimension.width; x++) {
                levelMap.get(new Point(x, y)).draw(this, g, rv.width / 2 - dimension.width * Field.width / 2 + x * Field.width,
                        rv.height / 2 - dimension.height * Field.height / 2 + y * Field.height);
            }
		}
		if (end) {
            thumbUp.paintIcon(this, g,
                    (rv.width / 2) - (thumbUp.getIconWidth() / 2), (rv.height / 2) - (thumbUp.getIconHeight()) / 2);
		}
	}

	@Override
	public synchronized void right() {
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
	public synchronized void left() {
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
	public synchronized void forward() {
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
            say("I can't move anymore.");
        } else if (nextField.getType().equals("r")) {
            say("I can't go through this rock.");
        } else if (nextField.getType().equals("o")) {
            say("I can't go through this the wall.");
        } else if (nextField.getType().equals("x")) {
            say("I can't go through this the door.");
        } else if (nextField.isWeb()) {
            say("Oops, a spider web.");
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
    public synchronized void take() {
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
            say("I can't take one more rock.");
        } else if (!checkField.isRock()) {
            say("There's no rock to take here.");
        } else {
            antField.setRock(true);
            antField.setFG(checkField.delFG());
            checkField.setType(".");
        }
        this.repaint();
    }

    @Override
    public synchronized void drop() {
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
            say("I have nothing to drop.");
        } else if (checkField.isObstacle()){
            say("I can't drop the rock here.");
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
    public synchronized void escape() {
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
            say("I can't find a door to open.");
        } else if (antField.isRock()) {
            say("I can't go out carrying a rock.");
        } else {
            say("Wohoo, the exit!");
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

    @Override
    public Enum<tile> look() {
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
        if (checkField.getType().equals("r")) {
            return tile.Rock;
        }
        if (checkField.getType().equals("x")) {
            return tile.Exit;
        }
        if (checkField.getType().equals("o")) {
            return tile.Wall;
        }
        if (checkField.getType().equals("w")) {
            return tile.Web;
        }
        return tile.Void;
    }

    @Override
    public void say(String say) {
        feedBack.insert(say + "\n", 0);
        feedBack.select(0, 0);
    }

}
