package org.solr.integration.transformer;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class EpubToModelTransformer implements Transformer {

    public List<org.solr.integration.model.Book> transformEpubBooksToBookModels(List<Book> epubBooks) {
        return epubBooks.stream()
                .map(this::transform)
                .collect(Collectors.toList());
    }

    @Override
    public org.solr.integration.model.Book transform(Book epubBook) {
        return new org.solr.integration.model.Book(
                UUID.randomUUID().toString(),
                epubBook.getTitle(),
                epubBook.getMetadata().getLanguage(),
                convertAuthorsToStringList(epubBook.getMetadata().getAuthors()),
                epubBook.getMetadata().getDescriptions().toString()
        );
    }

    private static List<String> convertAuthorsToStringList(List<Author> authors) {
        return authors.stream()
                .map(author -> author.getFirstname() + " " + author.getLastname())
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
