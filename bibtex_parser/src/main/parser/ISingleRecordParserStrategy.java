package parser;

import model.Entry;

/**
 * Interface for strategies of parsing single record.
 * Any class implementing this interface can be chosen as single record parser.
 * Every strategy should be different from others. Default, simple version is provided.
 */
public interface ISingleRecordParserStrategy
{
    Entry parseSingleRecord(String record);
}