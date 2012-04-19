import java.awt.datatransfer.FlavorEvent;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.lang.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 4/17/12
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class b_MinimumScalarProduct {
    //public static int[] choiceTree;
    public static Milkshake[] milkshakes;
    public static Customer[] customers;
    public static List<Integer[]> answers;

    public static class Milkshake {
        public List<Integer> cust = new ArrayList<Integer>();

        public void addCustomer(int n) {
            cust.add(n);
        }
    }

    public static class Customer {
        public boolean satisfied = false;
        public List<Integer[]> prefs = new ArrayList<Integer[]>();

        public void addPref(Integer[] pref) {
            prefs.add(pref);
        }

        public boolean hasPref(Integer[] pref) {
            for (Integer[] p : prefs) {
                //System.out.println("hasPref=pref:" + pref[0] + pref[1] + " p:" + p[0] + p[1]);
                if (pref[0] == p[0] && pref[1] == p[1]) {
                    //System.out.println("hasPref=true");
                    return true;
                }
            }
            //System.out.println("hasPref=false");
            return false;
        }

        public String toString() {
            String output = "[";
            for (Integer[] p : prefs) {
                output = output + p[0] + "," + p[1] + "|";
            }
            output = output + "]";
            return output;
        }

        public boolean hasMoreShakes(int index) {
            //System.out.println("potential prefs:" + this.toString());
            for (Integer[] p : prefs) {
                if (p[0] > index) {
                    return true;
                }
            }
            return false;
        }
    }

    public static void Satisfy(boolean satiation, int index, int malted) {
        //System.out.println("About to satisfy!" + satiation + " index:" + index + " malted:" + malted);
        for (Integer cust :milkshakes[index].cust) {
            Integer[] pref = new Integer[2];
            pref[0] = index;
            pref[1] = malted;
            if (customers[cust].hasPref(pref)) {
                customers[cust].satisfied = satiation;
            }
        }
    }

    public static boolean CustomersPotentiallySatisfied(int index, int malted) {
        //check no customers are unsatisfied
        for (int c = 0; c < customers.length; c++) {
            if (!customers[c].satisfied) {
                if (!customers[c].hasMoreShakes(index)) {
                    Satisfy(false, index, malted);
                    //System.out.println("customers not potentially satisfied");
                    return false;
                }
            }
        }
        //System.out.println("customers ARE potentially satisfied");
        return true;
    }

    public static boolean DFS(Integer[] choices, int index) {
        //check if this is the end!
        String output = new String();
        for (Integer c : choices) {
            output = output + c.toString() + ",";
        }
        //System.out.println("choices:" + output + " index:" + index);
        if (index == choices.length) {
            answers.add(choices.clone());
            String derp = "[";
            for (Integer a : choices) {
                derp += a + ",";
            }
            //System.out.println("answer:" + derp);

            //System.out.println("failed on length");
            return false;
        }

        for (int i = 0; i <= 1; i++) {
            //System.out.println("  malt choice:" + i);
            choices[index] = i;
            Satisfy(true, index, choices[index]);

            if (CustomersPotentiallySatisfied(index, choices[index])) {
                if (DFS(choices, index + 1)) {
                    return true;
                }
            }
            Satisfy(false, index, choices[index]);
        }
        return false;
    }


    public static void main(String[] args) throws Exception {
        //String filename = "./codejam2008round1a/b_sample.txt";
        String filename = "./codejam2008round1a/B-small-practice.two.in";
        Scanner in = new Scanner(new File(filename));
        PrintWriter out = new PrintWriter(new File(filename + ".out"));

        int testcases = in.nextInt();

        for (int zz = 0; zz < testcases; zz++) {
            //pre-processing
            answers = new ArrayList<Integer[]>();
            int numMilk = in.nextInt();
            milkshakes = new Milkshake[numMilk];
            Integer[] choices = new Integer[numMilk];
            for (int milk = 0; milk < numMilk; milk++) {
                milkshakes[milk] = new Milkshake();
                choices[milk] = new Integer(-1);
            }

            int numCust = in.nextInt();
            customers = new Customer[numCust];
            for (int c = 0; c < numCust; c++) {
                customers[c] = new Customer();
                int numPrefs = in.nextInt();
                for (int p = 0; p < numPrefs; p++) {
                    Integer[] pref = new Integer[2];
                    pref[0] = in.nextInt() - 1;
                    pref[1] = in.nextInt();
                    customers[c].addPref(pref);
                    milkshakes[pref[0]].addCustomer(c);
                }
            }

            //processing!
            int index = 0;
            DFS(choices, 0);
            String ans = "IMPOSSIBLE";
            if (!answers.isEmpty()) {
                Integer[] malts = new Integer[answers.size()];
                int minMalt = 0;
                for (int j = 0; j < answers.size(); j++) {
                    String output = "[";
                    malts[j] = 0;
                    for (Integer a : answers.get(j)) {
                        malts[j] += a;
                        output += a + ",";
                    }
                    //System.out.println("answer:" + output + " malts:" + malts[j]);
                    if (malts[j] < malts[minMalt]) {
                        minMalt = j;
                    }
                }
                ans = "";
                for (Integer a : answers.get(minMalt)) {
                    if (ans != "") {
                        ans += " ";
                    }
                    ans += a;
                }
            }
            //out.format("Case #%d: IMPOSSIBLE\n", zz);
            //out.write("");
            System.out.format("Case #%d: %s\n", zz + 1, ans);
            out.format("Case #%d: %s\n", zz + 1, ans);
        }

        out.close();
    }

}
