package org.jvending.huntsville;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jvending.huntsville.commands.CommandExecutor;

public class ItVerifier
{

    private static final String SDK = System.getProperty( "ANDROID_SDK" ) + "/platform-tools/adb";

    public void saveLog( File outputFile )
        throws Exception
    {
        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();
        String logFile = "/sdcard/it-" + Math.random() + ".txt";
        List<String> commands = new ArrayList<String>();
        commands.add( "shell" );

        commands.add( "logcat" );

        commands.add( "-d" );

        commands.add( "-f" );
        commands.add( logFile );

        executor.executeCommand( SDK, commands, new File( "." ), false );

        pullFile( logFile );

    }

    public void pullFile( String fileName )
        throws Exception
    {
        CommandExecutor executor = CommandExecutor.Factory.createDefaultCommmandExecutor();
        List<String> commands = new ArrayList<String>();
        commands.add( "pull" );

        commands.add( fileName );

        System.out.println( fileName );

        executor.executeCommand( SDK, commands, new File( "." ), false );
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
}
