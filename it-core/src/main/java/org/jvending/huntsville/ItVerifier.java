package org.jvending.huntsville;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jvending.huntsville.commands.CommandExecutor;

public class ItVerifier
{

    private static final String SDK = System.getProperty( "ANDROID_SDK" ) + "/platform-tools/adb";

    public File saveLog( File tempFileDir, File targetDirectory, LogPriority priority )
        throws Exception
    {
        if ( !tempFileDir.exists() )
        {
            tempFileDir.mkdirs();
        }

        File tempFileOnDevice = new File( tempFileDir, "it-" + System.currentTimeMillis() + ".txt" );

        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();

        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );

        commands.add( "logcat" );

        commands.add( "-d" );

        commands.add( "-f" );
        commands.add( tempFileOnDevice.getAbsolutePath() );

        if ( priority != null )
        {
            commands.add( "-s" );
            commands.add( "*:" + priority.mLevel );
        }
        executor.executeCommand( SDK, commands, new File( "." ), false );

        return pullFile( tempFileOnDevice, targetDirectory );

    }

    public File pullFile( File sourceFileName, File targetDirectory )
        throws Exception
    {
        if ( !targetDirectory.exists() )
        {
            targetDirectory.mkdirs();
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
        throws Exception
    {
        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();

        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );

        commands.add( "logcat" );

        commands.add( "-c" );
        executor.executeCommand( SDK, commands, new File( "." ), false );
    }

    private void deleteLog( File log )
        throws Exception
    {
        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();

        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );

        commands.add( "rm" );

        commands.add( log.getAbsolutePath() );
        executor.executeCommand( SDK, commands, new File( "." ), false );
    }
}
