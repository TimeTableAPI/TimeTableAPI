package pt.iscte.asd.projectn3.group11.models.util;

public class Operations {
    public static final LogicOperation AND = ((a, b) -> a && b);
    public static final LogicOperation OR = ((a, b) -> a || b);
}
