import models.Ball;
import models.Collision;
import utils.MathUtils;

import java.util.*;
import java.util.stream.Collectors;

public class EDMD {

    private static final int CANT_BALLS = 22;
    private static final double RADIUS = 5.7/2;
    private static final double INITIAL_SPEED = 200;
    private static final int MASS = 165;

    public static void main(String[] args) {
        Collision imminentCollision;
//        List<Ball> balls = new ArrayList<>(CANT_BALLS);
//        List<Ball> b = FileGenerator.setupBalls();
        Map<Ball, List<Double>> balls = new HashMap<>(CANT_BALLS);
        balls.put(new Ball(56, 56, RADIUS, INITIAL_SPEED, 0, MASS, false), new ArrayList<>());
        balls.put(new Ball(66, 56, RADIUS, 0, 0, MASS, false), new ArrayList<>());


        //init balls
//        for (int i = 0; i < CANT_BALLS - 6; i++) {
//            if(i == 0)
//                balls.put(new Ball(56, 56, RADIUS, INITIAL_SPEED, 0, MASS, false), new ArrayList<>());
//            else
//                balls.put(new Ball(56 +  i*10, 56, RADIUS, 0, 0, MASS, false), new ArrayList<>());
//        }
//
//        balls.put(new Ball(0, 0, RADIUS, 0, 0, 0, true), new ArrayList<>());
//        balls.put(new Ball(112, 0, RADIUS, 0, 0, 0, true), new ArrayList<>());
//        balls.put(new Ball(224, 0, RADIUS, 0, 0, 0, true), new ArrayList<>());
//        balls.put(new Ball(0, 112, RADIUS, 0, 0, 0, true), new ArrayList<>());
//        balls.put(new Ball(112, 112, RADIUS, 0, 0, 0, true), new ArrayList<>());
//        balls.put(new Ball(224, 112, RADIUS, 0, 0, 0, true), new ArrayList<>());

        //PriorityQueue for the collisions
        PriorityQueue<Collision> collisions = new PriorityQueue<>();
        int balls_left = CANT_BALLS - 6;

        //init collisions
        for (Ball b1 : balls.keySet()) {
            double timeX = b1.collidesX();
            double timeY = b1.collidesY();

            if(timeX >= 0)
                collisions.add(new Collision(b1, null, timeX, 0));
            if(timeY >= 0)
                collisions.add(new Collision(null, b1, timeY, 0));

            for (Ball b2 : balls.keySet()) {
                if(b1.getId() < b2.getId()) {
                    double time = b1.collides(b2);
                    if(time >= 0)
                        collisions.add(new Collision(b1, b2, time, 0));
                }
            }
        }



        while (balls_left > 0) {
            Collision collision = collisions.poll();

            if((collision.getBall1() != null && balls.get(collision.getBall1()).stream().anyMatch(x -> x > collision.getTime())) || (collision.getBall2() != null && balls.get(collision.getBall2()).stream().anyMatch(x -> x > collision.getTime())))
                continue;

            System.out.println(collision);

            //update positions of balls
            for (Ball b : balls.keySet()) {
                b.setX(MathUtils.newPositionX(b, collision.getTimeToCollision()));
                b.setY(MathUtils.newPositionY(b, collision.getTimeToCollision()));
            }

            if(collision.getBall1() == null) {
                collision.getBall2().bounceY(collision.getTimeToCollision());
            }
            else if(collision.getBall2() == null)
                collision.getBall1().bounceX(collision.getTime());
            else
                collision.getBall1().bounce(collision.getBall2(), collision.getTimeToCollision());


            //recalculate collisions for both balls and the rest
            if(collision.getBall1() != null) {
                balls.get(collision.getBall1()).add(collision.getTimeToCollision());
                for (Ball b : balls.keySet()) {
                    if (b.getId() != collision.getBall1().getId()) {
                        double time = collision.getBall1().collides(b);
                        if (time >= 0)
                            collisions.add(new Collision(collision.getBall1(), b, time, collision.getTimeToCollision()));

                        double timeX = collision.getBall1().collidesX();
                        double timeY = collision.getBall1().collidesY();

                        if (timeX >= 0)
                            collisions.add(new Collision(collision.getBall1(), null, timeX, collision.getTimeToCollision()));
                        if (timeY >= 0)
                            collisions.add(new Collision(null, collision.getBall1(), timeY, collision.getTimeToCollision()));
                    }
                }
            }

            if(collision.getBall2() != null) {
                balls.get(collision.getBall2()).add(collision.getTime());
                for (Ball b : balls.keySet()) {
                    if (b.getId() != collision.getBall2().getId()) {
                        double time = collision.getBall2().collides(b);
                        if (time >= 0)
                            collisions.add(new Collision(collision.getBall2(), b, time, collision.getTimeToCollision()));

                        double timeX = collision.getBall2().collidesX();
                        double timeY = collision.getBall2().collidesY();

                        if (timeX >= 0)
                            collisions.add(new Collision(collision.getBall2(), null, timeX, collision.getTimeToCollision()));
                        if (timeY >= 0)
                            collisions.add(new Collision(null, collision.getBall2(), timeY, collision.getTimeToCollision()));
                    }
                }
            }

            balls_left --;
        }




    }
}
