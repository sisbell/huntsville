package org.jvending.huntsville;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogcatConfig
{
    private LogPriority priority;

    private String tag;

    private List<LogBuffer> buffers;

    private LogFormat format;

    public LogcatConfig( LogPriority priority, String tag, LogFormat format, List<LogBuffer> buffers )
    {
        this.priority = ( priority != null ) ? priority : LogPriority.DEBUG;
        this.tag = ( tag != null && !tag.isEmpty() ) ? tag : "*";
        this.buffers = ( buffers != null ) ? new ArrayList<LogBuffer>(buffers) : Arrays.asList( LogBuffer.MAIN, LogBuffer.SYSTEM );
        this.format = ( format != null ) ? format : LogFormat.BRIEF;
    }

    public LogcatConfig()
    {
        this( null, null, null, null );
    }
    
    public LogcatConfig createCopy() {
        return new LogcatConfig(priority, tag, format, buffers);
    }

    public LogPriority getPriority()
    {
        return priority;
    }

    public void setPriority( LogPriority priority )
    {
        this.priority = priority;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag( String tag )
    {
        this.tag = tag;
    }

    public List<LogBuffer> getBuffers()
    {
        return new ArrayList<LogBuffer>( buffers );
    }

    public void setBuffers( List<LogBuffer> buffers )
    {
        if ( buffers == null || buffers.isEmpty() )
        {
            throw new IllegalArgumentException( "Buffers is empty or null" );
        }
        this.buffers = buffers;
    }

    public void addBuffer(LogBuffer buffer) {
        buffers.add( buffer );
    }
    
    public LogFormat getFormat()
    {
        return format;
    }

    public void setFormat( LogFormat format )
    {
        this.format = format;
    }
}
