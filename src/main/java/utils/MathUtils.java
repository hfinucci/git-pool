package utils;

import models.Ball;
import models.Wall;

public class MathUtils {
    //TODO: estan todas mal, ignorarlas

    public static Tuple deltaR(Ball b1, Ball b2){
        return new Tuple(deltaX(b1.getX(), b2.getX()), deltaY(b1.getY(), b1.getY()));
    }

    public static Tuple deltaV(Ball b1, Ball b2){
        return new Tuple(deltaVx(b1.getSpeedX(), b2.getSpeedX()), deltaVy(b1.getSpeedY(), b2.getSpeedY()));
    }

    public static double d(Ball b1, Ball b2){
        return Math.pow(deltaV(b1, b2).dot(deltaR(b1, b2)), 2)
                - deltaV(b1, b2).dot(deltaV(b1, b2))
                * (deltaR(b1, b2).dot(deltaR(b1, b2)) - Math.pow(b1.getRadius(), 2));
    }

    public static double J (Ball b1, Ball b2){
        return 2 * b1.getMass() * b2.getMass() * deltaV(b1, b2).dot(deltaR(b1, b2))
                / ((b1.getMass() + b2.getMass())* b1.getRadius());
    }

    public static double Jx (Ball b1, Ball b2){
        return J(b1, b2) * deltaX(b1.getX(), b2.getX()) / b1.getRadius();
    }

    public static double Jy (Ball b1, Ball b2){
        return J(b1, b2) * deltaY(b1.getY(), b2.getY()) / b1.getRadius();
    }

    public static double timeToCollisionTwoParticles(Ball b1, Ball b2){
        if(!collides(b1, b2))
            return -1;
        return - (deltaR(b1, b2).dot(deltaV(b1, b2)) + Math.sqrt(d(b1, b2))) / deltaV(b1, b2).dot(deltaV(b1, b2));
    }

    public static double timeToCollisionParticleWallHorizontal(Ball b, Wall wall){
        if(b.getSpeedY() == 0)
            return -1;
        else if(b.getSpeedY() > 0)
            return (wall.getY() - b.getRadius() - b.getY()) / b.getSpeedY();
        else
            return (b.getRadius() - b.getY()) / b.getSpeedY();
    }

    public static double timeToCollisionParticleWallVertical(Ball b, Wall wall){
        if(b.getSpeedX() == 0)
            return -1;
        else if(b.getSpeedX() > 0)
            return (wall.getX() - b.getRadius() - b.getX()) / b.getSpeedX();
        else
            return (b.getRadius() - b.getX()) / b.getSpeedX();
    }

    public static boolean collides(Ball b1, Ball b2){
        return d(b1, b2) >= 0 || deltaR(b1, b2).dot(deltaV(b1, b2)) < 0;
    }
    public static double deltaX(double x1, double x2){
        return x1 - x2;
    }

    public static double deltaY(double y1, double y2){
        return y1 - y2;
    }

    public static double deltaVx(double vx1, double vx2){
        return vx1 - vx2;
    }

    public static double deltaVy(double vy1, double vy2){
        return vy1 - vy2;
    }

    static class Tuple {
        double a;
        double b;

        public Tuple(double a, double b){
            this.a = a;
            this.b = b;
        }

        public double getA() {
            return a;
        }

        public void setA(double a) {
            this.a = a;
        }

        public double getB() {
            return b;
        }

        public void setB(double b) {
            this.b = b;
        }

        public double dot(Tuple t){
            return a * t.getA() + b * t.getB();
        }
    }


}
