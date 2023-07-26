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
//            List<Book> books = epubToModelTransformer.transformEpubBooksToBookModels(bookStore.getEpubBooks());
//            solrRepository.save(books);

            List<Book> allBooks = solrRepository.getAllBooks();
            allBooks.forEach(System.out::println);

            Book bookById = solrRepository.findBookById("824d1e31-b454-499b-93b9-0a505c725f47");
            System.out.println("Book by ID: " + bookById);

        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }
}
