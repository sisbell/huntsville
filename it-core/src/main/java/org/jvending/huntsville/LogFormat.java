package org.jvending.huntsville;

public enum LogFormat {
    BRIEF("brief"), PROCESS("process"), TAG("tag"), THREAD("thread"), RAW("raw"), TIME("time"), THREADTIME("threadtime"), LONG(
        "long");

    public final String format;

    LogFormat( String format )
    {
        this.format = format;
    }
}
