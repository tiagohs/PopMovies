package br.com.tiagohs.popmovies.model.dto;

/**
 * Created by Tiago Henrique on 04/09/2016.
 */
public class ItemListDTO {
    private int itemID;
    private String nameItem;

    public ItemListDTO(int itemID, String nameItem) {
        this.itemID = itemID;
        this.nameItem = nameItem;
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
}
