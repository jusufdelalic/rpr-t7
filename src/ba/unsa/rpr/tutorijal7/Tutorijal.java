package ba.unsa.rpr.tutorijal7;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
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
        } catch (FileNotFoundException e) {
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

                double[] temperature = new double[podatak.length - 1];

                while (i < 1000 && i < temperature.length) {

                    //if( temperature[i] < -273.15 ) throw nelegalna temperatura...

                    temperature[i] = Double.parseDouble(podatak[i + 1]);

                    i++;
                }


                grad.setTemperature(temperature);

                gradovi.add(grad);

            }

        } catch (Exception e) {
            System.out.println("Problem pri čitanju podataka.");
            System.out.println("Greška: " + e);
        }


        return gradovi;

    }

    /*Primjer sa predavanja za ispis elemenata iz XML datoteke

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

    */






    public static UN ucitajXml(ArrayList<Grad> gradoviZaUcitavanje) {

        // ucitati drzave iz xml datoteke u klasu un i vratiti tu klasu

        UN ujedinjeneNacije = new UN();

        Document xmldoc = null;


        Drzava drzava = new Drzava();
        String nazivDrzave = new String();
        String jedinicaZaPovrsinu = new String();
        String nazivGlavnogGrada = new String();
        double povrsina = 0;
        Grad grad = new Grad();

        int brojStanovnikaGrada = 0;
        int brojStanovnikaDrzave = 0;

        try {
            DocumentBuilder xmlIzlazniTok = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            xmldoc = xmlIzlazniTok.parse(new File("C:\\Users\\Korisnik\\IdeaProjects\\rpr-t7\\drzave.xml"));
            //prosljedjujemo putanju xml dokumenta, u pozivu metode kreiramo novi bezimeni objekat tipa File
            // (apstraktna reprezentacija putanja datoteka/direktorija)


            NodeList drzaveUXml = xmldoc.getElementsByTagName("drzava");

            // Ova metoda vraća sve elemente iz xml dokumenta onim redom kako su u
            // dokumentu napisani, sa tagom "drzava" u vidu liste Node-ova
            // Element je interfejs koji je naslijeđen iz interfejsa Node


            for (int i = 0; i < drzaveUXml.getLength(); i++) // idemo kroz listu nodova
            {
                Node drzavaNodeUXml = drzaveUXml.item(i); // uzimamo jednu drzavu

                if (drzavaNodeUXml instanceof Element) // provjera da li je ucitani node validan xml element
                {

                    Element drzavaElementUXml = (Element) drzavaNodeUXml; // downcasting...

                    // vracanje vrijednosti atributa stanovnika, povratna vrijednost String
                    String stanovnici = drzavaElementUXml.getAttribute("brojStanovnika");
                    brojStanovnikaDrzave = Integer.parseInt(stanovnici); // konverzija String u int
                    drzava.setBrojStanovnika(brojStanovnikaDrzave);


                    NodeList djeca_drzave = drzavaElementUXml.getChildNodes();
                    // Svi nodovi unutar noda drzava

                    for (int j = 0; j < djeca_drzave.getLength(); j++) {
                        Node nodeDijeteDrzave = djeca_drzave.item(j);

                        if (nodeDijeteDrzave instanceof Element) {
                            //Element elementDijeteDrzave = (Element) nodeDijeteDrzave;


                            if (((Element) nodeDijeteDrzave).getTagName().equals("naziv")) // iduci tag unutar drzava je naziv drzave
                            {
                                nazivDrzave = nodeDijeteDrzave.getTextContent(); // text content i atribut nije isto...

                                drzava.setNaziv(nazivDrzave);
                            } else if (((Element) nodeDijeteDrzave).getTagName().equals("povrsina")) {
                                jedinicaZaPovrsinu = ((Element) nodeDijeteDrzave).getAttribute("jedinicaZaPovrsinu");// km2
                                povrsina = Integer.parseInt(nodeDijeteDrzave.getTextContent()); // 51209

                                drzava.setPovrsina(povrsina);
                                drzava.setJedinicaZaPovrsinu(jedinicaZaPovrsinu);
                            } else if (((Element) nodeDijeteDrzave).getTagName().equals("glavniGrad")) {
                                nazivGlavnogGrada = ((Element) nodeDijeteDrzave).getElementsByTagName("naziv").item(0).getTextContent();

                                brojStanovnikaGrada = Integer.parseInt(((Element) nodeDijeteDrzave).getAttribute("brojStanovnika"));

                                grad.setNaziv(nazivGlavnogGrada.trim());
                                grad.setBrojStanovnika(brojStanovnikaGrada);

                                for (int k = 0; k < gradoviZaUcitavanje.size(); k++) {
                                    if (grad.getNaziv().equals(gradoviZaUcitavanje.get(k).getNaziv()))
                                        grad.setTemperature(gradoviZaUcitavanje.get(k).getTemperature());
                                }
                            }

                        }
                    }
                    drzava.setGlavniGrad(grad);
                    ujedinjeneNacije.getDrzave().add(drzava);

                    //ponovno ucitavanje...
                    drzava = new Drzava();
                    grad = new Grad();

                }

            }

        } catch (Exception e) {

            System.out.println("Greska : " + e);
        }


        return ujedinjeneNacije;
    }

    public static void zapisiXml(UN ujedinjeneNacije) throws FileNotFoundException {

        try {

            XMLEncoder izlazniTok = new XMLEncoder(new FileOutputStream("un.xml"));

            izlazniTok.writeObject(ujedinjeneNacije);
            izlazniTok.close();

        } catch (FileNotFoundException izuzetak) {

            System.out.println("Greska prilikom zapisivanja u datoteku: " + izuzetak);
        }

    }

    public static void main(String[] args) throws Exception {

        try {
            var gradovi = ucitajGradove();

            for (Grad grad : gradovi) {
                System.out.println(grad);
            }

            var ujedinjeneNacije = ucitajXml(gradovi);

            //zapisiXml(ujedinjeneNacije);
        } catch (Exception e) {

            // ništa...
        }

    }
}

