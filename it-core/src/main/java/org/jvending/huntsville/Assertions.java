package org.jvending.huntsville;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.jvending.huntsville.commands.CommandExecutor;
import org.jvending.huntsville.commands.ExecutionException;

public class Assertions
{
    public void assertFileExists( File file )
        throws ExecutionException
    {
        CommandExecutor executor = executeLsCommand( file );
        Assert.assertTrue( executor.getStandardOut().contains( file.getName() ) );
    }

    public void assertFileDoesNotExist( File file )
        throws ExecutionException
    {
        CommandExecutor executor = executeLsCommand( file );
        Assert.assertFalse( executor.getStandardOut().contains( file.getName() ) );
    }

    public void assertPackageInstalled( String appPackage )
        throws ExecutionException
    {
        Assert.assertTrue( executePackagesCommands().getStandardOut().contains( appPackage ) );
    }
    
    public void assertPackagesInstalled( List<String> appPackages )
        throws ExecutionException
    {
        String packages = executePackagesCommands().getStandardOut();
        
        for(String app :appPackages) {
            if(!packages.contains( app )) {
                Assert.fail( "Missing package: " + app );
            }
        } 
    }    

    private CommandExecutor executePackagesCommands()
        throws ExecutionException {
        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();
        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );
        commands.add( "cat" );
        commands.add( "/data/system/packages.list" );

        executor.executeCommand( ItVerifier.SDK, commands, new File( "." ), false );
        return executor;
    }
    private CommandExecutor executeLsCommand( File file )
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
