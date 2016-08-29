package br.com.tiagohs.popmovies.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.model.ChangeKeyItem;

public class ChangesResponse {

    @JsonProperty("changes")
    private List<ChangeKeyItem> changedItems = new ArrayList<>();

    public List<ChangeKeyItem> getChangedItems() {
        return changedItems;
    }

    public void setChangedItems(List<ChangeKeyItem> changes) {
        this.changedItems = changes;
    }
}
