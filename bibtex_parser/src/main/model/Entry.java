package model;

import java.util.*;

/**
 * Objects of this class represent entire single BibTeX record, e. g. section from @RECORDTYPE{, through tags and their values, to closing bracket }.
 */
public class Entry
{
    private String recordType;
    private String key;
    private String crossref;
    private Map<String,String> requiredAttributes;
    private Map<String,String> optionalAttributes;

    /**
     * Initializes recordType and key as empty Strings to avoid NullPointerException. Also initializes attributes' lists as empty lists.
     */
    public Entry()
    {
        this.setRecordType("");
        this.setKey("");
        this.setCrossref("");
        this.setRequiredAttributes(new LinkedHashMap<>());
        this.setOptionalAttributes(new LinkedHashMap<>());
    }

    /**
     * Next part of requiredAttributes list initialization, gets list of required fields and puts them in map with values as empty Strings
     * (to avoid NullPointerException).
     * @param requiredFields list of required fields for given record type
     * @return list of requiredAttributes with keys and all values as empty Strings
     */
    public Map<String,String> setRequired(List<String> requiredFields)
    {
        Map<String,String> required = new LinkedHashMap<>();
        for (String field : requiredFields)
            required.put(field,"");

        return required;
    }

    /**
     * Next part of optionalAttributes list initialization, gets list of optional fields and puts them in map with values as empty Strings
     * (to avoid NullPointerException).
     * @param optionalFields list of optional fields for given record type
     * @return list of optionalAttributes with keys and all values as empty Strings
     */
    public Map<String,String> setOptional(List<String> optionalFields)
    {
        Map<String,String> optional = new LinkedHashMap<>();
        for (String field : optionalFields)
            optional.put(field,"");

        return optional;
    }

    /**
     * Attributes:<br>
     * - recordType: type of record, each type defined in BibTeX documentation has list of required and optional attributes (types other than those from
     * documentation are ignored),<br>
     * - key: identifier of particular record, used in LaTeX file to reference bibliography position from BibTeX,<br>
     * - requiredAttributes: collection of attributes required for given record type and their values,<br>
     * - optionalAttributes: collection of attributes optional for given record type and their values.<br>
     * Attributes that are not required nor optional are ignored. Record type, key and values for required attributes are all required not to be empty Strings.
     */
    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCrossref() {
        return crossref;
    }

    public void setCrossref(String crossref) {
        this.crossref = crossref;
    }

    public Map<String, String> getRequiredAttributes() {
        return requiredAttributes;
    }

    public void setRequiredAttributes(Map<String, String> requiredAttributes) {
        this.requiredAttributes = requiredAttributes;
    }

    public Map<String, String> getOptionalAttributes() {
        return optionalAttributes;
    }

    public void setOptionalAttributes(Map<String, String> optionalAttributes) {
        this.optionalAttributes = optionalAttributes;
    }
}
