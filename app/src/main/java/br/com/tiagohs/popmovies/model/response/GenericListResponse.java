package br.com.tiagohs.popmovies.model.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Tiago Henrique on 24/08/2016.
 */
public class GenericListResponse<T> {

    @JsonProperty("page")
    private int page;

    @JsonProperty("total_pages")
    private int totalPage;

    @JsonProperty("total_results")
    private int totalResults;

    @JsonProperty("results")
    private List<T> results;

    public List<T> getResults() {
        return results;
    }

    @JsonCreator
    public void setResults(List<T> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
