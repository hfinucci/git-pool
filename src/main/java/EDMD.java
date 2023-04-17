import models.Ball;
import models.Collision;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class EDMD {

    private static final int CANT_BALLS = 15;

    public static void main(String[] args) {
        Collision imminentCollision;
//        List<Ball> balls = new ArrayList<>(CANT_BALLS);
        List<Ball> balls = FileGenerator.setupBalls();
        for (Ball ball : balls) {
            System.out.println(ball);
        }
        //PriorityQueue for the collisions
        PriorityQueue<Collision> collisions = new PriorityQueue<>();
    }
}
