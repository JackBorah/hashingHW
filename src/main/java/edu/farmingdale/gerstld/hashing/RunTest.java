/**
 * Gerstl CSC329 code. (c) 2023, v1.3 David Gerstl, all rights reserved
 * for use in my CSC programming classes only. No consent is given for posting 
 * except to the Farmingdale State College LMS.
 * 
 */
package edu.farmingdale.gerstld.hashing;

/**
 * This interface denotes classes that can run a test
 *
 * @author gerstl
 */
public interface RunTest {

    /**
     * Runs a test.
     *
     * @return the empty string for a successful test. A descriptive error
     * otherwise
     */
    String runTest();

    /**
     * Returns the display name of the test
     *
     * @return String representation of the test name, simple class name by
     * default.
     */
    default String getTestName() {
        // if they do not implement, use the class name 
        return this.getClass().getSimpleName();
    }
}