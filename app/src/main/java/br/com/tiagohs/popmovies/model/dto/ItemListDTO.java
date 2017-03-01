package br.com.tiagohs.popmovies.model.dto;

import br.com.tiagohs.popmovies.util.enumerations.Sort;

public class ItemListDTO {
    private int itemID;
    private String nameItem;
    private Sort typeItem;
    private DiscoverDTO mDiscoverDTO;

    public ItemListDTO(int itemID) {
        this.itemID = itemID;
    }

    public ItemListDTO(int itemID, String nameItem) {
        this.itemID = itemID;
        this.nameItem = nameItem;
    }

    public ItemListDTO(int itemID, String nameItem, Sort typeItem, DiscoverDTO discoverDTO) {
        this.itemID = itemID;
        this.nameItem = nameItem;
        this.typeItem = typeItem;
        this.mDiscoverDTO = discoverDTO;
    }

    public ItemListDTO(String nameItem, Sort typeItem, DiscoverDTO discoverDTO) {
        this.nameItem = nameItem;
        this.typeItem = typeItem;
        this.mDiscoverDTO = discoverDTO;
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

    public DiscoverDTO getDiscoverDTO() {
        return mDiscoverDTO;
    }

    public void setDiscoverDTO(DiscoverDTO discoverDTO) {
        mDiscoverDTO = discoverDTO;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemListDTO) {
            ItemListDTO item = (ItemListDTO) obj;
            return getItemID() == item.getItemID();
        }

        return false;
    }
}
