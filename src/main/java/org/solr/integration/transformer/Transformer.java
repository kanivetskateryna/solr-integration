package org.solr.integration.transformer;

import nl.siegmann.epublib.domain.Book;

public interface Transformer {

    org.solr.integration.model.Book transform(nl.siegmann.epublib.domain.Book epubBook);
}
