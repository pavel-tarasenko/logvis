package org.gradle;


public class RuntimeTimestampCalculator implements TimestampCalculator
{
    private long prevMillis = -1;
    
    public long calculateLineTimestamp(String line)
    {
        long millis = System.currentTimeMillis();
        
        long diff = 0;
        if (prevMillis > 0)
        {
            diff = millis - prevMillis;
        }
        
        prevMillis = millis;
        
        return diff;
    }
}
