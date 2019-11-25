import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class testFacade
{
    @Test
    public void testMainWithWrongArguments() throws Exception
    {
        UtilSingleton.getInstance().originalText = null;
        String args[];

        args = new String[] {"","",""};
        Facade.main(args);
        Assert.assertNull(UtilSingleton.getInstance().originalText);

        args = new String[] {"",""};
        Facade.main(args);
        Assert.assertNull(UtilSingleton.getInstance().originalText);

        args = new String[] {};
        Facade.main(args);
        Assert.assertNull(UtilSingleton.getInstance().originalText);
    }
}
