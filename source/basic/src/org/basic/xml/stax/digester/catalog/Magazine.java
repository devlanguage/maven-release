package org.basic.xml.stax.digester.catalog;

import java.util.HashSet;
import java.util.Set;

public class Magazine {

    private String name;

    private Set<Article> articles;
    private Catalog catalog;

    /**
     * @return the catalog
     */
    public Catalog getCatalog() {

        return catalog;
    }

    /**
     * @param catalog
     *            the catalog to set
     */
    public void setCatalog(Catalog catalog) {

        this.catalog = catalog;
    }

    /**
     * @return the articles
     */
    public synchronized Set<Article> getArticles() {

        if (this.articles == null) {
            this.articles = new HashSet<Article>();
        }
        return articles;
    }

    public void addArticle(Article article) {

        this.getArticles().add(article);
    }

    /**
     * @param articles
     *            the articles to set
     */
    public void setArticles(Set<Article> articles) {

        this.articles = articles;
    }

    /**
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {

        this.name = name;
    }
}
