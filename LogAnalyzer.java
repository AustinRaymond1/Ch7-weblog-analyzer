/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String name)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(name);
    }

    public int numberofAccesses(){
        int total_entries = 0;
        for (int count = 0; count < hourCounts.length; ++count){
            total_entries += hourCounts[count];
        }
        return total_entries;
    }
    
    public int busiestHour(){
        int count, highestHour = 0;
        for(count = 0; count < hourCounts.length; ++count){
            if (highestHour > hourCounts[count]){
                highestHour = highestHour;
            }
            else if (highestHour < hourCounts[count]){
                highestHour = hourCounts[count];
            }
        }
        return highestHour;
    }
    public int quietestHour(){
        int count, lowestHour = hourCounts[0];
        for(count = 0; count < hourCounts.length; ++count){
            if (lowestHour < hourCounts[count]){
                lowestHour = lowestHour;
            }
            else if (lowestHour > hourCounts[count] || lowestHour == hourCounts[count]){
                lowestHour = hourCounts[count];
            }
        }
        return lowestHour;
    }
    public int busiestTwoHour(){
        int[] two_hours = new int[2];
        int count, highestHour = 0;
        for(count = 0; count < hourCounts.length; ++count){
            if (highestHour > hourCounts[count]){
                highestHour = highestHour;
            }
            else if (highestHour < hourCounts[count]){
                highestHour = hourCounts[count];
                two_hours[0] = highestHour;
                if (count == 0){
                    if (hourCounts[count] > hourCounts[count+1]){
                        two_hours[0] = hourCounts[count];
                    }
                    else if (hourCounts[count] < hourCounts[count+1]){
                        two_hours[0] = hourCounts[count+1];
                    }
                }
                else{
                if (hourCounts[count+1] > hourCounts[count-1]){
                    two_hours[1] = hourCounts[count+1];
                }
                else if (hourCounts[count+1] < hourCounts[count-1]){
                    two_hours[0] = hourCounts[count-1];
                    two_hours[1] = highestHour;
                }
            }
            }
        }
        return two_hours[0];
    }
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
