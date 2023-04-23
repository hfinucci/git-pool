import models.Ball;

import java.util.ArrayList;
import java.util.List;

public class FileGenerator {
    private static final float TABLE_WIDTH = 224;
    private static final float TABLE_HEIGHT = 112;
    private static final float REAL_RADIUS = 2.85f;
    private static final float HOLE_RADIUS = 2 * REAL_RADIUS;
    private static final float MAX_EPSILON = 0.015f;
    private static final float MIN_EPSILON = 0.01f;
    private static final float WHITE_X = 56;
    private static final float WHITE_Y = 56;

    private static final float INITIAL_SPEED = 200;

    private static final int MASS = 165;

    private static final float r = REAL_RADIUS + 0.015f;

    public static List<Ball> setupBalls() {
        List<Ball> balls = new ArrayList<>();

        balls.add(new Ball(WHITE_X, WHITE_Y, REAL_RADIUS, INITIAL_SPEED, 0, MASS, false));

        // for each row
        for(int rowNumber = 0; rowNumber < 5; rowNumber++) {
            float y = rowY(rowNumber);
            for(float x : rowXs(rowNumber)) {
                Ball ball = new Ball(x, y, REAL_RADIUS, MASS, false);
                balls.add(ball);
            }
        }

        for (Ball ball : balls) {
            if (ball.getId() != 0) {
                float aux = ball.getX();
                ball.setX(ball.getY());
                ball.setY(aux);
//                float random = Math.random();
//                float randomX = -MIN_EPSILON + random * (MAX_EPSILON + MIN_EPSILON);
//                float randomY = -MIN_EPSILON + random * (MAX_EPSILON + MIN_EPSILON);

                ball.setX(ball.getX() + 168);
                ball.setY(ball.getY() + 56);
            }
        }

        addHoles(balls);

        return balls;
    }

    private static void addHoles(List<Ball> balls) {
        balls.add(new Ball(0, TABLE_HEIGHT, HOLE_RADIUS, 0, true));
        balls.add(new Ball(TABLE_WIDTH/2, TABLE_HEIGHT, HOLE_RADIUS, 0, true));
        balls.add(new Ball(TABLE_WIDTH, TABLE_HEIGHT, HOLE_RADIUS, 0, true));
        balls.add(new Ball(0, 0, HOLE_RADIUS, 0, true));
        balls.add(new Ball(TABLE_WIDTH/2, 0, HOLE_RADIUS, 0, true));
        balls.add(new Ball(TABLE_WIDTH, 0, HOLE_RADIUS, 0, true));
    }

    // Returns the Y of a given row
    private static float rowY(int rowNumber) {
        return rowNumber * ((float) Math.sqrt(5) * r);
    }

    // Returns the list of the Xs for the balls on the given row
    private static float[] rowXs(int rowNumber) {
        switch (rowNumber) {
            case 0: return new float[] {0};
            case 1: return new float[] {-r, r};
            case 2: return new float[] {-2*r, 0, 2*r};
            case 3: return new float[] {-3*r, -r, r, 3*r};
            case 4: return new float[] {-4*r, -2*r, 0, 2*r, 4*r};
            default: throw new IllegalArgumentException("no more than 5 rows");
        }
    }

}
