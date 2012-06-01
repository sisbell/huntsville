package org.jvending.huntsville;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import junit.framework.Assert;

import org.jvending.huntsville.commands.CommandExecutor;
import org.jvending.huntsville.commands.ExecutionException;

public class DeviceAssert
    extends Assert
{
    public static void assertFileExists( File file )
        throws ExecutionException
    {
        CommandExecutor executor = executeLsCommand( file );
        assertTrue( executor.getStandardOut().contains( file.getName() ) );
    }

    public static void assertFileDoesNotExist( File file )
        throws ExecutionException
    {
        CommandExecutor executor = executeLsCommand( file );
        assertFalse( executor.getStandardOut().contains( file.getName() ) );
    }

    public static void assertPackageInstalled( String appPackage )
        throws ExecutionException
    {
        assertTrue( executePackagesCommands().getStandardOut().contains( appPackage ) );
    }

    public static void assertNoErrorsInLog( String tag )
        throws IOException, ExecutionException
    {
        LogcatConfig c = new LogcatConfig();
        c.setPriority( LogPriority.ERROR );

        if ( tag != null )
        {
            c.setTag( tag );
        }
        assertTrue( !( new ItVerifier().getLogAsString( c ).contains( "E/" ) ) );
    }

    public static void assertNoErrorsOrWarningsInLog( String tag )
        throws IOException, ExecutionException
    {
        LogcatConfig c = new LogcatConfig();
        c.setPriority( LogPriority.WARNING );
        if ( tag != null )
        {
            c.setTag( tag );
        }
        
        String log = new ItVerifier().getLogAsString( c );
        assertTrue( !( log.contains( "E/" ) || log.contains( "W/" ) ) );
    }

    public static void assertLogDoesNotContainMessage( String message )
        throws ExecutionException, IOException
    {
        assertTrue( !( new ItVerifier().getLogAsString( null ).contains( message ) ) );
    }

    public static void assertLogContainsMessage( String message )
        throws ExecutionException, IOException
    {
        assertTrue( ( new ItVerifier().getLogAsString( null ).contains( message ) ) );
    }

    public static void assertPackagesInstalled( List<String> appPackages )
        throws ExecutionException
    {
        String packages = executePackagesCommands().getStandardOut();

        for ( String app : appPackages )
        {
            if ( !packages.contains( app ) )
            {
                fail( "Missing package: " + app );
            }
        }
    }

    public static void assertEnvironmentPropertyExists( String name )
        throws ExecutionException, IOException
    {
        ItVerifier it = new ItVerifier();
        Properties p = it.readEnvironmentVariablesFromDevice();
        assertTrue( p.containsKey( name ) );
    }

    public static void assertEnvironmentPropertiesExist( List<String> names )
        throws ExecutionException, IOException
    {
        ItVerifier it = new ItVerifier();
        Properties p = it.readEnvironmentVariablesFromDevice();
        for ( String name : names )
        {
            if ( !p.containsKey( name ) )
            {
                fail( "Missing environmental property: " + name );
            }
        }
    }

    public static void assertEnvironmentPropertyValue( String name, String value )
        throws ExecutionException, IOException
    {
        ItVerifier it = new ItVerifier();
        Properties p = it.readEnvironmentVariablesFromDevice();
        assertTrue( p.containsKey( name ) );
        assertEquals( value, p.getProperty( name ) );
    }

    public static void assertDevicePropertyExists( String name )
        throws ExecutionException, IOException
    {
        ItVerifier it = new ItVerifier();
        Properties p = it.getDeviceProperties();
        assertTrue( p.containsKey( name ) );
    }

    public static void assertDevicePropertiesExists( List<String> names )
        throws ExecutionException, IOException
    {
        ItVerifier it = new ItVerifier();
        Properties p = it.getDeviceProperties();
        for ( String name : names )
        {
            if ( !p.containsKey( name ) )
            {
                fail( "Missing device property: " + name );
            }
        }
    }

    public static void assertDevicePropertyValue( String name, String value )
        throws ExecutionException, IOException
    {
        ItVerifier it = new ItVerifier();
        Properties p = it.getDeviceProperties();
        assertTrue( p.containsKey( name ) );
        assertEquals( value, p.getProperty( name ) );
    }

    public static void assertSdkVersion( int sdk )
        throws ExecutionException, IOException
    {
        assertDevicePropertyValue( "ro.build.version.sdk", String.valueOf( sdk ) );
    }

    private static CommandExecutor executePackagesCommands()
        throws ExecutionException
    {
        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();
        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );
        commands.add( "cat" );
        commands.add( "/data/system/packages.list" );

        executor.executeCommand( ItVerifier.SDK, commands, new File( "." ), false );
        return executor;
    }

    private static CommandExecutor executeLsCommand( File file )
        throws ExecutionException
    {
        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();
        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );
        commands.add( "ls" );
        commands.add( file.getParent() );

        executor.executeCommand( ItVerifier.SDK, commands, new File( "." ), false );
        return executor;
    }

}
