import models.Ball;

import java.util.ArrayList;
import java.util.List;

public class FileGenerator {

    private static final double TABLE_WIDTH = 224;
    private static final double TABLE_HEIGHT = 112;
    private static final double REAL_RADIUS = 2.65;
    private static final double HOLE_RADIUS = 2 * REAL_RADIUS;
    private static final double MAX_EPSILON = 0.015;
    private static final double MIN_EPSILON = 0.01;
    private static final double WHITE_X = 56;
    private static final double WHITE_Y = 42;

    private static double r = REAL_RADIUS + 0.15;

    public static List<Ball> setupBalls() {
        List<Ball> balls = new ArrayList<>();

        balls.add(new Ball(WHITE_X, WHITE_Y, REAL_RADIUS, false));

        int ballId = 0;

        // for each row
        for(int rowNumber = 0; rowNumber < 5; rowNumber++) {
//            System.out.print("Row "+rowNumber + ": ");
            double y = rowY(rowNumber);
            for(double x : rowXs(rowNumber)) {
                Ball ball = new Ball(x, y, REAL_RADIUS, false);
                balls.add(ball);
//                System.out.print(ball + " ");
                ballId++;
            }
        }

        for (Ball ball : balls) {
            double aux = ball.getX();
            ball.setX(ball.getY());
            ball.setY(aux);

            double randomX = -MIN_EPSILON + Math.random() * (MAX_EPSILON + MIN_EPSILON);
            double randomY = -MIN_EPSILON + Math.random() * (MAX_EPSILON + MIN_EPSILON);

            if (ball.getId() != 0) {
                ball.setX(ball.getX() + randomX + 168);
                ball.setY(ball.getY() + randomY + 56);
            }
        }

        addHoles(balls);

        return balls;
    }

    private static void addHoles(List<Ball> balls) {
        balls.add(new Ball(0, TABLE_HEIGHT, HOLE_RADIUS, true));
        balls.add(new Ball(TABLE_WIDTH/2, TABLE_HEIGHT, HOLE_RADIUS, true));
        balls.add(new Ball(TABLE_WIDTH, TABLE_HEIGHT, HOLE_RADIUS, true));
        balls.add(new Ball(0, 0, HOLE_RADIUS, true));
        balls.add(new Ball(TABLE_WIDTH/2, 0, HOLE_RADIUS, true));
        balls.add(new Ball(TABLE_WIDTH, 0, HOLE_RADIUS, true));
    }

    // Returns the Y of a given row
    private static double rowY(int rowNumber) {
        return rowNumber * (Math.sqrt(5) * r);
    }

    // Returns the list of the Xs for the balls on the given row
    private static double[] rowXs(int rowNumber) {
        switch (rowNumber) {
            case 0: return new double[] {0};
            case 1: return new double[] {-r, r};
            case 2: return new double[] {-2*r, 0, 2*r};
            case 3: return new double[] {-3*r, -r, r, 3*r};
            case 4: return new double[] {-4*r, -2*r, 0, 2*r, 4*r};
            default: throw new IllegalArgumentException("no more than 5 rows");
        }
    }

}
