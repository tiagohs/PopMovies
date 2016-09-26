package br.com.tiagohs.popmovies.model.dto;

import java.util.Map;

import br.com.tiagohs.popmovies.util.enumerations.Sort;

/**
 * Created by Tiago Henrique on 04/09/2016.
 */
public class ItemListDTO {
    private int itemID;
    private String nameItem;
    private Sort typeItem;
    private Map<String, String> mParamenters;

    public ItemListDTO(int itemID, String nameItem) {
        this.itemID = itemID;
        this.nameItem = nameItem;
    }

    public ItemListDTO(int itemID, String nameItem, Sort typeItem, Map<String, String> paramenters) {
        this.itemID = itemID;
        this.nameItem = nameItem;
        this.typeItem = typeItem;
        this.mParamenters = paramenters;
    }

    public ItemListDTO(String nameItem, Sort typeItem, Map<String, String> paramenters) {
        this.nameItem = nameItem;
        this.typeItem = typeItem;
        this.mParamenters = paramenters;
    }

    public ItemListDTO(String nameItem, Sort typeItem) {
        this.nameItem = nameItem;
        this.typeItem = typeItem;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public ItemListDTO() {
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public Sort getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(Sort typeItem) {
        this.typeItem = typeItem;
    }

    public Map<String, String> getParamenters() {
        return mParamenters;
    }

    public void setParamenters(Map<String, String> paramenters) {
        mParamenters = paramenters;
    }
}
