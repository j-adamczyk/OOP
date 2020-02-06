import command.CommandAverageParameterValue;
import model.UtilSingleton;
import org.junit.Before;
import org.junit.Test;

import javax.naming.CommunicationException;
import java.io.IOException;
import java.text.*;
import java.util.Date;

public class testCmdAvgParamValue
{
    @Before
    public void initialize() throws CommunicationException, IOException
    {
        UtilSingleton.getInstance().getCacheService().setFilePath("C:\\Users\\jakub\\Desktop\\Obiektowe\\cache.json");
        UtilSingleton.getInstance().getCacheService().checkCache();
        UtilSingleton.getInstance().getCacheService().loadCache();
    }

    @Test (expected = ParseException.class)
    public void testEmptyArguments() throws ParseException, NullPointerException
    {
        CommandAverageParameterValue command = new CommandAverageParameterValue();
        command.execute("","","","");
    }

    @Test (expected = NullPointerException.class)
    public void testNullArguments() throws ParseException, NullPointerException
    {
        CommandAverageParameterValue command = new CommandAverageParameterValue();
        command.execute(null,null,null,null);
    }

    @Test (expected = NullPointerException.class)
    public void testNonexistentParameter() throws ParseException, NullPointerException
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today3oclock = dateFormat.format(date)+" 03:00:00";
        String today7oclock = dateFormat.format(date)+" 07:00:00";


        CommandAverageParameterValue command = new CommandAverageParameterValue();
        command.execute("Kraków, ul. Bujaka","NonexistentParameter",today3oclock,today7oclock);
    }

    @Test
    public void testNormalWork() throws ParseException, NullPointerException
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today3oclock = dateFormat.format(date)+" 03:00:00";
        String today7oclock = dateFormat.format(date)+" 07:00:00";

        CommandAverageParameterValue command = new CommandAverageParameterValue();
        command.execute("Kraków, ul. Bujaka","NO2",today3oclock,today7oclock);
    }
}
