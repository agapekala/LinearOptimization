import java.util.LinkedList;

public class PointPP {
    private LinkedList<Double> points=new LinkedList<>();

    public LinkedList<Double> getPoints() {
        return points;
    }

    public void setPoints(LinkedList<Double> points) {
        this.points = points;
    }

    public PointPP(int max) {
        for(int i=0; i<max; i++){
            points.add(0.0);
        }
    }

    public void updatePoint(LinkedList<Ogr> ogran, LinkedList<Integer> eq){
        Point punkt=new Point();
        double a=ogran.getFirst().getWsp().get(eq.get(0));
        double b=ogran.getFirst().getWsp().get(eq.get(1));
        double c=ogran.getFirst().getWolna();

        double d=ogran.getLast().getWsp().get(eq.get(0));
        double e=ogran.getLast().getWsp().get(eq.get(1));
        double f=ogran.getLast().getWolna();
        punkt.findPoint(a,b,c,d,e,f);

        points.remove((int)eq.getFirst());
        points.add(eq.getFirst(),punkt.getX());
        points.remove((int)eq.getLast());
        points.add(eq.getLast(),punkt.getY());
    }

    public void print(){
        System.out.println("Punkt realizujący optium PP: ");
        String str="V = (";
        for(Double d: points){
            str+=(d+", ");
        }
        str = str.substring(0, str.length() - 2);
        System.out.print(str);
        System.out.println(")");
        System.out.println();
    }
}
