package br.com.tiagohs.popmovies.util;

/**
 * Created by Tiago on 26/02/2017.
 */

public class UrlUtils {

    public static String formatAppendToResponse(final String[] appendToResponse) {

        if (appendToResponse != null) {
            StringBuilder sb = new StringBuilder();

            boolean first = Boolean.TRUE;

            for (String append : appendToResponse) {
                if (first)
                    first = Boolean.FALSE;
                else
                    sb.append(",");
                sb.append(append);
            }

            return sb.toString();
        } else {
            return null;
        }

    }
}
