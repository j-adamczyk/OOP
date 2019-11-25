import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class testIntegrationTests
{
    @Test
    public void testEndToEnd()
    {
        UtilSingleton.getInstance().readFile("C:\\Users\\jakub\\Desktop\\bibtex.txt");

        Parser p = new Parser();
        p.parse();

        DefaultOutputBuilder o = new DefaultOutputBuilder();
        String output = o.buildOutput(UtilSingleton.getInstance().parsedRecords,"");

        Assert.assertTrue(output.contains(
                "***********************************************************************\n" +
                "*MISC (random-note-crossref)                                          *\n" +
                "***********************************************************************\n" +
                "*note                *Volume~2 is listed under Knuth \\cite{book-full} *\n" +
                "***********************************************************************\n" +
                "*key                 *{Volume-2}                                      *\n" +
                "***********************************************************************"));

        Assert.assertTrue(output.contains(
                "**********************************************************************\n" +
                "*ARTICLE (article-crossref)                                          *\n" +
                "**********************************************************************\n" +
                "*author              *- L[eslie] A. Aamport                          *\n" +
                "**********************************************************************\n" +
                "*title               *The Gnats and Gnus Document Preparation System *\n" +
                "**********************************************************************\n" +
                "*journal             *\\mbox{G-Animal's} Journal                      *\n" +
                "**********************************************************************\n" +
                "*year                *1986                                           *\n" +
                "**********************************************************************"));

        Assert.assertTrue(output.contains(
                "***************************************************************************************\n" +
                "*INPROCEEDINGS (inproceedings-minimal)                                                *\n" +
                "***************************************************************************************\n" +
                "*author              *- Alfred V. Oaho                                                *\n" +
                "*                    *- Jeffrey D. Ullman                                             *\n" +
                "*                    *- Mihalis Yannakakis                                            *\n" +
                "***************************************************************************************\n" +
                "*title               *On Notions of Information Transfer in {VLSI} Circuits           *\n" +
                "***************************************************************************************\n" +
                "*booktitle           *Proc. Fifteenth Annual ACM Symposium on the Theory of Computing *\n" +
                "***************************************************************************************\n" +
                "*year                *1983                                                            *\n" +
                "***************************************************************************************"));

        Assert.assertTrue(output.contains(
                "****************************************************\n" +
                "*MISC (misc-minimal)                               *\n" +
                "****************************************************\n" +
                "*note                *This is a minimal MISC entry *\n" +
                "****************************************************\n" +
                "*key                 *Missilany                    *\n" +
                "****************************************************"));
    }

    @Test
    public void testSimpleSingleAuthor()
    {
        String[] args = {"C:\\Users\\jakub\\Desktop\\bibtex.txt","Tom Terrific",""};

        UtilSingleton.getInstance().readFile(args[0]);

        Parser p = new Parser();
        p.parse();

        AuthorsFilterVisitor v = new AuthorsFilterVisitor();

        List<Entry> filteredRecords = v.visitAndFilter(UtilSingleton.getInstance().parsedRecords,args[1]);

        Assert.assertEquals(1,filteredRecords.size());
    }

    @Test
    public void testComplexSingleAuthor()
    {
        String[] args = {"C:\\Users\\jakub\\Desktop\\bibtex.txt","Phony-Baloney",""};

        UtilSingleton.getInstance().readFile(args[0]);

        Parser p = new Parser();
        p.parse();

        AuthorsFilterVisitor v = new AuthorsFilterVisitor();

        List<Entry> filteredRecords = v.visitAndFilter(UtilSingleton.getInstance().parsedRecords,args[1]);

        Assert.assertEquals(2,filteredRecords.size());
    }

    @Test
    public void testManySimpleAuthors()
    {
        String[] args = {"C:\\Users\\jakub\\Desktop\\bibtex.txt","Ned {\\~N}et and Paul {\\={P}}ot",""};

        UtilSingleton.getInstance().readFile(args[0]);

        Parser p = new Parser();
        p.parse();

        AuthorsFilterVisitor v = new AuthorsFilterVisitor();

        List<Entry> filteredRecords = v.visitAndFilter(UtilSingleton.getInstance().parsedRecords,args[1]);

        Assert.assertEquals(2,filteredRecords.size());
    }

    @Test
    public void testManyComplexAuthors()
    {
        String[] args = {"C:\\Users\\jakub\\Desktop\\bibtex.txt","Oaho | Alfred V. and Ullman | Jeffrey D. and Mihalis Yannakakis",""};

        UtilSingleton.getInstance().readFile(args[0]);

        Parser p = new Parser();
        p.parse();

        AuthorsFilterVisitor v = new AuthorsFilterVisitor();

        List<Entry> filteredRecords = v.visitAndFilter(UtilSingleton.getInstance().parsedRecords,args[1]);

        Assert.assertEquals(3,filteredRecords.size());
    }

    @Test
    public void testNoFilters()
    {
        String[] args = {"C:\\Users\\jakub\\Desktop\\bibtex.txt","",""};

        UtilSingleton.getInstance().readFile(args[0]);

        Parser p = new Parser();
        p.parse();

        List<Entry> result = UtilSingleton.getInstance().parsedRecords;

        if (!args[1].equals(""))
        {
            IRecordFilteringVisitor authorsFilterVisitor = new AuthorsFilterVisitor();
            result = authorsFilterVisitor.visitAndFilter(result,args[1]);
        }

        if(!args[2].equals(""))
        {
            IRecordFilteringVisitor publicationFilterVisitor = new PublicationFilterVisitor();
            result = publicationFilterVisitor.visitAndFilter(result,args[2]);
        }

        Assert.assertEquals(35,result.size());
    }

    @Test
    public void testSimplePublicationFilter()
    {
        String[] args = {"C:\\Users\\jakub\\Desktop\\bibtex.txt","","book"};

        UtilSingleton.getInstance().readFile(args[0]);

        Parser p = new Parser();
        p.parse();

        PublicationFilterVisitor v = new PublicationFilterVisitor();

        List<Entry> filteredRecords = v.visitAndFilter(UtilSingleton.getInstance().parsedRecords,args[2]);

        Assert.assertEquals(5,filteredRecords.size());
    }

    @Test
    public void testComplexPublicationFilter()
    {
        String[] args = {"C:\\Users\\jakub\\Desktop\\bibtex.txt","","book, misc"};

        UtilSingleton.getInstance().readFile(args[0]);

        Parser p = new Parser();
        p.parse();

        PublicationFilterVisitor v = new PublicationFilterVisitor();

        List<Entry> filteredRecords = v.visitAndFilter(UtilSingleton.getInstance().parsedRecords,args[2]);

        Assert.assertEquals(8,filteredRecords.size());
    }

    @Test
    public void testBothAuthorAndPublicationFilters()
    {
        String[] args = {"C:\\Users\\jakub\\Desktop\\bibtex.txt","Tom Terrific","techreport"};

        UtilSingleton.getInstance().readFile(args[0]);

        Parser p = new Parser();
        p.parse();

        List<Entry> result = UtilSingleton.getInstance().parsedRecords;

        if (!args[1].equals(""))
        {
            IRecordFilteringVisitor authorsFilterVisitor = new AuthorsFilterVisitor();
            result = authorsFilterVisitor.visitAndFilter(result,args[1]);
        }

        if(!args[2].equals(""))
        {
            IRecordFilteringVisitor publicationFilterVisitor = new PublicationFilterVisitor();
            result = publicationFilterVisitor.visitAndFilter(result,args[2]);
        }

        Assert.assertEquals(1,result.size());
    }

    @Test
    public void testTwoWritingsOfSameAuthor()
    {
        String[] args = {"C:\\Users\\jakub\\Desktop\\bibtex.txt","Donald E. Knuth",""};
        UtilSingleton.getInstance().readFile(args[0]);

        Parser p = new Parser();
        p.parse();

        AuthorsFilterVisitor v = new AuthorsFilterVisitor();
        List<Entry> filteredRecords1 = v.visitAndFilter(UtilSingleton.getInstance().parsedRecords,args[1]);


        String[] args2 = {"C:\\Users\\jakub\\Desktop\\bibtex.txt","Knuth | Donald E.",""};
        UtilSingleton.getInstance().readFile(args2[0]);

        Parser p2 = new Parser();
        p.parse();

        AuthorsFilterVisitor v2 = new AuthorsFilterVisitor();
        List<Entry> filteredRecords2 = v2.visitAndFilter(UtilSingleton.getInstance().parsedRecords,args[1]);

        Assert.assertEquals(filteredRecords1.size(),filteredRecords2.size());

        for (int i=0; i<filteredRecords1.size(); i++)
        {
            Assert.assertEquals(filteredRecords1.get(i).key, filteredRecords2.get(i).key);
            Assert.assertEquals(filteredRecords1.get(i).recordType, filteredRecords2.get(i).recordType);
            Assert.assertEquals(filteredRecords1.get(i).crossref, filteredRecords2.get(i).crossref);
            for (int j=0; j<filteredRecords1.get(i).requiredAttributes.size(); j++)
                Assert.assertEquals(filteredRecords1.get(i).requiredAttributes.get(j), filteredRecords2.get(i).requiredAttributes.get(j));

            for (int j=0; j<filteredRecords1.get(i).optionalAttributes.size(); j++)
                Assert.assertEquals(filteredRecords1.get(i).optionalAttributes.get(j), filteredRecords2.get(i).optionalAttributes.get(j));
        }
    }

    @Test
    public void testLoadFileAndSubstitute()
    {
        UtilSingleton.getInstance().readFile("C:\\Users\\jakub\\Desktop\\bibtex.txt");
        DefaultStringSubstituter s = new DefaultStringSubstituter();
        s.substitute();

        System.out.println(UtilSingleton.getInstance().substitutedText);

        Assert.assertTrue(UtilSingleton.getInstance().substitutedText.contains("organization = The OX Association for Computing Machinery,"));
        Assert.assertTrue(UtilSingleton.getInstance().substitutedText.contains("\"Proc. Fifteenth Annual ACM\" # Symposium on the Theory of Computing,"));
        Assert.assertTrue(UtilSingleton.getInstance().substitutedText.contains("series = \"All ACM Conferences\","));
    }

    @Test
    public void testSubstitutingAndParsingEmptyFile()
    {
        UtilSingleton.getInstance().originalText = "";
        UtilSingleton.getInstance().substitutedText = "";
        Parser p = new Parser();
        p.parse();

        Assert.assertTrue(UtilSingleton.getInstance().parsedRecords.isEmpty());
    }

    @Test
    public void testParsingAndSubstituting()
    {
        UtilSingleton.getInstance().readFile("C:\\Users\\jakub\\Desktop\\bibtex.txt");
        Parser p = new Parser();
        p.parse();

        boolean tmp = true;
        for (Entry e : UtilSingleton.getInstance().parsedRecords)
        {
            if (e.key.equals(""))
                tmp = false;
            if (e.recordType.equals(""))
                tmp = false;
        }

        Assert.assertTrue(tmp);
    }
}
