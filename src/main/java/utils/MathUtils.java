package utils;

public class MathUtils {
    //TODO: estan todas mal, ignorarlas

    public double delta_r(double x1, double y1, double x2, double y2){

        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public double delta_x(double x1, double x2){
        return x1 - x2;
    }

    public double delta_y(double y1, double y2){
        return y1 - y2;
    }

    public double delta_vx(double vx1, double vx2){
        return vx1 - vx2;
    }

    public double delta_vy(double vy1, double vy2){
        return vy1 - vy2;
    }

    class Tuple {
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
    }


}
