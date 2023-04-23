import models.Ball;
import models.Collision;
import utils.MathUtils;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class EDMD {

    private static final int CANT_BALLS = 22;

    public static void main(String[] args) {
        List<Ball> ballList = FileGenerator.setupBalls();
        Map<Ball, List<Double>> balls = new HashMap<>(CANT_BALLS);

        int [] balls_out = new int[CANT_BALLS];
        for (Ball ball : ballList){
            balls.put(ball, new ArrayList<>());
        }

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

        try {
            FileWriter myWriter = new FileWriter("src/main/resources/output_double_1.txt");
            PrintWriter printWriter = new PrintWriter(myWriter);

            printWriter.println(CANT_BALLS);

            double previoustime = 0;
            int gen = 0;

            while (balls_left > 0) {

                Collision collision = collisions.poll();
                if (collision == null)
                    throw new RuntimeException("No more collisions");

                if ((collision.getBall1() != null && balls.get(collision.getBall1()).stream().anyMatch(x -> x > collision.getTime()))
                        || (collision.getBall2() != null && balls.get(collision.getBall2()).stream().anyMatch(x -> x > collision.getTime()))
                        || (collision.getBall1() != null && balls_out[collision.getBall1().getId()] == 1)
                        || (collision.getBall2() != null && balls_out[collision.getBall2().getId()] == 1)
                )
                    continue;

                //update positions of balls
                for (Ball b : balls.keySet()) {
                    if (balls_out[b.getId()] == 1)
                        continue;
                    b.setX(MathUtils.newPositionX(b, collision.getTimeToCollision() - previoustime));
                    b.setY(MathUtils.newPositionY(b, collision.getTimeToCollision() - previoustime));
                }

                boolean bounce = true;

                if (collision.getBall1() == null) {
                    collision.getBall2().bounceY(collision.getTimeToCollision());
                } else if (collision.getBall2() == null)
                    collision.getBall1().bounceX(collision.getTime());
                else
                    bounce = collision.getBall1().bounce(collision.getBall2(), collision.getTimeToCollision());


                if (!bounce) {
                    balls_left--;
                    if (!collision.getBall1().isHole()) {
                        balls_out[collision.getBall1().getId()] = 1;
                        printWriter.println(printBallThatLeft(collision.getBall1(), collision.getBall2(), gen, collision.getTimeToCollision()));
                    }
                    if (!collision.getBall2().isHole()) {
                        balls_out[collision.getBall2().getId()] = 1;
                        printWriter.println(printBallThatLeft(collision.getBall2(), collision.getBall1(), gen, collision.getTimeToCollision()));
                    }
                } else {
                    //recalculate collisions for both balls and the rest
                    if (collision.getBall1() != null) {
                        balls.get(collision.getBall1()).add(collision.getTimeToCollision());
                        double timeX = collision.getBall1().collidesX();
                        double timeY = collision.getBall1().collidesY();

                        if (timeX >= 0)
                            collisions.add(new Collision(collision.getBall1(), null, collision.getTimeToCollision() + timeX, collision.getTimeToCollision()));

                        if (timeY >= 0)
                            collisions.add(new Collision(null, collision.getBall1(), collision.getTimeToCollision() + timeY, collision.getTimeToCollision()));

                        for (Ball b : balls.keySet()) {
                            if (b.getId() != collision.getBall1().getId()  && balls_out[b.getId()] == 0) {
                                double time = collision.getBall1().collides(b);
                                if (time >= 0)
                                    collisions.add(new Collision(collision.getBall1(), b, collision.getTimeToCollision() + time, collision.getTimeToCollision()));
                            }
                        }
                    }

                    if (collision.getBall2() != null) {
                        balls.get(collision.getBall2()).add(collision.getTimeToCollision());
                        double timeX = collision.getBall2().collidesX();
                        double timeY = collision.getBall2().collidesY();

                        if (timeX >= 0)
                            collisions.add(new Collision(collision.getBall2(), null, collision.getTimeToCollision() + timeX, collision.getTimeToCollision()));
                        if (timeY >= 0)
                            collisions.add(new Collision(null, collision.getBall2(), collision.getTimeToCollision() + timeY, collision.getTimeToCollision()));

                        for (Ball b : balls.keySet()) {
                            if (b.getId() != collision.getBall2().getId() && balls_out[b.getId()] == 0){
                                double time = collision.getBall2().collides(b);
                                if (time >= 0)
                                    collisions.add(new Collision(collision.getBall2(), b, collision.getTimeToCollision() + time, collision.getTimeToCollision()));
                            }
                        }
                    }
                }

                gen++;
                previoustime = collision.getTimeToCollision();
            }
            printWriter.println(previoustime);
            printWriter.close();
            myWriter.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String printBallThatLeft(Ball ball, Ball hole, int gen, double time) {
        StringBuilder sb = new StringBuilder();
        sb.append(gen).append("\n");
        sb.append(time).append("\n");
        sb.append(hole).append("\n");
        String sb_line = ball.getId() + "\t" +
                ball.getX() + "\t" +
                ball.getY() + "\t" +
                ball.getSpeedX() + "\t" +
                ball.getSpeedY() + "\t";
        sb.append(sb_line).append("\n");
        sb.append("\n");

        return sb.toString();
    }

    static String printBalls(List<Ball> balls, int[] balls_out, int gen, double time) {
        StringBuilder sb = new StringBuilder();
        sb.append(gen).append("\n");
        sb.append(time).append("\n");
        for (Ball ball : balls) {
            if (balls_out[ball.getId()] == 0) {
                String sb_line = ball.getId() + "\t" +
                        ball.getX() + "\t" +
                        ball.getY() + "\t" +
                        ball.getSpeedX() + "\t" +
                        ball.getSpeedY() + "\t" +
                        ball.getRadius() + "\t" +
                        ball.getMass() + "\t";
                sb.append(sb_line).append("\n");
            }
        }
        sb.append("\n");

        return sb.toString();
    }
}
