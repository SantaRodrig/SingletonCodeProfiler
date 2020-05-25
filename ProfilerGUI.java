import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ProfilerGUI extends JFrame
{
    //private JPanel stats;
    private Map<String,ArrayList<TimeSpan>>map;
    private Map<String,Integer>mapCountTracker;
    

    public ProfilerGUI(Map<String,ArrayList<TimeSpan>>map, Map<String,Integer>mapCountTracker)
    {
        super("Profiler Stats");
        this.map =map;
        this.mapCountTracker = mapCountTracker;
    
        System.out.println("In the ProfilerGUI constructor");
        setGUI();

    }

    public void setGUI()
    {
        System.out.println("In the setGUI: ");
        String[] timeSpanColumnsSummary = new String[]{"Id","AVG","Shortest","Longest"};
        String[] timeSpanColumnsDetails = new String[]{"Id", "Start","Stop","Start Message", "Stop Message"};
        String[] countColumns = new String []{"Id", "Count"};

        String[][] rowsSummary = getSummaryData();
        String[][] rowsDetails = getTimeSpanDetails();
        String[][] countDetails = getCountDetails();

        JTable dataSummaryTable = new JTable(rowsSummary,timeSpanColumnsSummary);
        JTable dataDetailsTable = new JTable(rowsDetails,timeSpanColumnsDetails);
        JTable countTable = new JTable(countDetails,countColumns);

        JScrollPane summaryScrollPane = new JScrollPane(dataSummaryTable);
        JScrollPane detailScrollPane = new JScrollPane(dataDetailsTable);
        JScrollPane countScrollPane = new JScrollPane(countTable);
        
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("TimeSpan Summary", summaryScrollPane);
        tabs.addTab("TimeSpan Details", detailScrollPane);
        tabs.addTab("Counts", countScrollPane);
        
        
        add(tabs,BorderLayout.CENTER);
        

        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(300,300);
        setVisible(true);
    }

    public String[][] getSummaryData()
    {   
        int x = 0;
        ArrayList<ArrayList<String>>dataSummaryArr = new ArrayList<ArrayList<String>>();
        for(Map.Entry<String,ArrayList<TimeSpan>>entry : map.entrySet())
        {
            long sum = 0;
            double avg = 0;
            long shortest = Long.MAX_VALUE;
            long largest = 0;
            String key = entry.getKey();
            ArrayList<TimeSpan>durations = entry.getValue();
            for(int i = 0; i < durations.size();i++)
            {
                if(shortest>durations.get(i).getDifferenceNS())
                {
                    shortest = durations.get(i).getDifferenceNS();
                }

                if(largest<durations.get(i).getDifferenceNS())
                {
                    largest = durations.get(i).getDifferenceNS(); 
                }

                sum = sum + durations.get(i).getDifferenceNS();
            }
            dataSummaryArr.add(new ArrayList<String>());
            avg = sum / durations.size();
            dataSummaryArr.get(x).add(key);
            dataSummaryArr.get(x).add(Double.toString(avg));
            dataSummaryArr.get(x).add(Long.toString(shortest));
            dataSummaryArr.get(x).add(Long.toString(largest));

            x++;
            
        }

        String rows[][] = new String[dataSummaryArr.size()][];
        for(int i = 0; i < dataSummaryArr.size(); i++)
        {
            String row[] = new String[dataSummaryArr.get(i).size()];
            row = dataSummaryArr.get(i).toArray(row);
            rows[i] =row;
        }

        return rows;
    }

    public String[][] getTimeSpanDetails()
    {
        int x = 0;
        
        ArrayList<ArrayList<String>>timeDetailsArr = new ArrayList<ArrayList<String>>();
        for(Map.Entry<String,ArrayList<TimeSpan>>entry: map.entrySet())
        {
            String key = entry.getKey();
            ArrayList<TimeSpan>durations = entry.getValue();
            long start = 0;
            long stop = 0;
            String startMessage = " ";
            String endMessage = " ";

            for(int i = 0; i <durations.size();i++)
            {
                timeDetailsArr.add(new ArrayList<String>());

                start = durations.get(i).getStartTime();
                stop = durations.get(i).getEndTime();
                startMessage = durations.get(i).getBeginMessage();
                endMessage = durations.get(i).getEndMessage();

                timeDetailsArr.get(x).add(key);
                timeDetailsArr.get(x).add(Long.toString(start));
                timeDetailsArr.get(x).add(Long.toString(stop));
                timeDetailsArr.get(x).add(startMessage);
                timeDetailsArr.get(x).add(endMessage);
                x++;
            }
            

           
        }

        String rows[][] = new String[timeDetailsArr.size()][];
        for(int i = 0; i < timeDetailsArr.size(); i++)
        {
            String row[] = new String[timeDetailsArr.get(i).size()];
            row = timeDetailsArr.get(i).toArray(row);
            rows[i] = row;
        }

        return rows;
    }

    public String[][] getCountDetails()
    {
        int x = 0;
        ArrayList<ArrayList<String>>countDetails = new ArrayList<ArrayList<String>>();
        for(Map.Entry<String,Integer>entry: mapCountTracker.entrySet())
        {
            String key = entry.getKey();

            countDetails.add(new ArrayList<String>());
            countDetails.get(x).add(key);
            countDetails.get(x).add(Integer.toString(entry.getValue()));
            x++;
        }

        String rows[][] = new String[countDetails.size()][];
        for(int i = 0;i<countDetails.size();i++)
        {
            String row[] = new String[countDetails.get(i).size()];
            row = countDetails.get(i).toArray(row);
            rows[i] = row;
        }
        return rows;
    }

}