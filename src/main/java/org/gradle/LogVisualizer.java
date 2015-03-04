package org.gradle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;

public class LogVisualizer
{
    public static void main(String[] args) throws ParseException, IOException
    {
        InputStream is;
        TimestampCalculator tsCalc;
        if (args.length > 0)
        {
            is = new FileInputStream(args[0]);
            tsCalc = new HeuristicTimestampCalculator();
        }   
        else
        {
            is = System.in;
            tsCalc = new RuntimeTimestampCalculator();
        }
            
        InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
        try(BufferedReader br = new BufferedReader(isr))
        {        
            long prevMillis = -1;
            
            String line;
            while ((line = br.readLine()) != null)
            {
                long millis = tsCalc.calculateLineTimestamp(line);
                
                if (millis == -1)
                {
                    System.err.println("Cannot extract timestamp from line: " + line);
                    continue;
                }
                
                if (prevMillis < 0)
                    prevMillis = millis;
    
                String formattedLine = formatLine(millis - prevMillis, line);
                System.out.println(formattedLine);
                
                prevMillis = millis;
            }
        }
    }

    private static String formatLine(long durationMillis, String line)
    {
        StringBuilder sb = new StringBuilder();
        long emptyLinesCount = Math.round(durationMillis * 0.1);
        for (int i = 0; i < emptyLinesCount; i++)
        {
            if (i == emptyLinesCount-1)
                sb.append(" v    " + durationMillis + " ms.\n");
            else
                sb.append(" |\n");
        }
        sb.append(line);
        
        return sb.toString();
    }
}
