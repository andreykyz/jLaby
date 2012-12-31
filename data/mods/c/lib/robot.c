#define _GNU_SOURCE
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "robot.h"

void output(char *s) {
    printf("%s\n", s);
    fflush(stdout);
}

char *input() {
    char *line = NULL;
    size_t len = 0;
    getline(&line, &len, stdin);
    if (strcmp(line, "quit\n") == 0)
        exit(0);
    return line;
}

void left() {
    output("left");
    free(input());
}

void right() {
    output("right");
    free(input());
}

void forward() {
    output("forward");
    free(input());
}

void take() {
    output("take");
    free(input());
}

void drop() {
    output("drop");
    free(input());
}

void escape() {
    output("escape");
    free(input());
}

enum tile look() {
    output("look");
    char *s = input();
    enum tile answer = Unknown;
    if (strcmp(s, "void\n") == 0)
        answer = Void;
    if (strcmp(s, "wall\n") == 0)
        answer = Wall;
    if (strcmp(s, "rock\n") == 0)
        answer = Rock;
    if (strcmp(s, "web\n") == 0)
        answer = Web;
    if (strcmp(s, "exit\n") == 0)
        answer = Exit;
    free(s);
    return answer;
}

void say(char *s) {
    printf("say %s\n", s);
    fflush(stdout);
    free(input());
}

void ant();

int main() {
    output("start");
    free(input());
    ant();
    return 0;
}

