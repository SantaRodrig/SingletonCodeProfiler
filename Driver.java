//import java.sql.Time;
import java.lang.Thread;
public class Driver
{
    public static void main(String arg[])
    {
        System.out.println("Code Profiler");
        /*TimeSpan t = new TimeSpan();
        t.setBegin(System.nanoTime());
        t.setEnd(System.nanoTime()+100);

        long duration = t.getDifferenceNS();

        System.out.println("Duration: "+duration);*/
        try
        {
            for(int i = 0; i < 100; i++)
            {
                Profiler.getInstance().start("timer1","start of timer, i: "+i);
                Profiler.getInstance().start("timer2","start of timer, i: "+i);
               
                //pause the program for 100 ms
                Thread.sleep(100);
               
                Profiler.getInstance().stop("timer1","end of timer, i: "+i);
                Profiler.getInstance().stop("timer2","end of timer, i: "+i);
            
                if(i % 10 == 0)
                {
                    //count how many times the counter is an even multiple of 10
                    Profiler.getInstance().count("Every 10 count");
                }
            }

            //turn off the profiler
            Profiler.getInstance().setEnable(false);

            Profiler.getInstance().start("not really a timer");

            Profiler.getInstance().count("every 10 count");
            Profiler.getInstance().count("every 10 count");
            Profiler.getInstance().count("every 10 count");

            Thread.sleep(100);

            Profiler.getInstance().stop("not really a timer");

            //turn the profiler back on 
            Profiler.getInstance().setEnable(true);

            Profiler.getInstance().start("random timer");

            //Math.random() returns a random double between 0.0-1.0
            Thread.sleep((long)(Math.random()*5000));

            Profiler.getInstance().stop("random timer");

            //analyze the results of the profiler
            Profiler.getInstance().generateReport();


        }
        catch(ProfilerException ex1)
        {
            ex1.printStackTrace();
        }
        catch(InterruptedException ex2)
        {
            ex2.printStackTrace();
        }
    }
}