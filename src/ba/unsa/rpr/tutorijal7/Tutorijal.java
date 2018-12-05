package ba.unsa.rpr.tutorijal7;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
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

                int i = 0;

                double[] temperature = new double [podatak.length-1];

                while( i < 1000 && i < temperature.length ) {

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

    /*

    static Fakultet ucitajXml() {
        Fakultet f = null;
        try {
            XMLDecoder ulaz = new XMLDecoder(new FileInputStream("fakultet.xml"));
            f = (Fakultet) ulaz.readObject();
            ulaz.close();
        } catch(Exception e) {
            System.out.println("Greška: "+e);
        }
        return f;
    }
     */

    public static UN ucitajXml(ArrayList<Grad> gradovi)  {

        //ucitati drzave iz xml datoteke u klasu un i vratiti tu klasu

        UN ujedinjeneNacije = null;

        try {
            XMLDecoder ulaz = new XMLDecoder(new FileInputStream("drzave.xml"));
            ujedinjeneNacije = (UN) ulaz.readObject();
            ulaz.close();
        } catch(Exception e) {
            System.out.println("Greška: "+e);
        }


        return ujedinjeneNacije;
    }

    public static void main(String[] args) throws Exception{

        try {
            var gradovi = ucitajGradove();

            for(Grad grad : gradovi) {
                System.out.println(grad);
            }

            var UN = ucitajXml(gradovi);
        }

        catch(Exception e) {

            // ništa...
        }

    }
}
