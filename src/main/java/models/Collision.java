package models;

import java.util.Objects;

public class Collision implements Comparable<Collision> {
    Ball ball1;
    Ball ball2;

    Wall wall;

    double time;

    double timeToCollision;

    public Collision(Ball ball1, Ball ball2, double time) {
        this.ball1 = ball1;
        this.ball2 = ball2;
        this.wall = null;
        this.time = time;
    }

    public Collision(Ball ball, Wall wall, double time) {
        this.ball1 = ball;
        this.ball2 = null;
        this.wall = wall;
        this.time = time;
    }

    public boolean wasSuperveningEvent(){
        return false;
    }

    public Ball getBall1() {
        return ball1;
    }

    public void setBall1(Ball ball1) {
        this.ball1 = ball1;
    }

    public Ball getBall2() {
        return ball2;
    }

    public void setBall2(Ball ball2) {
        this.ball2 = ball2;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Collision collision = (Collision) o;

        if (!Objects.equals(ball1, collision.ball1)) return false;
        if (!Objects.equals(ball2, collision.ball2)) return false;
        return wall == collision.wall;
    }

    @Override
    public int hashCode() {
        int result = ball1 != null ? ball1.hashCode() : 0;
        result = 31 * result + (ball2 != null ? ball2.hashCode() : 0);
        result = 31 * result + (wall != null ? wall.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Collision o) {
        if (this.time < o.time) {
            return -1;
        } else if (this.time > o.time) {
            return 1;
        }
        return 0;
    }
}
