package org.solr.integration;

import org.apache.solr.client.solrj.SolrServerException;
import org.solr.integration.model.Book;
import org.solr.integration.repository.SolrRepository;
import org.solr.integration.store.BookStore;
import org.solr.integration.transformer.EpubToModelTransformer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SolrRepository solrRepository = new SolrRepository();
        BookStore bookStore = new BookStore();
        EpubToModelTransformer epubToModelTransformer = new EpubToModelTransformer();

        try {
            List<Book> books = epubToModelTransformer.transformEpubBooksToBookModels(bookStore.getEpubBooks());
            solrRepository.save(books);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }
}
