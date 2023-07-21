package org.solr.integration.store;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class BookStore {

    private static final String BOOKS_FOLDER_PATH = "src/main/resources/books";

    public List<Book> getEpubBooks() {
        return Arrays.stream(Objects.requireNonNull(new File(BOOKS_FOLDER_PATH).listFiles()))
                .filter(file -> file.isFile() && file.getName().toLowerCase().endsWith(".epub"))
                .map(this::getEpubBook)
                .collect(Collectors.toList());
    }

    private Book getEpubBook(File epubFile) {
        Book epubBook = null;
        try(FileInputStream epubInputStream = new FileInputStream(epubFile)) {
            epubBook = (new EpubReader()).readEpub(epubInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return epubBook;
    }
}
