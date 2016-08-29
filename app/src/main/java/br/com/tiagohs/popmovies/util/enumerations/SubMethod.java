package br.com.tiagohs.popmovies.util.enumerations;

import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;

/**
 * Created by Tiago Henrique on 25/08/2016.
 */
public enum SubMethod {

    NONE(""),
    ACCOUNT_STATES("account_states"),
    ADD_ITEM("add_item"),
    AIRING_TODAY("airing_today"),
    ALT_TITLES("alternative_titles"),
    CASTS("casts"),
    CHANGES("changes"),
    CLEAR("clear"),
    COLLECTION("collection"),
    CONTENT_RATINGS("content_ratings"),
    COMBINED_CREDITS("combined_credits"),
    COMPANY("company"),
    CREDITS("credits"),
    EXTERNAL_IDS("external_ids"),
    FAVORITE("favorite"),
    FAVORITE_MOVIES("favorite/movies"),
    FAVORITE_TV("favorite/tv"),
    GUEST_SESSION("guest_session/new"),
    IMAGES("images"),
    ITEM_STATUS("item_status"),
    KEYWORD("keyword"),
    KEYWORDS("keywords"),
    LATEST("latest"),
    LIST("list"),
    LISTS("lists"),
    MOVIE("movie"),
    MOVIES("movies"),
    MOVIE_CREDITS("movie_credits"),
    MOVIE_LIST("movie/list"),
    MULTI("multi"),
    NOW_PLAYING("now_playing"),
    ON_THE_AIR("on_the_air"),
    PERSON("person"),
    POPULAR("popular"),
    RATED_MOVIES("rated/movies"),
    RATED_MOVIES_GUEST("rated_movies"),
    RATED_TV("rated/tv"),
    RATING("rating"),
    RELEASES("releases"),
    REMOVE_ITEM("remove_item"),
    REVIEWS("reviews"),
    SESSION_NEW("session/new"),
    SIMILAR("similar"),
    TAGGED_IMAGES("tagged_images"),
    TOKEN_NEW("token/new"),
    TOKEN_VALIDATE("token/validate_with_login"),
    TOP_RATED("top_rated"),
    TRANSLATIONS("translations"),
    TV("tv"),
    TV_CREDITS("tv_credits"),
    TV_LIST("tv/list"),
    UPCOMING("upcoming"),
    VIDEOS("videos"),
    WATCHLIST("watchlist"),
    WATCHLIST_MOVIES("watchlist/movies"),
    WATCHLIST_TV("watchlist/tv"),
    RELEASE_DATES("release_dates");

    private final String value;

    private SubMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static SubMethod fromString(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (SubMethod method : EnumSet.allOf(SubMethod.class)) {
                if (value.equalsIgnoreCase(method.value)) {
                    return method;
                }
            }
        }

        throw new IllegalArgumentException("Método '" + value + "' não reconhecido.");
    }
}
