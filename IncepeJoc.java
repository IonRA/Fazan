package joc;

public class IncepeJoc {

    public static void main(String[] args){
        try {
            Fazan.initializareDictionar("dictionar.txt");
            Joc joc = new Fazan();
            joc.startJoc();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
