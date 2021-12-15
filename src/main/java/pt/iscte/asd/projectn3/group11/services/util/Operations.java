package pt.iscte.asd.projectn3.group11.services.util;

import pt.iscte.asd.projectn3.group11.services.util.LogicOperation;
@Deprecated
public class Operations {
    public static final LogicOperation AND = ((a, b) -> a && b);
    public static final LogicOperation OR = ((a, b) -> a || b);
}
