import org.junit.Assert;
import org.junit.Test;
import parser.DefaultSingleRecordParser;
import parser.DefaultStringSubstituter;
import parser.ParsingContext;

public class testParsingContext
{
    @Test
    public void testDefaultStrategies()
    {
        ParsingContext p = new ParsingContext();
        Assert.assertEquals(p.getStringSubstituter().getClass(), DefaultStringSubstituter.class);
        Assert.assertEquals(p.getSingleRecordParser().getClass(), DefaultSingleRecordParser.class);
    }
}
