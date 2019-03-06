import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ogr {
    private LinkedList<Double> wsp=new LinkedList<>();
    private String znak="<=";
    private Double wolna;

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

    public Double getWolna() {
        return wolna;
    }

    public void setWolna(Double wolna) {
        this.wolna = wolna;
    }

    public void updateWsp(Double wsp1){
        wsp.add(wsp1);
    }

    public void fillWsp(int max){
        for(int i=0; i<max; i++){
            wsp.add(0.0);
        }
    }

    public void findWsp(String row){
        LinkedList<Double> ret =new LinkedList<>();
        List<String> allMatches = new ArrayList<String>();
        String line = row;
        String pattern = "\\d+\\.?\\d*\\*x\\d";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(line);
        while (m.find( )) {
            allMatches.add(m.group());
        }

        int check=-1;
        for(String a: allMatches) {
            line = a;
            pattern = "[+-]?([0-9]*[.])?[0-9]+";
            r = Pattern.compile(pattern);
            m = r.matcher(line);
            check++;
            while (m.find()) {
                double value=Double.parseDouble(m.group());
                m.find();
                int index=Integer.parseInt(m.group())-1;
                if(index!=check){
                    for(int i=check; i<index; i++){
                        ret.add(0.0);
                        check++;
                    }
                    ret.add(index,value);
                }else{
                    ret.add(index,value);
                }
//                if(index==ret.size()){
//                    ret.add(index,value);
//                }else{
//                    for(int i=ret.size(); i<index; i++){
//                        ret.add(0.0);
//                    }
//                    ret.add(index,value);
//                }
            }
        }
        setWsp(ret);
    }

    public void findWol(String row){
        String line = row;
        String pattern = "\\d+$";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(line);
        m.find();
        String ret=m.group();
        setWolna(Double.parseDouble(ret));
    }

    public void findSign(String row){
        String line = row;
        String pattern = "(<|>)=";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(line);
        m.find();
        String ret=m.group();
        setZnak(ret);
    }

    public void print(){
        String rownanie="";
        for(int i=0; i<wsp.size(); i++){
            rownanie+=wsp.get(i)+"*y"+(i+1)+"+";
        }
        rownanie = rownanie.substring(0, rownanie.length() - 1);
        System.out.print(rownanie);
        System.out.print(znak);
        System.out.println(wolna);
    }

    public boolean checkEq(Point p){
        double wynik;
        wynik=wsp.getFirst()*p.getX()+wsp.getLast()*p.getY();
        wynik *= 100;
        wynik = Math.round(wynik);
        wynik /= 100;
        if(wynik!=wolna){
            return false;
        }
        return true;
    }

}
