import models.Ball;

import java.util.ArrayList;
import java.util.List;

public class FileGenerator {

    private static double REAL_RADIUS = 10;
    private static double r = REAL_RADIUS + 0.15;

    public static List<Ball> setupBalls() {
        List<Ball> balls = new ArrayList<>();
        int ballId = 0;

        // for each row
        for(int rowNumber = 0; rowNumber<5; rowNumber++) {
//            System.out.print("Row "+rowNumber + ": ");
            double y = rowY(rowNumber);
            for(double x : rowXs(rowNumber)) {
                Ball ball = new Ball(ballId, x, y, REAL_RADIUS);
                balls.add(ball);
//                System.out.print(ball + " ");
                ballId++;
            }
        }

        for (Ball ball : balls) {
            double aux = ball.getX();
            ball.setX(ball.getY());
            ball.setY(aux);

            double randomX = -0.1 + Math.random() * (0.15 + 0.1);
            double randomY = -0.1 + Math.random() * (0.15 + 0.1);

            if (ball.getId() != 0) {
                ball.setX(ball.getX() + randomX);
                ball.setY(ball.getY() + randomY);
            }
        }

        return balls;
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
