package br.com.tiagohs.popmovies.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.tiagohs.popmovies.model.IDNameAbstract;
import br.com.tiagohs.popmovies.model.media.AlternativeTitle;

/**
 * Created by Tiago Henrique on 24/08/2016.
 */
public class AlternativeTitlesResponse extends IDNameAbstract {

    @JsonProperty("titles")
    private List<AlternativeTitle> mAlternativeTitles;

    public List<AlternativeTitle> getAlternativeTitles() {
        return mAlternativeTitles;
    }

    public void setAlternativeTitles(List<AlternativeTitle> alternativeTitles) {
        mAlternativeTitles = alternativeTitles;
    }
}
