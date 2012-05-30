package org.jvending.huntsville;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ItVerifierTest
    extends TestCase
{
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public ItVerifierTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ItVerifierTest.class );
    }

    @Override
    protected void setUp()
        throws Exception
    {
        super.setUp();
        ItVerifier it = new ItVerifier();
        it.clearLog();
    }

    public void testLog()
        throws Exception
    {
        File tempFile = new File("/sdcard/it-" + System.currentTimeMillis() + ".txt");
        ItVerifier it = new ItVerifier();
        it.saveLog( new File( "it.log"), tempFile );
    }
}
