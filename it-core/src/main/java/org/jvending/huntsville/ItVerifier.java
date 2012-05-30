package org.jvending.huntsville;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jvending.huntsville.commands.CommandExecutor;
import org.jvending.huntsville.commands.ExecutionException;

public class ItVerifier
{

    private static final String SDK = System.getProperty( "ANDROID_SDK" ) + "/platform-tools/adb";

    public File saveLog( File tempFileDir, File targetDirectory )
        throws IOException, ExecutionException
    {
        return saveLog( tempFileDir, targetDirectory, null, null );
    }

    public File saveLog( File tempFileDir, File targetDirectory, LogPriority priority )
        throws IOException, ExecutionException
    {
        return saveLog( tempFileDir, targetDirectory, priority, null );
    }

    public File saveLog( File tempFileDir, File targetDirectory, LogPriority priority, String tag )
        throws IOException, ExecutionException
    {
        if ( tempFileDir == null || targetDirectory == null )
        {
            throw new IllegalArgumentException( "Required parameter is null" );
        }

        if ( !tempFileDir.exists() )
        {
            if ( !tempFileDir.mkdirs() )
            {
                throw new IOException( "Unable to create temp file directory" );
            }
        }

        if ( priority == null )
        {
            priority = LogPriority.DEBUG;
        }

        if ( tag == null || tag.isEmpty() )
        {
            tag = "*";
        }

        File tempFileOnDevice = new File( tempFileDir, "it-" + System.currentTimeMillis() + ".txt" );

        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();

        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );

        commands.add( "logcat" );

        commands.add( "-d" );

        commands.add( "-f" );
        commands.add( tempFileOnDevice.getAbsolutePath() );

        commands.add( "-s" );
        commands.add( tag + ":" + priority.mLevel );

        executor.executeCommand( SDK, commands, new File( "." ), false );

        return pullFileFromDevice( tempFileOnDevice, targetDirectory );
    }

    public File pullFileFromDevice( File sourceFileName, File targetDirectory )
        throws IOException, ExecutionException
    {
        if ( sourceFileName == null || targetDirectory == null )
        {
            throw new IllegalArgumentException( "Required parameter is null" );
        }

        if ( !targetDirectory.exists() )
        {
            if ( !targetDirectory.mkdirs() )
            {
                throw new IOException( "Unable to create target directory" );
            }
        }

        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();
        List<String> commands = new ArrayList<String>();
        commands.add( "pull" );

        commands.add( sourceFileName.getAbsolutePath() );

        executor.executeCommand( SDK, commands, targetDirectory, false );

        deleteLog( sourceFileName );
        return new File( targetDirectory, sourceFileName.getName() );
    }

    public void clearLog()
        throws ExecutionException
    {
        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();

        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );

        commands.add( "logcat" );

        commands.add( "-c" );
        executor.executeCommand( SDK, commands, new File( "." ), false );
    }

    private void deleteLog( File log )
        throws ExecutionException
    {
        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();

        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );

        commands.add( "rm" );

        commands.add( log.getAbsolutePath() );
        executor.executeCommand( SDK, commands, new File( "." ), false );
    }
}
