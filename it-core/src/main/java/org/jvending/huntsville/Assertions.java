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
        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();
        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );
        commands.add( "ls" );
        commands.add( file.getParent() );

        executor.executeCommand( ItVerifier.SDK, commands, new File( "." ), false );
        
        Assert.assertTrue( executor.getStandardOut().contains( file.getName() ) );
    }
}
