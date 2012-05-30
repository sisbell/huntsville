package org.jvending.huntsville;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jvending.huntsville.commands.CommandExecutor;

public class ItVerifier
{

    private static final String SDK = System.getProperty( "ANDROID_SDK" ) + "/platform-tools/adb";

    public void saveLog( File outputFile, File tempFileOnDevice )
        throws Exception
    {
        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();

        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );

        commands.add( "logcat" );

        commands.add( "-d" );

        commands.add( "-f" );
        commands.add( tempFileOnDevice.getAbsolutePath() );

        executor.executeCommand( SDK, commands, new File( "." ), false );

        pullFile( tempFileOnDevice );

    }

    public void pullFile( File fileName )
        throws Exception
    {
        File target = new File( "target/logs" );
        if ( !target.exists() )
        {
            target.mkdirs();
        }

        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();
        List<String> commands = new ArrayList<String>();
        commands.add( "pull" );

        commands.add( fileName.getAbsolutePath() );

        executor.executeCommand( SDK, commands, target, false );
        
        deleteLog(fileName);
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
    
    private void deleteLog(File log) throws Exception {
        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();

        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );

        commands.add( "rm" );

        commands.add( log.getAbsolutePath() );
        executor.executeCommand( SDK, commands, new File( "." ), false );       
    }
}
