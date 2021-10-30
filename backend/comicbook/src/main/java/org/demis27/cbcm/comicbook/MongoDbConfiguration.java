package org.demis27.cbcm.comicbook;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.naming.Named;

@ConfigurationProperties("db")
public interface MongoDbConfiguration extends Named {

    String getCollection();
}
