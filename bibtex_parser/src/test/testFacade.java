import io.UtilSingleton;
import org.junit.Assert;
import org.junit.Test;

public class testFacade
{
    @Test
    public void testMainWithWrongArguments() throws Exception
    {
        UtilSingleton.getInstance().setOriginalText(null);
        String args[];

        args = new String[] {"","",""};
        Facade.main(args);
        Assert.assertNull(UtilSingleton.getInstance().getOriginalText());

        args = new String[] {"",""};
        Facade.main(args);
        Assert.assertNull(UtilSingleton.getInstance().getOriginalText());

        args = new String[] {};
        Facade.main(args);
        Assert.assertNull(UtilSingleton.getInstance().getOriginalText());
    }
}
