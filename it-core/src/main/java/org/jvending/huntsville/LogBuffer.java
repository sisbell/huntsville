package org.jvending.huntsville;

public enum LogBuffer {

    MAIN("main"), SYSTEM("system"), RADIO("radio"), EVENTS("events");

    public final String buffer;

    LogBuffer( String buffer )
    {
        this.buffer= buffer;
    }    
}
