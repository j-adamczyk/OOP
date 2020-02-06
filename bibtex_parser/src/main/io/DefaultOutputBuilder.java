package io;

import model.Entry;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple, default class preparing final program output - if no other is chosen, this one is used.
 */
public class DefaultOutputBuilder implements IOutputBuilder
{
    /**
     * Main method building final results of the program, preparing it to be printed.
     * This default version makes table with frame from ASCII sign (or default * if it was not specified).
     * First row is made of record type and key. Further there are 2 columns: first with attribute name, second with attribute value.
     * This method is meant to be used on previously checked entry list (e. g. all required fields are filled).
     * Method takes each entry. If it's not author or editor (fields that have to treated specially), then it just adds
     * key and value to result (except for optional attributes with empty values - then it just omits them).
     * For author/editor field each author/editor is in separate line in list with his/her name first and surname later.
     * @param entries list of entries to be printed by the program
     * @param sign ASCII sign to make frames of tables etc. in the output
     * @return String representing program results, ready to be printed
     */
    public String buildOutput(List<Entry> entries, String sign)
    {
        if (sign.equals(""))
            sign = "*";
        String output = "\n";

        for (Entry entry : entries)
        {
            String tmp = "";
            int entire_length = 0;
            for (Map.Entry<String,String> iter : entry.getRequiredAttributes().entrySet())
                if (iter.getValue().length() > entire_length)
                    entire_length = iter.getValue().length()+1;

            for (Map.Entry<String,String> iter : entry.getOptionalAttributes().entrySet())
                if (iter.getValue().length() > entire_length)
                    entire_length = iter.getValue().length()+1;

            entire_length += 23;


            for (int i=1; i<=entire_length; i++)
                output += sign;
            output += "\n";


            tmp = sign + entry.getRecordType() + " (" + entry.getKey() + ")";
            int tmp_length = tmp.length();
            for (int i = 1; i<(entire_length-tmp_length); i++)
                tmp += " ";

            tmp = tmp + sign + "\n";

            for (int i=1; i<=entire_length; i++)
                tmp += sign;

            tmp += "\n";
            output += tmp;

            tmp = "";
            for (Map.Entry<String,String> iter : entry.getRequiredAttributes().entrySet())
            {
                if (iter.getKey().equals("author") && iter.getValue().equals("") && entry.getRequiredAttributes().containsKey("editor")
                        && !entry.getRequiredAttributes().get("editor").equals(""))
                    continue;

                if (iter.getKey().equals("editor") && iter.getValue().equals("") && entry.getRequiredAttributes().containsKey("author")
                        && !entry.getRequiredAttributes().get("author").equals(""))
                    continue;

                tmp = sign + iter.getKey();
                for (int i=1; i<=20-iter.getKey().length(); i++)
                    tmp += " ";

                tmp += sign;

                if (iter.getKey().equals("author") || iter.getKey().equals("editor"))
                {
                    if (iter.getValue().equals(""))
                    {
                        for (int i=1; i<=entire_length-23; i++)
                            tmp += " ";

                        tmp = tmp + sign + "\n";

                        for (int i=1; i<=entire_length; i++)
                            tmp += sign;

                        tmp += "\n";
                        output += tmp;
                        break;
                    }

                    String[] authors = iter.getValue().split("and");

                    for (int i=0; i<authors.length; i++)
                    {
                        String authorNameAndLastname = "";
                        if (i==0)
                            tmp = tmp + "- ";
                        else
                        {
                            tmp += sign;
                            for (int j=1; j<=20; j++)
                                tmp += " ";

                            tmp = tmp + sign + "- ";
                        }

                        authorNameAndLastname = getAuthorName(authors[i]);

                        tmp += authorNameAndLastname;

                        for (int j=1; j<=entire_length-25-authorNameAndLastname.length(); j++)
                            tmp += " ";

                        tmp = tmp + sign + "\n";
                    }
                }
                else
                {
                    tmp += iter.getValue();

                    for (int i=1; i<=entire_length-23-iter.getValue().length(); i++)
                        tmp += " ";

                    tmp = tmp + sign + "\n";
                }

                for (int i=1; i<=entire_length; i++)
                    tmp += sign;

                tmp += "\n";
                output += tmp;
            }

            tmp = "";
            for (Map.Entry<String,String> iter : entry.getOptionalAttributes().entrySet())
            {
                if (!iter.getValue().equals(""))
                {
                    tmp = sign + iter.getKey();
                    for (int i=1; i<=20-iter.getKey().length(); i++)
                        tmp += " ";

                    tmp += sign;

                    if (iter.getKey().equals("author") || iter.getKey().equals("editor"))
                    {
                        if (iter.getValue().equals(""))
                        {
                            for (int i=1; i<=entire_length-23; i++)
                                tmp += " ";

                            tmp = tmp + sign + "\n";

                            for (int i=1; i<=entire_length; i++)
                                tmp += sign;

                            tmp += "\n";
                            output += tmp;
                            break;
                        }

                        String[] authors = iter.getValue().split("and");

                        for (int i=0; i<authors.length; i++)
                        {
                            String authorNameAndLastname = "";
                            if (i==0)
                            {
                                tmp = tmp + "- ";

                                authorNameAndLastname = getAuthorName(authors[i]);

                                tmp += authorNameAndLastname;

                                for (int j=1; j<=entire_length-25-authorNameAndLastname.length(); j++)
                                    tmp += " ";

                                tmp = tmp + sign + "\n";
                            }
                            else
                            {
                                tmp += sign;
                                for (int j=1; j<=20; j++)
                                    tmp += " ";

                                tmp = tmp + sign + "- ";

                                authorNameAndLastname = getAuthorName(authors[i]);

                                tmp += authorNameAndLastname;

                                for (int j=1; j<=entire_length-25-authorNameAndLastname.length(); j++)
                                    tmp += " ";

                                tmp = tmp + sign + "\n";
                            }
                        }

                        for (int i=1; i<=entire_length; i++)
                            tmp += sign;

                        tmp += "\n";

                        output += tmp;
                    }
                    else
                    {
                        tmp += iter.getValue();

                        for (int i=1; i<=entire_length-23-iter.getValue().length(); i++)
                            tmp += " ";

                        tmp = tmp + sign + "\n";

                        for (int i=1; i<=entire_length; i++)
                            tmp += sign;

                        tmp += "\n";
                        output += tmp;
                    }
                }
            }

            output = output+"\n\n";
        }

        return output;
    }

    /**
     * Formats author name and surname, so it will be displayed in right order.
     * @param author name and surname of single author (with or without pipe |)
     * @return String with author's name and surname in right order, ready for display
     */
    public String getAuthorName (String author)
    {
        String result = "";

        if (Pattern.compile("\\|").matcher(author).find())
        {
            Pattern pattern = Pattern.compile("\\s*(.*)\\s*\\|\\s*(.*)\\s*");
            Matcher matcher = pattern.matcher(author);
            while (matcher.find())
            {
                result = matcher.group(2) + " " + matcher.group(1);
            }

            result = result.replaceAll("\\s{2,}"," ");
        }
        else
        {
            Pattern pattern = Pattern.compile("\\s*\\b(.*)\\b\\s*");
            Matcher matcher = pattern.matcher(author);
            if (matcher.find())
            {
                result = matcher.group(1).replaceAll("\\s{2,}", " ");
            }
            else
                result = matcher.group(0).replaceAll("\\s{2,}"," ");
        }

        return result;
    }
}