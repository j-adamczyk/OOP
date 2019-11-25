import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.CommunicationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testCmdParamWithLargestChange
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
        CommandParameterWithLargestChange command = new CommandParameterWithLargestChange();
        command.execute("","");

        Assert.assertEquals(command.getResult(),"Parameter was not measured at any of the provided stations since given date and time!");
    }

    @Test
    public void testNullArguments() throws ParseException
    {
        CommandParameterWithLargestChange command = new CommandParameterWithLargestChange();
        command.execute(null,null);

        Assert.assertEquals(command.getResult(),"Parameter was not measured at any of the provided stations since given date and time!");

    }

    @Test
    public void testNonexistentStation() throws ParseException
    {
        CommandParameterWithLargestChange command = new CommandParameterWithLargestChange();
        command.execute("XYZ","");

        Assert.assertEquals(command.getResult(),"Parameter was not measured at any of the provided stations since given date and time!");
    }

    @Test
    public void testEntireTime() throws ParseException
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today3oclock = dateFormat.format(date)+" 03:00:00";

        CommandParameterWithLargestChange command = new CommandParameterWithLargestChange();
        command.execute("Kraków, ul. Bujaka","1900-01-01 10:00:00");

        Assert.assertTrue(command.getResult().contains("Largest change of parameter"));
    }

    @Test
    public void testFutureTime() throws ParseException
    {
        CommandParameterWithLargestChange command = new CommandParameterWithLargestChange();
        command.execute("Kraków, ul. Bujaka","3000-01-01 12:00:00");

        Assert.assertEquals(command.getResult(),"Parameter was not measured at any of the provided stations since given date and time!");
    }

    @Test
    public void testNormalWork() throws ParseException
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today3oclock = dateFormat.format(date)+" 03:00:00";

        CommandParameterWithLargestChange command = new CommandParameterWithLargestChange();
        command.execute("Kraków, ul. Bujaka",today3oclock);

        Assert.assertTrue(command.getResult().contains("Largest change of parameter"));
    }
}
