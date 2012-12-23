package devhead.ru.jlaby;

import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class LevelMap extends JComponent {

	private HashMap<Point, Field> levelMap;

	public LevelMap() {

	}

	public void LoadLevel(String levelPath) {
		levelMap = new HashMap<Point, Field>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(levelPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		String flag = "";
		int y = 0;
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
					for (int x = 0; x < mapLine.length; x++) {
						levelMap.put(new Point(x, y), new Field(mapLine[x]));
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void paintComponent(Graphics g) {
		
	}
}
