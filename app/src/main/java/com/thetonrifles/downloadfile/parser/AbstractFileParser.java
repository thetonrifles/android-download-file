package com.thetonrifles.downloadfile.parser;

import java.util.List;

public abstract class AbstractFileParser<T extends AbstractItem> {

    abstract public List<T> parse(String content);

}
