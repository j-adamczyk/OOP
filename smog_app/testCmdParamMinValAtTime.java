import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.CommunicationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testCmdParamMinValAtTime
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
        CommandParameterMinValueAtTime command = new CommandParameterMinValueAtTime();
        command.execute("","");

        Assert.assertEquals(command.getResult(),"The chosen station was not found!");
    }

    @Test
    public void testNullArguments()
    {
        CommandParameterMinValueAtTime command = new CommandParameterMinValueAtTime();
        command.execute(null,null);

        Assert.assertEquals(command.getResult(),"The chosen station was not found!");
    }

    @Test
    public void testNonexistentParameter() throws NullPointerException
    {
        CommandParameterMinValueAtTime command = new CommandParameterMinValueAtTime();
        command.execute("XYZ","");

        Assert.assertEquals(command.getResult(),"The chosen station was not found!");
    }

    @Test
    public void testNonexistentDateAndTime() throws NullPointerException
    {
        CommandParameterMinValueAtTime command = new CommandParameterMinValueAtTime();
        command.execute("Kraków, ul. Bujaka","1900-01-01 10:00:00");

        Assert.assertEquals(command.getResult(),"Parameters' measurements for chosen date and time were not found!");
    }

    @Test
    public void testNormalWork() throws NullPointerException
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today3oclock = dateFormat.format(date)+" 03:00:00";

        CommandParameterMinValueAtTime command = new CommandParameterMinValueAtTime();
        command.execute("Kraków, ul. Bujaka",today3oclock);

        Assert.assertTrue(command.getResult().contains("For the station"));
    }
}
