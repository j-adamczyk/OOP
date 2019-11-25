import org.junit.Assert;
import org.junit.Test;

public class testParsingContext
{
    @Test
    public void testDefaultStrategies()
    {
        ParsingContext p = new ParsingContext();
        Assert.assertEquals(p.getStringSubstituter().getClass(),DefaultStringSubstituter.class);
        Assert.assertEquals(p.getSingleRecordParser().getClass(),DefaultSingleRecordParser.class);
    }
}
