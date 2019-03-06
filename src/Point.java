import java.util.LinkedList;

public class Point {
    private double x;
    private double y;

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

    public void findPoint(Ogr ogr1, Ogr ogr2){
        double a=ogr1.getWsp().getFirst();
        double b=ogr1.getWsp().getLast();
        double c=ogr1.getWolna();

        double d=ogr2.getWsp().getFirst();
        double e=ogr2.getWsp().getLast();
        double f=ogr2.getWolna();
        double x;
        double y;
        double disc;

        disc=a * e - b * d;
        disc *= 100;
        disc = Math.round(disc);
        disc /= 100;

        if(disc==0){
            //System.out.println("Brak rozwiązań");
        }else {

            x = (c * e - b * f) / (a * e - b * d);
            x *= 100;
            x = Math.round(x);
            x /= 100;

            y = (a * f - c * d) / (a * e - b * d);
            y *= 100;
            y = Math.round(y);
            y /= 100;
            setX(x);
            setY(y);
        }

    }
    public void findPoint(double a,double b,double c, double d, double e,double f){
        double x;
        double y;
        double disc;

        disc=a * e - b * d;
        disc *= 100;
        disc = Math.round(disc);
        disc /= 100;

        if(disc==0){
            //System.out.println("Brak rozwiązań");
        }else {

            x = (c * e - b * f) / (a * e - b * d);
            x *= 100;
            x = Math.round(x);
            x /= 100;

            y = (a * f - c * d) / (a * e - b * d);
            y *= 100;
            y = Math.round(y);
            y /= 100;
            setX(x);
            setY(y);
        }
    }

    public boolean checkPoint(double x1, double y1, LinkedList<Ogr> ogr){
        String znak=ogr.getFirst().getZnak();
        double x;
        double y;
        double d;
        double e;
        double f;
        double a1=1;
        double b1=0;
        double c1=x1;
        double a2=0;
        double b2=1;
        double c2=y1;

        for(Ogr o: ogr){
            d=o.getWsp().getFirst();
            e=o.getWsp().getLast();
            f=o.getWolna();
            x = (c2 * e - b2 * f) / (a2* e - b2 * d);
            x *= 100;
            x = Math.round(x);
            x /= 100;

            y = (a1 * f - c1 * d) / (a1 * e - b1 * d);
            y *= 100;
            y = Math.round(y);
            y /= 100;

            if(znak.equals(">=")) {
                if(d==0.0){
                    if(f/e>y1){
                        return false;
                    }
                }else if(e==0.0){
                    if(f/d>x1){
                        return false;
                    }
                }else{
                    if (y > y1 || x > x1) {
                    return false;
                }
                }
            }else{
                if(d==0.0){
                    if(f/e<y1){
                        return false;
                    }
                }else if(e==0.0){
                    if(f/d<x1){
                        return false;
                    }
                }else{
                    if (y < y1 || x < x1) {
                        return false;
                    }
                }
            }
        }
            return true;
    }

    public void print(){
        System.out.println("x: "+getX());
        System.out.println("y: "+getY());
        System.out.println();
    }

    public boolean equals(Object o){
        if(o instanceof Point){
            Point toCompare = (Point) o;
            return (this.getX()==toCompare.getX() && this.getY()==toCompare.getY());
        }
        return false;
    }


}
