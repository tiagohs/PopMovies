package br.com.tiagohs.popmovies.util.enumerations;

import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;

/**
 * Created by Tiago Henrique on 24/08/2016.
 */
public enum Param {
    ADULT("include_adult"),
    API_KEY("api_key"),
    APPEND("append_to_response"),
    CERTIFICATION("certification"),
    CERTIFICATION_COUNTRY("certification_country"),
    CERTIFICATION_LTE("certification.lte"),
    CONFIRM("confirm"),
    COUNTRY("country"),
    END_DATE("end_date"),
    EPISODE_NUMBER("episode_number"),
    EXTERNAL_SOURCE("external_source"),
    FAVORITE("favorite"),
    FIRST_AIR_DATE_GTE("first_air_date.gte"),
    FIRST_AIR_DATE_LTE("first_air_date.lte"),
    FIRST_AIR_DATE_YEAR("first_air_date_year"),
    GUEST_SESSION_ID("guest_session_id"),
    ID("id"),
    INCLUDE_ADULT("include_adult"),
    INCLUDE_ALL_MOVIES("include_all_movies"),
    INCLUDE_IMAGE_LANGUAGE("include_image_language"),
    INCLUDE_VIDEO("include_video"),
    LANGUAGE("language"),
    MOVIE_ID("movie_id"),
    MOVIE_WATCHLIST("movie_watchlist"),
    PAGE("page"),
    PASSWORD("password"),
    PRIMARY_RELEASE_YEAR("primary_release_year"),
    QUERY("query"),
    RELEASE_DATE_GTE("release_date.gte"),
    RELEASE_DATE_LTE("release_date.lte"),
    PRIMARY_RELEASE_DATE_GTE("primary_release_date.gte"),
    PRIMARY_RELEASE_DATE_LTE("primary_release_date.lte"),
    SEARCH_TYPE("search_type"),
    SEASON_NUMBER("season_number"),
    SESSION_ID("session_id"),
    SORT_BY("sort_by"),
    SORT_ORDER("sort_order"),
    START_DATE("start_date"),
    TIMEZONE("timezone"),
    TOKEN("request_token"),
    USERNAME("username"),
    VALUE("value"),
    VOTE_AVERAGE_GTE("vote_average.gte"),
    VOTE_AVERAGE_LTE("vote_average.lte"),
    VOTE_COUNT_GTE("vote_count.gte"),
    VOTE_COUNT_LTE("vote_count.lte"),
    WITH_CAST("with_cast"),
    WITH_COMPANIES("with_companies"),
    WITH_CREW("with_crew"),
    WITH_GENRES("with_genres"),
    WITH_KEYWORDS("with_keywords"),
    WITH_NETWORKS("with_networks"),
    WITH_PEOPLE("with_people"),
    YEAR("year"),
    OMDB_QUERY("i"),
    OMDB_TOMATOES("tomatoes"),
    WITH_RELEASE_TYPE("with_release_type"),
    RELEASE("region");

    private String param;

    Param(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public static Param fromString(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (Param param : EnumSet.allOf(Param.class)) {
                if (value.equalsIgnoreCase(param.param)) {
                    return param;
                }
            }
        }

        throw new IllegalArgumentException("Value '" + value + "' not recognised");
    }

    @Override
    public String toString() {
        return getParam();
    }
}
