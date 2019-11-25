import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.CommunicationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testCmdMaxNormExceedNSensors
{
    @Before
    public void initialize() throws CommunicationException, IOException
    {
        UtilSingleton.getInstance().cacheService.filePath = "C:\\Users\\jakub\\Desktop\\Obiektowe\\cache.json";
        UtilSingleton.getInstance().cacheService.checkCache();
        UtilSingleton.getInstance().cacheService.loadCache();
    }

    @Test(expected = NullPointerException.class)
    public void testEmptyArguments()
    {
        CommandMaxNormExceedNSensors command = new CommandMaxNormExceedNSensors();
        command.execute("","",0);
    }

    @Test (expected = NullPointerException.class)
    public void testNullArguments()
    {
        CommandMaxNormExceedNSensors command = new CommandMaxNormExceedNSensors();
        command.execute(null,null,0);
    }

    @Test (expected = NullPointerException.class)
    public void testNonexistentStation() throws NullPointerException
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        CommandMaxNormExceedNSensors command = new CommandMaxNormExceedNSensors();
        command.execute("XYZ",dateFormat.format(date),3);
    }

    @Test
    public void testNormalWork() throws NullPointerException
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today3oclock = dateFormat.format(date)+" 03:00:00";


        CommandMaxNormExceedNSensors command = new CommandMaxNormExceedNSensors();
        command.execute("Kraków, ul. Bujaka",today3oclock,3);

        Assert.assertNotNull(command.getResult());
    }
}
