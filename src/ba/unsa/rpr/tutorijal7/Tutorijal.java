package ba.unsa.rpr.tutorijal7;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {

    public static ArrayList<Grad> ucitajGradove() throws FileNotFoundException {

        ArrayList<Grad> gradovi = new ArrayList<Grad>();

        Scanner ulaz; // omotač za tekstualni ulazni tok

        // Konstruisanje ulaznog toka za datoteku mjerenja.txt
        try {
            ulaz = new Scanner(new FileReader("mjerenja.txt"));
        }

        catch(FileNotFoundException e) {
            System.out.println("Datoteka mjerenja.txt ne postoji ili se ne može otvoriti.");
            System.out.println("Greška: " + e);
            throw e; // kraj programa
        }


        try {
            // Učitavamo podatke...

            while (ulaz.hasNext()) {

                String s = ulaz.nextLine();
                String[] podatak = s.split(","); // razdvajanje stringova preko ","

                //if(!(podatak[0].charAt(0)>='A' && podatak[0].charAt(0)<='Z')) throw nelegalan naziv grada...


                Grad grad = new Grad();

                grad.setNaziv(podatak[0]);

                double[] temperature = new double [1000];

                int i = 0;

                while( i < 1000 && i < podatak.length - 1 ) {

                    //if( temperature[i] < -273.15 ) throw nelegalna temperatura...

                    temperature[i] = Double.parseDouble(podatak[i+1]);

                    i++;
                }

                grad.setTemperature(temperature);

                gradovi.add(grad);

            }

        }

        catch(Exception e) {
            System.out.println("Problem pri čitanju podataka.");
            System.out.println("Greška: " + e);
        }




        return  gradovi;

    }

    public static void main(String[] args) {

        try {
            ucitajGradove();
        }

        catch(FileNotFoundException e) {}

    }
}
