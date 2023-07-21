package org.solr.integration.repository;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.solr.integration.model.Book;

import java.io.IOException;
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
}
