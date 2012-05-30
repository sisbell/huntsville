package org.jvending.huntsville;

public enum LogPriority 
{

    DEBUG("D"), INFO("I"), WARNING("W"), ERROR("E"), FATAL("F"), SILENT("S");

    public final String level;

    LogPriority( String level )
    {
        this.level = level;
    }   
}
