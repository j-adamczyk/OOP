import io.UtilSingleton;
import org.junit.Assert;
import org.junit.Test;
import parser.DefaultSingleRecordParser;
import parser.DefaultStringSubstituter;
import parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class testParser
{
    @Test
    public void testDefaultContextAndStrategies()
    {
        Parser p = new Parser();
        Assert.assertEquals(p.ParsingContext.getStringSubstituter().getClass(), DefaultStringSubstituter.class);
        Assert.assertEquals(p.ParsingContext.getSingleRecordParser().getClass(), DefaultSingleRecordParser.class);
    }

    @Test (expected = NullPointerException.class)
    public void testNullStringSubstitutionStrategy()
    {
        Parser p = new Parser();
        p.ParsingContext.setStringSubstituter(null);
        p.parse();
    }

    @Test (expected = NullPointerException.class)
    public void testNullSingleRecordParsingStrategy()
    {
        UtilSingleton.getInstance().setOriginalText("Dummy text");
        UtilSingleton.getInstance().setSubstitutedText("Dummy text");
        Parser p = new Parser();
        p.ParsingContext.setSingleRecordParser(null);
        p.ParsingContext.getSingleRecordParser().parseSingleRecord("Dummy record");
    }

    @Test
    public void testEmptyTextForGetRecords()
    {
        UtilSingleton.getInstance().setSubstitutedText("");
        Parser p = new Parser();
        Assert.assertTrue(p.getRecords().size()==0);
    }

    @Test (expected = NullPointerException.class)
    public void testNullTextForGetRecords()
    {
        UtilSingleton.getInstance().setSubstitutedText(null);
        Parser p = new Parser();
        p.getRecords();
    }

    @Test
    public void testCountWeirdBibTeXRecordsForGetRecords()
    {
        UtilSingleton.getInstance().setSubstitutedText("@STRING{...}@STRING{...}\n @MISC{...} \n\n @BOOK{...}\n");
        Parser p = new Parser();
        Assert.assertEquals(p.getRecords().size(),3);
    }

    @Test
    public void testFileWithNoRecordsForGetRecords()
    {
        UtilSingleton.getInstance().setSubstitutedText("Dummy text, comment for BibTeX");
        Parser p = new Parser();
        Assert.assertEquals(p.getRecords().size(),0);
    }

    @Test
    public void testEmptyRecordsListForGetEntries()
    {
        List<String> emptyRecordsList = new ArrayList<>();
        Parser p = new Parser();
        Assert.assertEquals(p.getEntries(emptyRecordsList).size(),0);
    }


}
