import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;



public class Main{

    public static void main(String [] args) throws IOException{
        LinkedList<String> s = new LinkedList<>();
        File file = new File("/home/agnieszka/IdeaProjects/BOiTZO/src/dane.txt");
        Scanner in = new Scanner(file);
        int line_num = 0;
        while (in.hasNextLine()) {
            String zdanie = in.nextLine();
            s.add(zdanie);
            line_num++;
        }
        //Czytanie danych z PP

        //Współczynniki
        LinkedList<Ogr> ogran = new LinkedList<>();
        for (int k = 0; k < line_num - 1; k++) {
            Ogr ogr = new Ogr();
            ogr.findWsp(s.get(k));
            ogr.findWol(s.get(k));
            ogr.findSign(s.get(k));
            ogran.add(ogr);
        }

        //Funkcja celu
        Cel cel = new Cel();
        cel.findWsp(s.getLast());
        cel.findGoal(s.getLast());

        //Zamiana na program dualny
        Cel cel_dual = new Cel();
        LinkedList<Ogr> ogran_dual = new LinkedList<>();
        int limit1 = ogran.getFirst().getWsp().size();
        int limit2 = ogran.getLast().getWsp().size();
        int limit=limit1;
        if(limit1>limit2){
            ogran.getLast().getWsp().add(0.0);
            limit=limit1;
        }else if(limit1<limit2){
            ogran.getFirst().getWsp().add(0.0);
            limit=limit2;
        }

        for (int i = 0; i < limit; i++) {
            Ogr ogr_dual = new Ogr();
            ogran_dual.add(ogr_dual);
        }

        //parsowanie ograniczeń
        for (Ogr g : ogran) {
            cel_dual.updateWsp(g.getWolna());
            for (int j = 0; j < limit; j++) {
                ogran_dual.get(j).updateWsp(g.getWsp().get(j));
                ogran_dual.get(j).setWolna(cel.getWsp().get(j));
                if (g.getZnak().equals("<=")) {
                    ogran_dual.get(j).setZnak(">=");
                } else {
                    ogran_dual.get(j).setZnak("<=");
                }
            }
        }
        if (cel.getZnak().equals("max")) {
            cel_dual.setZnak("min");
        } else {
            cel_dual.setZnak("max");
        }

        //wypisanie programu dualnego
        System.out.println("Program dualny");
        System.out.println();
        for(Ogr o: ogran_dual){
            o.print();
        }
        cel_dual.print();
        System.out.println();



        //znalezienie punktów
        LinkedList<Point> points = new LinkedList<>();
        for (int a = 0; a < ogran_dual.size(); a++) {
            for (int b = a + 1; b < ogran_dual.size(); b++) {
                Point p = new Point();
                p.findPoint(ogran_dual.get(a), ogran_dual.get(b));
                if (p.getX() >= 0 && p.getY() >= 0) {
                    if(!points.contains(p)){
                        points.add(p);
                    }
                }
            }
        }

        //przecięcia osi współrzędnych
        Ogr ogr_x = new Ogr();
        Ogr ogr_y = new Ogr();

        ogr_x.updateWsp(1.0);
        ogr_x.updateWsp(0.0);
        ogr_x.setWolna(0.0);

        ogr_y.updateWsp(0.0);
        ogr_y.updateWsp(1.0);
        ogr_y.setWolna(0.0);

        for (Ogr g : ogran_dual) {
            Point p1 = new Point();
            Point p2 = new Point();
            p1.findPoint(g, ogr_x);
            if(!points.contains(p1)) {
                points.add(p1);
            }
            p2.findPoint(g, ogr_y);
            if(!points.contains(p2)) {
                points.add(p2);
            }
        }


        //punkty ograniczające
        LinkedList<Point> disc_point = new LinkedList<>();
        System.out.println("Punkty ograniczające: ");
        for (Point p : points) {
            //p.print();
            boolean flag = p.checkPoint(p.getX(), p.getY(), ogran_dual);
            if (flag) {
                disc_point.add(p);
                p.print();
            }
        }



        LinkedList<Integer> rownania = new LinkedList<>();
        Point best_point = cel_dual.findOpt(disc_point);
        for (int it = 0; it < ogran_dual.size(); it++) {
            boolean help = ogran_dual.get(it).checkEq(best_point);
            if (help) {
                rownania.add(it);
            }
        }

        PointPP punkt = new PointPP(ogran_dual.size());
        punkt.updatePoint(ogran, rownania);
        punkt.print();


        System.out.print("Wartość optymalna: ");
        System.out.println("F(V)="+cel_dual.obliczWynik(best_point));
    }

}
