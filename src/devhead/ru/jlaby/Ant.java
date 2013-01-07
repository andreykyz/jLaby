package devhead.ru.jlaby;

/**
 * 
 * @author Kuznetsov Andrey
 *
 */

public interface Ant {

    enum tile {
        Void, Wall, Rock, Web, Exit, Unknown
    };
    
	public void right();

	public void left();

	public void forward();

	public void take();

	public void drop();
	
	public Enum<tile> look();
	
	public void say(String say);
	
	public void escape();

}
