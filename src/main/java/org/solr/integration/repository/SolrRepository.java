package org.solr.integration.repository;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.solr.integration.model.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SolrRepository {

    private static final String REPO_URL = "http://localhost:8983/solr/my_core";

    public void save(List<Book> books) throws SolrServerException {
        try(Http2SolrClient solrClient = new Http2SolrClient.Builder(REPO_URL)
                .withConnectionTimeout(10000, TimeUnit.MILLISECONDS)
                .build()) {
            solrClient.addBeans(books);
            solrClient.commit();
            System.out.println("Data indexed to Solr successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Book findBookById(String bookId) throws SolrServerException {
        try (Http2SolrClient solrClient = new Http2SolrClient.Builder(REPO_URL)
                .withConnectionTimeout(10000, TimeUnit.MILLISECONDS)
                .build()) {
            SolrQuery query = new SolrQuery();
            query.setQuery("id:" + bookId);
            QueryResponse response = solrClient.query(query);
            SolrDocumentList results = response.getResults();

            if (results.getNumFound() > 0) {
                SolrDocument doc = results.get(0);
                DocumentObjectBinder binder = new DocumentObjectBinder();
                return binder.getBean(Book.class, doc);
            } else {
                System.out.println("Book with ID " + bookId + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Book> getAllBooks() throws SolrServerException {
        List<Book> books = new ArrayList<>();

        try (Http2SolrClient solrClient = new Http2SolrClient.Builder(REPO_URL)
                .withConnectionTimeout(10000, TimeUnit.MILLISECONDS)
                .build()) {
            SolrQuery query = new SolrQuery();
            query.setQuery("*:*");
            QueryResponse response = solrClient.query(query);
            SolrDocumentList results = response.getResults();

            if (results.getNumFound() > 0) {
                DocumentObjectBinder binder = new DocumentObjectBinder();
                for (SolrDocument doc : results) {
                    Book book = binder.getBean(Book.class, doc);
                    books.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }
}
