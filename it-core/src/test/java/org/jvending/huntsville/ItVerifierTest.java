package org.jvending.huntsville;

import java.io.File;
import java.util.Arrays;
import java.util.Properties;
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
        File tempFileDir = new File( "/sdcard" );

        ItVerifier it = new ItVerifier();
        File log = it.saveLog( tempFileDir, targetDir, null );

        Logger.getAnonymousLogger().info( "Saved file:  " + log.getAbsolutePath() );
    }

    public void testReadProperties()
        throws Exception
    {
        ItVerifier it = new ItVerifier();
        Properties p = it.readPropertiesFileFromDevice( new File( "/default.prop" ) );
        assertTrue( p.containsKey( "ro.debuggable" ) );
    }

    public void testFileExists()
        throws Exception
    {
        DeviceAssert.assertFileExists( new File( "/default.prop" ) );
    }

    public void testFileDoesNotExist()
        throws Exception
    {
        DeviceAssert.assertFileDoesNotExist( new File( "/dummy.prop" ) );
    }

    public void testPackageInstalled()
        throws Exception
    {
        DeviceAssert.assertPackageInstalled( "com.android.vending" );
    }

    public void testPackagesInstalled()
        throws Exception
    {
        DeviceAssert.assertPackagesInstalled( Arrays.asList( "com.android.vending", "com.google.android.browser" ) );
    }

    public void testEnvironmentPropertyExists()
        throws Exception
    {
        DeviceAssert.assertEnvironmentPropertyExists( "SHELL" );
    }

    public void testEnvironmentPropertyValue()
        throws Exception
    {
        DeviceAssert.assertEnvironmentPropertyValue( "ANDROID_DATA", "/data" );
    }

    public void testDevicePropertyExists()
        throws Exception
    {
        DeviceAssert.assertDevicePropertyExists( "ro.debuggable" );
    }

    public void testDevicePropertyValue()
        throws Exception
    {
        DeviceAssert.assertDevicePropertyValue( "ro.debuggable", "1" );

    }

    public void testLogContains()
        throws Exception
    {
        DeviceAssert.assertLogContainsMessage( "/" );
    }
    
    public void testLogDoesNotContain()
        throws Exception
    {
        DeviceAssert.assertLogDoesNotContainMessage( "foobar" );
    }

    /*
    public void testSetEnvironmentProperty() throws Exception {
        ItVerifier it = new ItVerifier();
        String testName = "test" + System.currentTimeMillis();
        it.setEnvironmentProperty( testName, "testValue" );
        
        DeviceAssert.assertEnvironmentPropertyExists( testName );
    }
    */
}
