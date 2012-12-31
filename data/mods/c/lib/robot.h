enum tile {
    Void, Wall, Rock, Web, Exit, Unknown
};

void left();
void right();
void forward();
void escape();
void take();
void drop();
enum tile look();
void say(char *s);
