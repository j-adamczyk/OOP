import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.CommunicationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testCmdParamValAtTime
{
    @Before
    public void initialize() throws CommunicationException, IOException
    {
        UtilSingleton.getInstance().cacheService.filePath = "C:\\Users\\jakub\\Desktop\\Obiektowe\\cache.json";
        UtilSingleton.getInstance().cacheService.checkCache();
        UtilSingleton.getInstance().cacheService.loadCache();
    }

    @Test
    public void testEmptyArguments()
    {
        CommandParameterValueAtTime command = new CommandParameterValueAtTime();
        command.execute("","","");

        Assert.assertEquals(command.getResult(),"Provided station, parameter or date was not found!");
    }

    @Test
    public void testNullArguments()
    {
        CommandParameterValueAtTime command = new CommandParameterValueAtTime();
        command.execute(null,null,null);

        Assert.assertEquals(command.getResult(),"Provided station, parameter or date was not found!");
    }

    @Test
    public void testNonexistentParameter() throws NullPointerException
    {
        CommandParameterValueAtTime command = new CommandParameterValueAtTime();
        command.execute("","XYZ","");

        Assert.assertEquals(command.getResult(),"Provided station, parameter or date was not found!");
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
