package br.com.tiagohs.popmovies.util.enumerations;

import br.com.tiagohs.popmovies.R;

public enum Sort {
    GENEROS(R.string.genres_title),
    KEYWORDS(R.string.keywords),
    SIMILARS(R.string.similares),
    COMPANY(R.string.company),
    DISCOVER(R.string.discover),
    PERSON_MOVIES_CARRER(R.string.person),
    PERSON_CONHECIDO_POR(R.string.conhecido_por),
    PERSON_POPULAR(R.string.persons_popular),
    PERSON_DIRECTORS(R.string.person_directors),
    FAVORITE(R.string.favoritos),
    ASSISTIDOS(R.string.assistidos),
    QUERO_VER(R.string.want_see),
    NAO_QUERO_VER(R.string.dont_want_see),
    LIST_DEFAULT(R.string.list_default);

    private int nameID;

    Sort(int nameID) {
        this.nameID = nameID;
    }

    public int getNameID() {
        return nameID;
    }
}
