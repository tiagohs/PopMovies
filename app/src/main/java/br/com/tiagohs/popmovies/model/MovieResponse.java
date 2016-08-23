package br.com.tiagohs.popmovies.model;

import java.util.List;

/**
 * Created by Tiago Henrique on 20/08/2016.
 */
public class MovieResponse {
    private Integer page;
    private List<Movie> results;
    private Integer total_pages;
    private Integer total_results;

    public MovieResponse(Integer page, Integer total_results, Integer total_pages, List<Movie> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
