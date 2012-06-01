package org.jvending.huntsville;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.FileUtils;
import org.jvending.huntsville.commands.CommandExecutor;
import org.jvending.huntsville.commands.ExecutionException;

public class Performance
{
    public static File vmstat( File targetDirectory, int timeout )
        throws IOException, ExecutionException
    {

        if ( !targetDirectory.exists() )
        {
            targetDirectory.mkdirs();
        }
        
        File log = new File( targetDirectory, "vmstat-" + System.currentTimeMillis() + ".txt" );

        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();
        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );

        commands.add( "vmstat" );

        try
        {
            executor.executeCommand( ItVerifier.SDK, commands, targetDirectory, false, timeout );
        }
        catch ( ExecutionException e )
        {
            //timeout, killed
        }

        String data = executor.getStandardOut();
        FileUtils.fileWrite( log.getAbsolutePath(), data );
        return log;

    }
}
