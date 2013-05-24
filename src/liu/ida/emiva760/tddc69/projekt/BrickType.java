package liu.ida.emiva760.tddc69.projekt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum BrickType {
    NORMAL,
    EXPLOSIVE,
    SOLID;

    private static final List<BrickType> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static BrickType randomBrickType() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
