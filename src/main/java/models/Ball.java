package models;

import utils.MathUtils;

import java.util.Set;
import java.util.TreeSet;

public class Ball {

    private int id;
    private double x;
    private double y;
    private double radius;
    private int mass;
    private double speedX;
    private double speedY;
    private boolean isHole;
//
//    private Set<Collision> collisions = new TreeSet<>();

    public Ball(int id, double x, double y, double radius, double speedX, double speedY, boolean isHole) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = speedX;
        this.speedY = speedY;
        this.isHole = isHole;
    }

    public Ball(int id, double x, double y, double radius) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = 0;
        this.speedY = 0;
        this.isHole = false;
    }

    public double collidesX(){
        double timeLeft = MathUtils.timeToCollisionParticleWallVertical(this, Wall.LEFT);
        double timeRight = MathUtils.timeToCollisionParticleWallVertical(this, Wall.RIGHT);
        return timeLeft >= 0? timeLeft: timeRight >= 0? timeRight: -1;
    }

    public double collidesY(){
        double timeTop = MathUtils.timeToCollisionParticleWallHorizontal(this, Wall.TOP);
        double timeBottom = MathUtils.timeToCollisionParticleWallHorizontal(this, Wall.BOTTOM);
        return timeTop >= 0? timeTop: timeBottom >= 0? timeBottom: -1;
    }

    public double collides(Ball b) {
        return MathUtils.timeToCollisionTwoParticles(this, b);
    }

    public void bounceX(){
        setSpeedX(-getSpeedX());
    }

    public void bounceY() {
        setSpeedY(-getSpeedY());
    }

    public void bounce(Ball b) {
        setSpeedX(getSpeedX() + MathUtils.Jx(this, b)/this.getMass());
        setSpeedY(getSpeedY() + MathUtils.Jy(this, b)/this.getMass());

        b.setSpeedX(b.getSpeedX() - MathUtils.Jx(this, b)/b.getMass());
        b.setSpeedY(b.getSpeedY() - MathUtils.Jy(this, b)/b.getMass());
    }

//    public int getCollisionCount() {
//        return collisions.size();
//    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public boolean isHole() {
        return isHole;
    }

    public void setHole(boolean hole) {
        isHole = hole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    @Override
    public String toString() {
        return "Ball{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
