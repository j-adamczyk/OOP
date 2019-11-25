import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.CommunicationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testCmdParamValPlot
{
    @Before
    public void initialize() throws CommunicationException, IOException
    {
        UtilSingleton.getInstance().cacheService.filePath = "C:\\Users\\jakub\\Desktop\\Obiektowe\\cache.json";
        UtilSingleton.getInstance().cacheService.checkCache();
        UtilSingleton.getInstance().cacheService.loadCache();
    }

    @Test
    public void testEmptyArguments() throws ParseException
    {
        CommandParameterValuesPlot command = new CommandParameterValuesPlot();
        command.execute("","","","");

        Assert.assertEquals(command.getResult(),"Measurements for provided arguments are empty! Check station, parameter and hours again!");
    }

    @Test
    public void testNullArguments() throws ParseException
    {
        CommandParameterValuesPlot command = new CommandParameterValuesPlot();
        command.execute(null,null,null,null);

        Assert.assertEquals(command.getResult(),"Measurements for provided arguments are empty! Check station, parameter and hours again!");

    }

    @Test
    public void testNonexistentParameter() throws ParseException
    {
        CommandParameterValuesPlot command = new CommandParameterValuesPlot();
        command.execute("","XYZ","","");

        Assert.assertEquals(command.getResult(),"Measurements for provided arguments are empty! Check station, parameter and hours again!");
    }

    @Test
    public void testStartTimeAfterEndTime() throws ParseException
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today3oclock = dateFormat.format(date)+" 03:00:00";
        String today7oclock = dateFormat.format(date)+" 07:00:00";

        CommandParameterValuesPlot command = new CommandParameterValuesPlot();
        command.execute("Kraków, ul. Bujaka","NO2",today7oclock,today3oclock);

        Assert.assertEquals(command.getResult(),"Start date was after end date!");
    }

    @Test
    public void testNormalWork() throws NullPointerException
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today3oclock = dateFormat.format(date)+" 03:00:00";

        CommandParameterValueAtTime command = new CommandParameterValueAtTime();
        command.execute("Kraków, ul. Bujaka","O3",today3oclock);

        Assert.assertTrue(command.getResult().contains("Station:"));
    }
}
