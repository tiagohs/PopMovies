package br.com.tiagohs.popmovies.util.enumerations;

import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;

/**
 * Created by Tiago Henrique on 25/08/2016.
 */
public enum Method {

    ACCOUNT("account"),
    AUTH("authentication"),
    CERTIFICATION("certification"),
    COLLECTION("collection"),
    COMPANY("company"),
    CONFIGURATION("configuration"),
    CREDIT("credit"),
    DISCOVER("discover"),
    EPISODE("episode"),
    FIND("find"),
    GENRE("genre"),
    GUEST_SESSION("guest_session"),
    JOB("job"),
    KEYWORD("keyword"),
    LIST("list"),
    MOVIE("movie"),
    NETWORK("network"),
    PERSON("person"),
    REVIEW("review"),
    SEARCH("search"),
    SEASON("season"),
    TIMEZONES("timezones"),
    TV("tv");

    private final String value;

    private Method(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Method fromString(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (Method method : EnumSet.allOf(Method.class)) {
                if (value.equalsIgnoreCase(method.value)) {
                    return method;
                }
            }
        }

        throw new IllegalArgumentException("Método " + value + " não reconhecido.");
    }
}
