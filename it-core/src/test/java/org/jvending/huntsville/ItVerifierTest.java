package org.jvending.huntsville;

import java.io.File;
import java.util.logging.Logger;

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
        File targetDir = new File( "target/logs" );
        File tempFileDir = new File("/sdcard");
        ItVerifier it = new ItVerifier();
        File log = it.saveLog( tempFileDir, targetDir );
        Logger.getAnonymousLogger().info( "Saved file:  " + log.getAbsolutePath() );
    }
}
