package parser;

/**
 * Main part of Strategy design pattern. This class holds strategies used for @STRING substitution and single field parsing.
 * Default strategies are provided and they're used if no others are chosen.
 * parser.Parser class inherits from this one, so it can access strategies and check them. Every instance of parser.Parser has instance of
 * parser.ParsingContext connected to it, so strategies used can vary between parsers created and used.
 */
public class ParsingContext
{
    private IStringSubstituterStrategy stringSubstituter;
    private ISingleRecordParserStrategy singleRecordParser;

    /**
     * Sets default strategies.
     */
    public ParsingContext()
    {
        this.stringSubstituter = new DefaultStringSubstituter();
        this.singleRecordParser = new DefaultSingleRecordParser();
    }

    public IStringSubstituterStrategy getStringSubstituter()
    {
        return stringSubstituter;
    }

    public ISingleRecordParserStrategy getSingleRecordParser()
    {
        return singleRecordParser;
    }

    /**
     * Changes @STRING substitution strategy.
     * @param stringSubstituter strategy to be used
     */
    public void setStringSubstituter(IStringSubstituterStrategy stringSubstituter)
    {
        this.stringSubstituter = stringSubstituter;
    }

    /**
     * Changes single record parsing strategy.
     * @param singleRecordParser strategy to be used
     */
    public void setSingleRecordParser(ISingleRecordParserStrategy singleRecordParser)
    {
        this.singleRecordParser = singleRecordParser;
    }
}
