package org.solr.integration.model;

import org.apache.solr.client.solrj.beans.Field;

import java.util.ArrayList;
import java.util.List;

public final class Book {

    @Field
    private String id;
    @Field("book_title")
    private String title;
    @Field
    private List<String> authors = new ArrayList<>();
    @Field
    private String content;
    @Field
    private String language;

    public Book() {
    }

    public Book(String id, String title, String language, List<String> authors, String content) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.authors.addAll(authors);
        this.content = content;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", content='" + content + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getContent() {
        return content;
    }

    public String getLanguage() {
        return language;
    }
}
