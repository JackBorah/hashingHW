/**
 * Gerstl CSC329 code. (c) 2023, v1.4 David Gerstl, all rights reserved
 * for use in my CSC programming classes only. No consent is given for posting 
 * except to the Farmingdale State College LMS.
 * 
 */
package edu.farmingdale.gerstld.hashing;

import java.util.Random;

/**
 *
 * @author gerstld
 */
public class LargeHashTableTest implements RunTest {

    public String runTest() {
        final int TABLE_SIZE = 307; // it's a prime. 
        final int TEST_SIZE = TABLE_SIZE * 200;
        var random = new Random();
        // this is defined in java, so not necessary to compute, but I do so 
        // you can see how I compute it
        final int MAX_WIDTH = (int) Math.log10(Integer.MAX_VALUE) + 1;
        System.err.println("MAX_WIDTH is " + MAX_WIDTH);
        StringBuilder sb = new StringBuilder();
        // note that the name is slightly different, but fully specifying it
        // (instead of importing it) removes any potential misreading 
        var standard = new java.util.HashMap<String, Integer>(TABLE_SIZE);
        var mine = new HashTable<String, Integer>(TABLE_SIZE);
        int myCountOfItems = 0;
        int removeIterationCount = 0;
        int putIterationCount = 0;
        int duplicatePutCount = 0;
        int duplicateRemoveCount = 0;
        int foundGetCount = 0;
        for (int i = 0; i < TEST_SIZE; ++i) {
            // About 1/3 of the time, let's try to do a remove. We remove a 
            // random string, which will be a duplicate rarely (but probably 
            // not never). I'll have the test print statistics
            // we should get some duplicates
            String iString = generateRandomString(random, TEST_SIZE, MAX_WIDTH);
            int whatToDo = random.nextInt(4); // 0, 1, 2, or 3
            if (0 == whatToDo) {
                // remove
                ++removeIterationCount;
                Integer i1 = mine.remove(iString);
                Integer i2 = standard.remove(iString);
                if (null != i2) {
                    // thisItemExisted
                    --myCountOfItems;
                    ++duplicateRemoveCount;
                }
                if (i1 != null && !i1.equals(i2)) {
                    return "Failed at A0020";
                }
                // check that both found or both did not
                if ((i1 == null && i2 != null) || (i1 != null && i2 == null)) {
                    return "Failed at A0021";
                }
            } else if (1 == whatToDo) {
                // get
                Integer i1 = mine.get(iString);
                Integer i2 = standard.get(iString);
                if (null != i2) {
                    ++foundGetCount;
                }
                if (i1 != null && !i1.equals(i2)) {
                    return "Failed at A0022";
                }
                // check that both found or both did not
                if ((i1 == null && i2 != null) || (i1 != null && i2 == null)) {
                    return "Failed at A0023";
                }
            } else {
                // put 
                ++putIterationCount;
                // note that these two are unrelated, so sometime we'll be remapping
                // from an existing # to another #.
                Integer i1 = mine.put(iString, i);
                Integer i2 = standard.put(iString, i);
                if (null == i2) {
                    // if i2 did not return something, then this is a new item
                    // so increase the count of items we should have
                    ++myCountOfItems;
                } else {
                    ++duplicatePutCount;
                }
                if (i1 != null && !i1.equals(i2)) {
                    return "Failed at A0024";
                }
                if ((i1 == null && i2 != null) || (i1 != null && i2 == null)) {
                    return "Failed at A0025";
                }
                i1 = mine.get(iString);
                i2 = standard.get(iString);
                if (i1 != null && !i1.equals(i2)) {
                    return "Failed at A0026";
                }
                if ((i1 == null && i2 != null) || (i1 != null && i2 == null)) {
                    return "Failed at A0027";
                }
            }
        } // loop
        // System.err.println("The list is "+mine);
        if (!mine.sameAs(standard)) {
            return "Failed at A0028";
        }
        if (myCountOfItems != mine.size()) {
            System.err.println("I think I should have " + myCountOfItems
                    + " items, but the container shows " + mine.size());
            return "Failed at A0029";
        }
        System.err.println("In the end, the tables are of size " + mine.size());
        System.err.println("We did " + TEST_SIZE + " iterations");
        var df = new java.text.DecimalFormat("##.#");
        System.err.println("\tOf these " + putIterationCount + " ("
                + df.format(100.0 * putIterationCount / TEST_SIZE) + "%) were inserts,  "
                + removeIterationCount + "("
                + df.format(100.0 * removeIterationCount / TEST_SIZE)
                + "%) were removes, and " + (TEST_SIZE - removeIterationCount - putIterationCount)
                + " were gets");
        System.err.println("\tCount of duplicate puts: " + duplicatePutCount
                + " (" + df.format((100.0 * duplicatePutCount) / putIterationCount) + "%)");
        System.err.println("\tCount of duplicate removes:" + duplicateRemoveCount
                + " (" + df.format((100.0 * duplicateRemoveCount) / removeIterationCount) + "%)");
 System.err.println("\tCount of found gets:" + foundGetCount
                + " (" + df.format((100.0 * foundGetCount) / 
                        (TEST_SIZE - removeIterationCount - putIterationCount)
                        ) + "%)");

        return "";
    }

    String generateRandomString(Random random, int maxValue, int width) {
        StringBuilder sb = new StringBuilder();
        int aNumber = random.nextInt(maxValue);
        String sNumber = Integer.toString(aNumber);
        // now pad it.
        sb.delete(0, sb.length());
        while (sb.length() < width - sNumber.length()) {
            sb.append("0");
        }
        sb.append(sNumber);
        return sb.toString();
    } // generateRandomString()
}
