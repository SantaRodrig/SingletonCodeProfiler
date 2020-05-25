
public class TimeSpan 
{
    private long begin = -1;
    private long end = -1;
    private String beginMessage = "***";
    private String endMessage = "***";

    public void setBegin(long start)
    {
        begin = start;
        //System.out.println("Begin: "+begin);
    }

    public void setEnd(long finish)
    {
        end = finish;
        //System.out.println("End: "+end);
    }

    public long getDifferenceNS()
    {
        return end - begin;
    }

    public void setBeginMessage(String bMessage)
    {
        beginMessage = bMessage;
    }

    public String getBeginMessage()
    {
        return beginMessage;
    }

    public void setEndMessage(String eMessage)
    {
        endMessage = eMessage;
    }

    public String getEndMessage()
    {
        return endMessage;
    }

    public long getEndTime()
    {
        return end;
    }

    public long getStartTime()
    {
        return begin;
    }

}