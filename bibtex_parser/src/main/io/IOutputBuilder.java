package io;

import model.Entry;

import java.util.List;

/**
 * Interface for classes building final output to be printed by the program.
 * There is only one method required to be implemented, buildOutput, which should return String ready to be printed.
 * ASCII sign should be provided to make frames of tables etc. If it's empty String, some default should be provided.
 */
public interface IOutputBuilder
{
    String buildOutput(List<Entry> entries, String sign);
}