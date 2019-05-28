package joc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Fazan implements Joc{

    private static Set<String> dictionar;
    private static Set<String> prefixe;
    private static Random intamplator;

    private Set<String> cuvinteSpuse;
    private List<Jucator> jucatori;
    private int scorMax;

    public static void initializareDictionar(String numeFisierCuvinte) throws IOException{
        dictionar = new HashSet<String>();
        prefixe = new HashSet<String>();

        FileReader fr = new FileReader(numeFisierCuvinte);
        BufferedReader br = new BufferedReader(fr);
        String cuvant;

        while((cuvant = br.readLine()) != null)
        {
            dictionar.add(cuvant);

            if (cuvant.length() > 2)
                prefixe.add(cuvant.substring(0, 2));
        }

        br.close();
        fr.close();
    }

    public Fazan(){
        initializareJoc();
    }

    public void initializareJoc(){
        cuvinteSpuse = new HashSet<String>();
        jucatori = new ArrayList<Jucator>();
        intamplator = new Random();
        scorMax = 0;
    }

    private boolean esteCuvant(String text) {
        return dictionar.contains(text);
    }

    private boolean incuieJucator(String cuvant){
        return !prefixe.contains(cuvant.substring(cuvant.length() - 2));
    }

    private boolean verificaPrefixSufix(String cuvantCurent, String cuvantPrecedent) {
        if (cuvantPrecedent.substring(cuvantPrecedent.length() - 2).equals(cuvantCurent.substring(0, 2)))
            return true;

        return false;
    }

    private char genereazaLiteraInceput() {
        return (char)('a' + intamplator.nextInt(26));
    }

    public void startJoc() throws IOException{
        System.out.println("Salutare si bine ati venit in jocul FAZAN!\nIntroduceti numarul de jucatori din joc: ");

        Scanner citeste = new Scanner(System.in);

        int nrJucatori;

        while ((nrJucatori = citeste.nextInt()) <= 1)
        {
            System.out.println("Jocul nu admite mai putin de 2 jucatori!!!\nIntroduceti numarul de jucatori din joc: ");
        }

        String numeJucator;

        for (int i = 1; i <= nrJucatori; ++i)
        {
            System.out.println("Introduceti numelele jucatorului nr " + i + ": ");
            numeJucator = citeste.next();
            jucatori.add(new Jucator(numeJucator));
        }

        int nrRaspunsuri = 0, codLiteraInceput;
        char literaInceput;
        String cuvantCurent, cuvantPrecedent = null;

        System.out.println("<<<SA INCEAPA JOCUL>>>");
        int runda = 1, jucatorCurent = 0;

        while (scorMax != 5)
        {
            if (nrRaspunsuri == 0)
            {
                System.out.println("\nRunda " + runda);
                literaInceput = genereazaLiteraInceput();

                System.out.println("Litera de inceput este \'" + String.valueOf(literaInceput) + "\'");

                System.out.println("Jucatorul " + jucatori.get(jucatorCurent).getNume() + " sa introduca un cuvant:");

                while ((cuvantCurent = citeste.next()).charAt(0) != literaInceput || !esteCuvant(cuvantCurent) ||
                        incuieJucator(cuvantCurent) || cuvinteSpuse.contains(cuvantCurent))
                {
                    System.out.println("Ai in inchis prima tura sau cuvantul este invalid. Introduceti un alt cuvant! ");
                }

                cuvantPrecedent = cuvantCurent;
                ++nrRaspunsuri;
            }
            else if (nrRaspunsuri < nrJucatori)
            {
                while (true)
                {
                    System.out.println("Jucatorul " + jucatori.get(jucatorCurent).getNume() + " sa introduca un cuvant:");

                    cuvantCurent = citeste.next();

                    if (!verificaPrefixSufix(cuvantCurent, cuvantPrecedent) || !esteCuvant(cuvantCurent))
                    {
                        jucatori.get(jucatorCurent).incrementeazaScor();
                        System.out.println("Jucatorul " + jucatori.get(jucatorCurent).getNume() + " are scorul de " +
                                jucatori.get(jucatorCurent).getScor() + " puncte");
                        ++runda;
                        nrRaspunsuri = 0;

                        break;
                    }
                    else if (cuvinteSpuse.contains(cuvantCurent))
                    {
                        int scorPrecedent = jucatori.get(jucatorCurent).getScor();
                        jucatori.get(jucatorCurent).incrementeazaPenalizari();

                        System.out.println("Jucatorul " + jucatori.get(jucatorCurent).getNume() + " are " +
                                jucatori.get(jucatorCurent).getPenalizari() + " penalizari");

                        ++nrRaspunsuri;

                        if (scorPrecedent != jucatori.get(jucatorCurent).getScor())
                        {
                            System.out.println("Jucatorul " + jucatori.get(jucatorCurent).getNume() + " are scorul de " +
                                    jucatori.get(jucatorCurent).getScor() + " puncte");

                            ++runda;
                            nrRaspunsuri = 0;
                        }
                        break;
                    }
                    else if (!incuieJucator(cuvantCurent))
                    {
                        cuvinteSpuse.add(cuvantCurent);
                        cuvantPrecedent = cuvantCurent;
                        ++nrRaspunsuri;
                        break;
                    }
                    else System.out.println("Nu ai voie sa inchizi in prima tura");
                }

                scorMax = scorMax < jucatori.get(jucatorCurent).getScor() ?
                        jucatori.get(jucatorCurent).getScor() : scorMax;
            }
            else
            {
                System.out.println("Jucatorul " + jucatori.get(jucatorCurent).getNume() + " sa introduca un cuvant:");

                if (!verificaPrefixSufix(cuvantCurent = citeste.next(), cuvantPrecedent) || !esteCuvant(cuvantCurent))
                {
                    jucatori.get(jucatorCurent).incrementeazaScor();
                    System.out.println("Jucatorul " + jucatori.get(jucatorCurent).getNume() + " are scorul de " +
                            jucatori.get(jucatorCurent).getScor() + " puncte");
                    ++runda;
                    nrRaspunsuri = 0;
                }
                else if (cuvinteSpuse.contains(cuvantCurent))
                {
                    int scorPrecedent = jucatori.get(jucatorCurent).getScor();
                    jucatori.get(jucatorCurent).incrementeazaPenalizari();

                    System.out.println("Jucatorul " + jucatori.get(jucatorCurent).getNume() + " are " +
                            jucatori.get(jucatorCurent).getPenalizari() + " penalizari");

                    ++nrRaspunsuri;

                    if (scorPrecedent != jucatori.get(jucatorCurent).getScor())
                    {
                        System.out.println("Jucatorul " + jucatori.get(jucatorCurent).getNume() + " are scorul de " +
                                jucatori.get(jucatorCurent).getScor() + " puncte");

                        ++runda;
                        nrRaspunsuri = 0;
                    }
                }
                else if (incuieJucator(cuvantCurent))
                {
                    cuvinteSpuse.add(cuvantCurent);
                    jucatori.get((jucatorCurent + 1) %nrJucatori).incrementeazaScor();
                    System.out.println("Jucatorul " + jucatori.get((jucatorCurent + 1) %nrJucatori).getNume() +
                                       " are scorul de " + jucatori.get((jucatorCurent + 1) %nrJucatori).getScor() +
                                       " puncte");
                    ++runda;
                    nrRaspunsuri = 0;
                }
                else
                {
                    cuvinteSpuse.add(cuvantCurent);
                    cuvantPrecedent = cuvantCurent;
                    ++nrRaspunsuri;
                }

                scorMax = scorMax < jucatori.get(jucatorCurent).getScor() ?
                        jucatori.get(jucatorCurent).getScor() : scorMax;
            }

            jucatorCurent = (jucatorCurent + 1) % nrJucatori;
        }

        citeste.close();

        System.out.println("\nJocul s-a terminat!");
    }
}
