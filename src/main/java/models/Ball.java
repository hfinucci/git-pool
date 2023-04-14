package models;

import java.util.Set;
import java.util.TreeSet;

public class Ball {

    private int id;
    private int x;
    private int y;
    private int radius;
    private int mass;
    private int speedX;
    private int speedY;
    private boolean isHole;

    private Set<Collision> collisions = new TreeSet<>();

    public Ball(int id, int x, int y, int radius, int speedX, int speedY, boolean isHole) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = speedX;
        this.speedY = speedY;
        this.isHole = isHole;
    }

    public double collidesX(){
        //TODO: implementar
        return 0;
    }

    public double collidesY(){
        //TODO: implementar
        return 0;
    }

    public double collides(Ball b) {
        //TODO: implementar
        return 0;
    }

    public void bounceX(){
        //TODO: implementar
        setSpeedX(-getSpeedX());
    }

    public void bounceY() {
        //TODO: implementar
        setSpeedY(-getSpeedY());
    }

    public void bounce(Ball b) {
        //TODO: implementar
    }

    public int getCollisionCount() {
        return collisions.size();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
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
}
