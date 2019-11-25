import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testDefaultStringSubstituter
{
    @Test
    public void testCutStringRecordsFromVariousRecords()
    {
        String records = "@STRING{...}\n @MISC{...}\n @string{...} \n\n @BoOk{...}\n";

        IStringSubstituterStrategy s = new DefaultStringSubstituter();
        Assert.assertEquals(s.getRecords(records).size(),2);
    }

    @Test
    public void testEmptyRecords()
    {
        String records = "";
        IStringSubstituterStrategy s = new DefaultStringSubstituter();
        Assert.assertEquals(s.getRecords(records).size(),0);
    }

    @Test
    public void testGetVariousSubstitutions()
    {
        List<String> stringRecords = new ArrayList<>();
        stringRecords.add("@STRING{a=\"aaa\"}");
        stringRecords.add("@string{ b = \" bbb \" }");
        stringRecords.add("@StRiNg{\n c= \"cc c \" }");
        stringRecords.add("@STRing{d = \"d d d\"\n}");

        IStringSubstituterStrategy s = new DefaultStringSubstituter();
        Map<String,String> m = s.getSubstitutions(stringRecords);

        Assert.assertEquals(m.get("a"),"aaa");
        Assert.assertEquals(m.get("b")," bbb ");
        Assert.assertEquals(m.get("c"),"cc c ");
        Assert.assertEquals(m.get("d"),"d d d");

        Assert.assertNull(m.get("e"));
        Assert.assertFalse(m.containsKey(null));
    }

    @Test
    public void testGetSubstitutionsFromEmptyList()
    {
        List<String> stringRecords = new ArrayList<>();

        IStringSubstituterStrategy s = new DefaultStringSubstituter();
        Map<String,String> m = s.getSubstitutions(stringRecords);

        Assert.assertTrue(m.isEmpty());
    }

    @Test
    public void testSubstitutions()
    {
        UtilSingleton.getInstance().originalText=
                "@STRING{ deleteMe = \"_\" }\n"+
                "@STRING{ subst = \"aaa\"} \n"+
                "@BOOK{KeydeleteMe_no13,\nauthor = \"and\"subst\n}\n";

        UtilSingleton.getInstance().substitutedText = UtilSingleton.getInstance().originalText;

        IStringSubstituterStrategy s = new DefaultStringSubstituter();
        s.substitute();

        boolean found = false;
        Pattern pattern = Pattern.compile("@BOOK\\{Key__no13,\nauthor = \"and\"aaa\n\\}\n");
        Matcher mat = pattern.matcher(UtilSingleton.getInstance().substitutedText);

        Assert.assertTrue(mat.find());
    }
}
