import models.Ball;
import models.Collision;
import utils.MathUtils;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class EDMD {

    private static final int CANT_BALLS = 22;
    private static final double RADIUS = 5.7/2;



    public static void main(String[] args) {
        Collision imminentCollision;
//        List<Ball> balls = new ArrayList<>(CANT_BALLS);
        List<Ball> ballList = FileGenerator.setupBalls();
        Map<Ball, List<Double>> balls = new HashMap<>(CANT_BALLS);

//        balls.put(new Ball(56, 56, RADIUS, 200, 0, 165, false), new ArrayList<>());
//        balls.put(new Ball(66, 56, RADIUS, 0, 0, 165, false), new ArrayList<>());

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
            FileWriter myWriter = new FileWriter("src/resources/output2.txt");
            PrintWriter printWriter = new PrintWriter(myWriter);
            printWriter.println(CANT_BALLS - 6);
            double previoustime = 0;

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


                if(ballList.get(14).getId() == 14 && ballList.get(14).getX() == 187.10364322763886)
                    System.out.println("asd");

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
                    if (!collision.getBall1().isHole())
                        balls_out[collision.getBall1().getId()] = 1;
                    if (!collision.getBall2().isHole())
                        balls_out[collision.getBall2().getId()] = 1;
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
                        balls.get(collision.getBall2()).add(collision.getTime());
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

                printWriter.println(printBalls(new ArrayList<>(balls.keySet()), balls_out, collision));

                previoustime = collision.getTimeToCollision();

            }

            printWriter.close();
            myWriter.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    static String printBalls(List<Ball> balls, int[] balls_out, Collision collision) {
        StringBuilder sb = new StringBuilder();
        sb.append(collision.getTimeToCollision()).append("\n");
        sb.append(collision.getBall1()).append("\t");
        sb.append(collision.getBall2()).append("\n");
        for (int i = 0; i < balls.size(); i++) {
            StringBuilder sb_line = new StringBuilder();
            sb_line.append(balls.get(i).getId()).append("\t");
            sb_line.append(balls.get(i).getX()).append("\t");
            sb_line.append(balls.get(i).getY()).append("\t");
//            sb_line.append(balls.get(i).getSpeedX()).append("\t");
//            sb_line.append(balls.get(i).getSpeedY()).append("\t");
            sb_line.append(balls_out[balls.get(i).getId()] == 0? "IN" : "OUT");
            sb.append(sb_line).append("\n");


        }
        sb.append("\n");

        return sb.toString();
    }
}
