import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Profiler 
{
    private static Profiler instance = null;
    private Map<String,ArrayList<TimeSpan>>map;
    private Map<String,Integer>mapCountTracker;
    private boolean isEnabled = true;
    
    

    public static Profiler getInstance()
    {
        if(instance == null)
        {
            instance = new Profiler();
        }
        return instance;
    }

    private Profiler()
    {
        map = new HashMap<String,ArrayList<TimeSpan>>();
        mapCountTracker = new HashMap<String,Integer>();
    }

    public void start(String id, String bMessage) throws ProfilerException
    {
        if(isEnabled == true)
        {
            TimeSpan ts = new TimeSpan();
            ts.setBegin(System.nanoTime());
            ts.setBeginMessage(bMessage);
            if(map.containsKey(id))
            {
                ArrayList<TimeSpan>durations = map.get(id);
                TimeSpan lastTime = new TimeSpan();
                lastTime = durations.get(durations.size()-1);
                if(lastTime.getEndTime() == -1)
                {
                    throw new ProfilerException("Cannot hit start again before the timer ends");
                }
                durations.add(ts);
            }
            else
            {
                ArrayList<TimeSpan>durations = new ArrayList<TimeSpan>();
                durations.add(ts);
                map.put(id,durations);
            }
        }
         
    }

    public void stop(String id, String eMessage) throws ProfilerException
    {
        if(isEnabled == true)
        {
            if(map.containsKey(id))
            {
                ArrayList<TimeSpan>durations = map.get(id);
                TimeSpan lastTime = new TimeSpan();
                lastTime = durations.get(durations.size()-1);
                if(lastTime.getStartTime()==-1)
                {
                    throw new ProfilerException("Cannot hit stop again before the starting the timer");
                }
                TimeSpan ts = durations.get(durations.size()-1);
                ts.setEnd(System.nanoTime());   
                ts.setEndMessage(eMessage);
            }
            else
            {
                throw new ProfilerException("Cannot hit stop without a start to the respected timer "+id);
            }
        }
    }

    public void setEnable(boolean b)
    {
        isEnabled = b;
    }

    public void count(String id)
    {
        //System.out.println("mapCounTracker id: "+mapCountTracker.entrySet());
        if(isEnabled == true)
        {
            if(mapCountTracker.containsKey(id))
            {
                int tempCounter; 
                tempCounter = 1 + mapCountTracker.get(id);
                mapCountTracker.put(id,tempCounter);
            }
            else
            {
                mapCountTracker.put(id, 1);
            }
        }
    }

    public void start(String id) throws ProfilerException
    {
        if(isEnabled == true)
        {
            TimeSpan ts = new TimeSpan();
            ts.setBegin(System.nanoTime());
            if(map.containsKey(id))
            {
                ArrayList<TimeSpan>durations = map.get(id);
                TimeSpan lastTime = new TimeSpan();
                lastTime = durations.get(durations.size()-1);
                if(lastTime.getEndTime() == -1)
                {
                    throw new ProfilerException("Cannot hit start again before the timer ends");
                }
                durations.add(ts);
            }
            else
            {
                ArrayList<TimeSpan>durations = new ArrayList<TimeSpan>();
                durations.add(ts);
                map.put(id,durations);
            }
        }
    }

    public void stop(String id) throws ProfilerException
    {
        if(isEnabled == true)
        {
            if(map.containsKey(id))
            {
                ArrayList<TimeSpan>durations = map.get(id);
                TimeSpan lastTime = new TimeSpan();
                lastTime = durations.get(durations.size()-1);
                if(lastTime.getStartTime()==-1)
                {
                    throw new ProfilerException("Cannot hit stop again before the starting the timer");
                }
                TimeSpan ts = durations.get(durations.size()-1);
                ts.setEnd(System.nanoTime());   
            }
            else
            {
                throw new ProfilerException("Cannot hit stop without a start to the respected timer "+id);
            }
        }
    }

    //Make sure to do the search for max/min value 

    public void generateReport()
    {
        ProfilerGUI reportGUI = new ProfilerGUI(map,mapCountTracker);
        //reportGUI.
    }
}