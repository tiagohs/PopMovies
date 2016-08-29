package br.com.tiagohs.popmovies.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Translation;

/**
 * Created by Tiago Henrique on 24/08/2016.
 */
public class TranslationsResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("translations")
    private List<Translation> translations;

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
