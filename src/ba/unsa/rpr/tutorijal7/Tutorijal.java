package ba.unsa.rpr.tutorijal7;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

    public static void ispisiElement(Element element) {

        System.out.print("Element " + element.getTagName() + ", ");

        int brAtributa = element.getAttributes().getLength();
        System.out.print(brAtributa+" atributa");

        NodeList djeca = element.getChildNodes();

        // Ako nema djece ispisujemo sadržaj
        if (djeca.getLength() == 1) {
            String sadrzaj = element.getTextContent();
            System.out.println(", sadrzaj: '" + sadrzaj + "'");
        } else {
            System.out.println("");
        }

        for(int i = 0; i < djeca.getLength(); i++) {
            Node dijete = djeca.item(i);
            if (dijete instanceof Element) {
                ispisiElement((Element)dijete);
            }
        }
    }

    public static UN ucitajXml(ArrayList<Grad> gradovi)  {

        // ucitati drzave iz xml datoteke u klasu un i vratiti tu klasu

        UN ujedinjeneNacije = null;

        Document xmldoc = null;

        try {

            DocumentBuilder docReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc = docReader.parse(new File("drzave.xml"));

            Element korijen = xmldoc.getDocumentElement();

            ispisiElement(korijen);

        }

        catch(Exception e) {
            System.out.println("Greška: " + e);
        }

        return ujedinjeneNacije;
    }

    public static void main(String[] args) throws Exception {

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
