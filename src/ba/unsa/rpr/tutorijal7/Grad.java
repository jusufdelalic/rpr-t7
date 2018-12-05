package ba.unsa.rpr.tutorijal7;

public class Grad {

    private String naziv;
    private int brojStanovnika;
    private double [] temperature;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public double[] getTemperature() {
        return temperature;
    }

    public void setTemperature(double[] temperature) {
        this.temperature = temperature;
    }



    public Grad() {
        this.naziv = null;
        this. brojStanovnika = 0;
        temperature = null;
    }


    @Override
    public String toString() {

        String podaciOGradu = this.naziv + " (" + this.brojStanovnika + ")\nMjerenja: ";

        if(temperature != null) {
            for (int i = 0; i < temperature.length; i++) {
                podaciOGradu += temperature[i];
                if (i != temperature.length - 1) podaciOGradu += ",";

            }
        }

        podaciOGradu += "\n";

        return podaciOGradu;

    }


}
