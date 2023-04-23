package models;

import utils.MathUtils;

import java.util.Set;
import java.util.TreeSet;

public class Ball {

    private static int global_id = 0;
    private int id;
    private float x;
    private float y;
    private float radius;
    private int mass;
    private float speedX;
    private float speedY;
    private boolean isHole;
//
//    private Set<Collision> collisions = new TreeSet<>();

    public Ball(float x, float y, float radius, float speedX, float speedY, int mass,  boolean isHole) {
        this.id = global_id++;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = speedX;
        this.speedY = speedY;
        this.mass = mass;
        this.isHole = isHole;
    }

    public Ball(float x, float y, float radius, int mass, boolean isHole) {
        this.id = global_id++;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = 0;
        this.speedY = 0;
        this.mass = mass;
        this.isHole = isHole;
    }

    public float collidesX(){
        float timeLeft = MathUtils.timeToCollisionParticleWallVertical(this, Wall.LEFT);
        float timeRight = MathUtils.timeToCollisionParticleWallVertical(this, Wall.RIGHT);
        return timeLeft >= 0? timeLeft: timeRight >= 0? timeRight: -1;
    }

    public float collidesY(){
        float timeTop = MathUtils.timeToCollisionParticleWallHorizontal(this, Wall.TOP);
        float timeBottom = MathUtils.timeToCollisionParticleWallHorizontal(this, Wall.BOTTOM);
        return timeTop >= 0? timeTop: timeBottom >= 0? timeBottom: -1;
    }

    public float collides(Ball b) {
        return MathUtils.timeToCollisionTwoParticles(this, b);
    }

    public void bounceX(float time){
        setSpeedX(-getSpeedX());
    }

    public void bounceY(float time) {
        setSpeedY(-getSpeedY());
    }

    //returns true if the balls bounced, false if one of them is a hole
    public boolean bounce(Ball b, float time) {

        if(this.isHole || b.isHole)
            return false;

        float jx = MathUtils.Jx(this, b)/this.getMass();
        float jy = MathUtils.Jy(this, b)/this.getMass();

        setSpeedX(getSpeedX() + jx);
        setSpeedY(getSpeedY() + jy);

        b.setSpeedX(b.getSpeedX() - jx);
        b.setSpeedY(b.getSpeedY() - jy);
        return true;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
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
