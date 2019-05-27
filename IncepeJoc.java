package joc;

import java.io.IOException;

public class IncepeJoc {

    public static void main(String[] args){
        try {
            Fazan.initializareDictionar("/home/ion/Desktop/dictionar");
            Joc joc = new Fazan();
            joc.startJoc();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
