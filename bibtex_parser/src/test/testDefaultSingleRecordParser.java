import model.Entry;
import org.junit.Assert;
import org.junit.Test;
import parser.DefaultSingleRecordParser;

public class testDefaultSingleRecordParser
{
    @Test
    public void testFillingEmptyRecordWithEmptyData()
    {
        Entry entry = new Entry();
        String emptyRecord = "";
        DefaultSingleRecordParser parser = new DefaultSingleRecordParser();

        entry = parser.fillFields(entry,emptyRecord);

        Assert.assertEquals("", entry.getRecordType());
        Assert.assertEquals("", entry.getKey());
        Assert.assertEquals("", entry.getCrossref());
        Assert.assertTrue(entry.getRequiredAttributes().isEmpty());
        Assert.assertTrue(entry.getOptionalAttributes().isEmpty());
    }

    @Test (expected = NullPointerException.class)
    public void testNullCrossref()
    {
        DefaultSingleRecordParser parser = new DefaultSingleRecordParser();
        parser.getCrossref(null);
    }

    @Test
    public void testEmptyCrossref()
    {
        DefaultSingleRecordParser parser = new DefaultSingleRecordParser();
        Assert.assertEquals("",parser.getCrossref(""));
    }

    @Test (expected = NullPointerException.class)
    public void testNullKey()
    {
        DefaultSingleRecordParser parser = new DefaultSingleRecordParser();
        parser.getKey(null);
    }

    @Test
    public void testEmptyKey()
    {
        DefaultSingleRecordParser parser = new DefaultSingleRecordParser();
        Assert.assertEquals("",parser.getKey(""));
    }

    @Test
    public void testCreateNonexistentEntryType()
    {
        String nonexEntryType = "EBOOK";
        DefaultSingleRecordParser parser = new DefaultSingleRecordParser();
        Assert.assertNull(parser.getEmptyEntry(nonexEntryType));
    }

    @Test
    public void testCreateExistentEntryType()
    {
        String exEntryType = "Book";
        DefaultSingleRecordParser parser = new DefaultSingleRecordParser();
        Assert.assertNotNull(parser.getEmptyEntry(exEntryType));
    }

    @Test (expected = NoClassDefFoundError.class)
    public void testCreateExistentEntryTypeWithWrongCase()
    {
        String exEntryType = "BOOK";
        DefaultSingleRecordParser parser = new DefaultSingleRecordParser();
        parser.getEmptyEntry(exEntryType);
    }

    @Test (expected = NullPointerException.class)
    public void testParserNullRecord()
    {
        DefaultSingleRecordParser parser = new DefaultSingleRecordParser();
        parser.parseSingleRecord(null);
    }
}