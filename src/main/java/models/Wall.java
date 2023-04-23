package models;

public enum Wall {
    TOP (null, 112f),
    BOTTOM (null, 0f),
    LEFT (0f, null),
    RIGHT(224f, null);

    private Float x;
    private Float y;

    Wall(Float x, Float y){
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }
}
