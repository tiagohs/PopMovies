package br.com.tiagohs.popmovies.util.enumerations;

import br.com.tiagohs.popmovies.R;

public enum Sort {
    GENEROS(R.string.generos),
    KEYWORDS(R.string.keywords),
    SIMILARS(R.string.similares),
    COMPANY(R.string.company),
    DISCOVER(R.string.discover),
    PERSON_MOVIES_CARRER(R.string.person),
    PERSON_CONHECIDO_POR(R.string.conhecido_por_title),
    PERSON_POPULAR(R.string.persons_popular),
    PERSON_DIRECTORS(R.string.person_directors),
    FAVORITE(R.string.menu_favoritos),
    ASSISTIDOS(R.string.assistidos);

    private int nameID;

    Sort(int nameID) {
        this.nameID = nameID;
    }

    public int getNameID() {
        return nameID;
    }
}
