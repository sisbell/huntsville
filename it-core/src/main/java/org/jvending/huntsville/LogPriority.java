package org.jvending.huntsville;

public enum LogPriority 
{

    DEBUG("D"), INFO("I"), WARNING("W"), ERROR("E"), FATAL("F"), SILENT("S");

    public final String mLevel;

    LogPriority( String level )
    {
        mLevel = level;
    }
}
