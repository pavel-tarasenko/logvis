package org.gradle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeuristicTimestampCalculator implements TimestampCalculator
{
    private static Pattern p = Pattern.compile("(\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d)\\s*(.*)");
    
    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS");

    public long calculateLineTimestamp(String line)
    {
        try
        {
            Matcher matcher = p.matcher(line);
            if (matcher.matches()) 
            {
                String timestamp = matcher.group(1);
                Date parsedDate = dateFormat.parse(timestamp);
                long millis = parsedDate.getTime();
                return millis;
            }
        }
        catch (Throwable e) {};
        
        return -1;
    }
}
