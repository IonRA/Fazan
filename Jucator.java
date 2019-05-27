package joc;

public class Jucator {

    private String nume;
    private int scor;
    private int penalizari;

    public Jucator(String numeJucator) {
        nume = numeJucator;
        scor = penalizari = 0 ;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setPenalizari(int penalizari) {
        this.penalizari = penalizari;
    }

    public int getPenalizari() {
        return penalizari;
    }

    public void setScor(int scor) {
        this.scor = scor;
    }

    public int getScor() {
        return scor;
    }

    public void incrementeazaPenalizari() {
        if ((++penalizari) == 3)
        {
            incrementeazaScor();
            penalizari = 0;
        }
    }

    public  void incrementeazaScor() {
        ++scor;
    }
}

