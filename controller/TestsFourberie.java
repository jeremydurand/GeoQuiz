/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.themes.CtrlCapitalQuiz;
import geoquiz.Connexion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.CountriesDao;
import modele.metier.Country;

/**
 *
 * @author Benoit
 */
public class TestsFourberie {

    public static void main(String[] args) {
        Connexion.connecter();
        ArrayList<Country> countries = null;
        try {
            countries = CountriesDao.selectAll();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlCapitalQuiz.class.getName()).log(Level.SEVERE, null, ex);
        }
        int index = new Random().nextInt(countries.size());
        String capitale = countries.get(index).getCapital();
        
        Random random = new Random();
        String alphabet = " abcdefghijklmnopqrstuvwxyz";
        int randomLetterIndex = random.nextInt(alphabet.length());
        char theLetter = alphabet.charAt(randomLetterIndex);
        System.out.println(theLetter);
        
        int indexLetter = random.nextInt(capitale.length() - 1 + 1) + 1;
        char letter = capitale.charAt(indexLetter);
        String subtile = capitale.replace(letter, theLetter);
        System.out.println(subtile);
        StringBuilder sb = new StringBuilder(capitale);
        sb.deleteCharAt(indexLetter);
        System.out.println(sb.toString());
        
        /*while (!capitale.equals("Sri Jayawardenapura Kotte")) {
         capitale = countries.get(new Random().nextInt(countries.size())).getCapital();
         }*/
        ArrayList<Similitude> similitudes = new ArrayList<>();
        for (Country country : countries) {
            if (!country.getCapital().equals(capitale)) {
                similitudes.add(new Similitude(levenshteinDistance(capitale, country.getCapital()), country.getCapital()));
            }
        }
        Collections.sort(similitudes, new Comparator<Similitude>() {
            @Override
            public int compare(Similitude o1, Similitude o2) {
                return o1.getCoefSimilitude() - o2.getCoefSimilitude();
            }
        });
        System.out.println(capitale);
        System.out.println(similitudes.get(0));
        System.out.println(similitudes.get(1));
        System.out.println(similitudes.get(2));
        int i = 1;
        ArrayList<String> answers = new ArrayList<>();
        while (i < 4) {
            int k = new Random().nextInt(15);
            boolean alreadyPicked = false;
            for (String anAnswer : answers) {
                if (anAnswer.equals(similitudes.get(k).getNomCapitale())) {
                    alreadyPicked = true;
                }
            }
            if (!alreadyPicked) {
                System.out.println(similitudes.get(k).getNomCapitale());
                answers.add(similitudes.get(k).getNomCapitale());
                similitudes.remove(k);
                i++;
            }
        }
    }

    public static class Similitude {

        public int coefSimilitude;
        public String nomCapitale;

        public Similitude(int coefSimilitude, String nomCapitale) {
            this.coefSimilitude = coefSimilitude;
            this.nomCapitale = nomCapitale;
        }

        public int getCoefSimilitude() {
            return coefSimilitude;
        }

        public void setCoefSimilitude(int coefSimilitude) {
            this.coefSimilitude = coefSimilitude;
        }

        public String getNomCapitale() {
            return nomCapitale;
        }

        public void setNomCapitale(String nomCapitale) {
            this.nomCapitale = nomCapitale;
        }

        @Override
        public String toString() {
            return "Similitude{" + "coefSimilitude=" + coefSimilitude + ", nomCapitale=" + nomCapitale + '}';
        }

    }

    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static int computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];

        for (int i = 0; i <= lhs.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 1; j <= rhs.length(); j++) {
            distance[0][j] = j;
        }

        for (int i = 1; i <= lhs.length(); i++) {
            for (int j = 1; j <= rhs.length(); j++) {
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));
            }
        }

        return distance[lhs.length()][rhs.length()];
    }

    public static int levenshteinDistance(CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances                                                       
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0                                 
        for (int i = 0; i < len0; i++) {
            cost[i] = i;
        }

        // dynamically computing the array of distances                                  
        // transformation cost for each letter in s1                                    
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1                             
            newcost[0] = j;

            // transformation cost for each letter in s0                                
            for (int i = 1; i < len0; i++) {
                // matching current letters in both strings                             
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation                               
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

                // keep minimum cost                                                    
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays                                                 
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings        
        return cost[len0 - 1];
    }
}
