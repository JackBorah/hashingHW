/**
 * Gerstl CSC329 code. (c) 2023, v1.4 David Gerstl, all rights reserved
 * for use in my CSC programming classes only. No consent is given for posting 
 * except to the Farmingdale State College LMS.
 * 
 */
package edu.farmingdale.gerstld.hashing;

/**
 *
 * @author gerstld
 */
public class ShortHashTableTest implements RunTest {

    public String runTest() {
        // compare with HashSet?
        // note that the name is slightly different, but fully specifying it
        // (instead of importing it) removes any potential misreading 
        var standard = new java.util.HashMap<Integer, String>(13);
        var mine = new HashTable<Integer, String>(13);
        if (null != standard.put(50, "fifty")) {
            return "Failed at A0001";
        }
        if (null != mine.put(50, "fifty")) {
            return "Failed at A0002";
        }
        if (null != standard.put(60, "sixty")) {
            return "Failed at A0003";
        }
        if (null != mine.put(60, "sixty")) {
            return "Failed at A0004";
        }
        if (null != standard.put(70, "seventy")) {
            return "Failed at A0005";
        }
        if (null != mine.put(70, "seventy")) {
            return "Failed at A0006";
        }
        if (!mine.sameAs(standard)) {
            return "Failed at A0007";
        }
        System.out.println("The hashtable currently contains " + mine);
        System.out.println("The java built in HashMap currently contains " + standard);
        System.out.println("Note that the order may be different");
        String prior = standard.put(60, "soixante");
        if (!prior.equals("sixty")) {
            return "Failed at A0008";
        }
        if (mine.sameAs(standard)) {
            return "Failed at A0009";
        }
        prior = mine.put(60, "soixante");
        if (!prior.equals("sixty")) {
            return "Failed at A00010";
        }
        if (!mine.sameAs(standard)) {
            return "Failed at A00011";
        }
        prior = mine.put(70, "soixante-dix");
        if (!prior.equals("seventy")) {
            return "Failed at A00012";
        }
        if (mine.sameAs(standard)) {
            return "Failed at A00013";
        }
        prior = standard.put(70, "soixante-dix");
        if (!prior.equals("seventy")) {
            return "Failed at A00014";
        }
        if (!mine.sameAs(standard)) {
            return "Failed at A00015";
        }

        // now test remove()
        String seventy = mine.remove(70);
        if (!seventy.equals("soixante-dix")) {
            return "Failed at A00016";
        }
        if (mine.sameAs(standard)) {
            return "Failed at A00017";
        }
        seventy = standard.remove(70);
        if (!seventy.equals("soixante-dix")) {
            return "Failed at A00018";
        }
        if (!mine.sameAs(standard)) {
            return "Failed at A00019";
        }
        System.out.println("The hashtable currently contains " + mine);
        System.out.println("The java built in HashMap currently contains " + standard);
        System.out.println("Note that the order may be different");
        return "";
    }
}
