package br.com.tiagohs.popmovies.model;

public class Item {
    public String mItemTextMarcado;
    public String mItemTextDesmarcado;
    public int mItemIconMarcado;
    public int mItemIconDesmarcado;

    public Item(String mItemTextMarcado, String mItemTextDesmarcado, int mItemIconMarcado, int mItemIconDesmarcado) {
        this.mItemTextMarcado = mItemTextMarcado;
        this.mItemTextDesmarcado = mItemTextDesmarcado;
        this.mItemIconMarcado = mItemIconMarcado;
        this.mItemIconDesmarcado = mItemIconDesmarcado;
    }

    public String getItemTextMarcado() {
        return mItemTextMarcado;
    }

    public void setItemTextMarcado(String mItemTextMarcado) {
        this.mItemTextMarcado = mItemTextMarcado;
    }

    public String getItemTextDesmarcado() {
        return mItemTextDesmarcado;
    }

    public void setItemTextDesmarcado(String mItemTextDesmarcado) {
        this.mItemTextDesmarcado = mItemTextDesmarcado;
    }

    public int getItemIconMarcado() {
        return mItemIconMarcado;
    }

    public void setItemIconMarcado(int mItemIconMarcado) {
        this.mItemIconMarcado = mItemIconMarcado;
    }

    public int getItemIconDesmarcado() {
        return mItemIconDesmarcado;
    }

    public void setItemIconDesmarcado(int mItemIconDesmarcado) {
        this.mItemIconDesmarcado = mItemIconDesmarcado;
    }

    @Override
    public String toString() {
        return mItemTextMarcado;
    }
}
