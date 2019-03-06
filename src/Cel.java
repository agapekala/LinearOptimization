import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cel extends Ogr{
    private LinkedList<Double> wsp=new LinkedList<>();
    private String znak;

    public LinkedList<Double> getWsp() {
        return wsp;
    }

    public void setWsp(LinkedList<Double> wsp) {
        this.wsp = wsp;
    }

    public String getZnak() {
        return znak;
    }

    public void setZnak(String znak) {
        this.znak = znak;
    }

    public void updateWsp(Double wsp1){
        wsp.add(wsp1);
    }

    public void findGoal(String row){
        String line = row;
        String pattern ="([a-z]){2,}";
        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(line);
        m.find();
        String ret=m.group();
        setZnak(ret);
    }

    public Point findOpt(LinkedList<Point> points){
        double min_max,wynik;
        Point best_point=new Point();
        min_max=wsp.getFirst()*points.getLast().getX()+wsp.getLast()*points.getLast().getY();
        for(int i=0; i<points.size()-1; i++){
            wynik=obliczWynik(points.get(i));
            if(znak.equals("min")) {
                if (wynik < min_max) {
                    min_max = wynik;
                    best_point = points.get(i);
                }
            }else{
                if (wynik > min_max) {
                    min_max = wynik;
                    best_point = points.get(i);
                }
            }
        }
        return best_point;
    }

    public double obliczWynik(Point p){
        double wynik;
        wynik=wsp.getFirst()*p.getX()+wsp.getLast()*p.getY();
        return wynik;
    }

    public void print(){
        System.out.print("F(y1,y2)=");
        String rownanie="";
        for(int i=0; i<wsp.size(); i++){
            rownanie+=wsp.get(i)+"*y"+(i+1)+"+";
        }
        rownanie = rownanie.substring(0, rownanie.length() - 1);
        System.out.print(rownanie);
        System.out.print("->");
        System.out.println(znak);
    }

}
