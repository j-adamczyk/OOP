import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testEndToEnd
{
    @Test
    public void testEndToEnd() throws Exception
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today3oclock = dateFormat.format(date)+" 03:00:00";
        String today7oclock = dateFormat.format(date)+" 07:00:00";

        String[] args = {"--AverageParameterValue", "Kraków, ul. Bujaka", "NO2", today3oclock, today7oclock};
        Facade.main(args);
    }
}
