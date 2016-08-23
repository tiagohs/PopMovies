package br.com.tiagohs.popmovies.util;

import android.content.Context;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import br.com.tiagohs.popmovies.R;

public class MovieUtils {

    public static String formatAbrev(long value) {
        NavigableMap<Long, String> suffixes = new TreeMap<>();

        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");

        if (value == Long.MIN_VALUE) return formatAbrev(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatAbrev(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static String formatGeneres(Context context, List<Integer> ids) {

        if(ids==null || ids.size()<0)
            return "";

        String genre = "";
        String name = "";
        for (int i = 0; i < ids.size(); i++) {
            int id = ids.get(i);
            switch (id) {

                case 28:
                    name = context.getString(R.string.genere_action); break;
                case 12:
                    name = context.getString(R.string.genere_adventure); break;
                case 16:
                    name = context.getString(R.string.genere_animation); break;
                case 35:
                    name = context.getString(R.string.genere_comedy); break;
                case 80:
                    name = context.getString(R.string.genere_crime); break;
                case 99:
                    name = context.getString(R.string.genere_documentary); break;
                case 18:
                    name = context.getString(R.string.genere_drama); break;
                case 10751:
                    name = context.getString(R.string.genere_family); break;
                case 14:
                    name = context.getString(R.string.genere_fantasy); break;
                case 10769:
                    name = context.getString(R.string.genere_foreign); break;
                case 36:
                    name = context.getString(R.string.genere_history); break;
                case 27:
                    name = context.getString(R.string.genere_horror); break;
                case 10402:
                    name = context.getString(R.string.genere_music); break;
                case 9648:
                    name = context.getString(R.string.genere_mystery); break;
                case 10749:
                    name = context.getString(R.string.genere_romance); break;
                case 878:
                    name = context.getString(R.string.genere_science_fiction); break;
                case 10770:
                    name = context.getString(R.string.genere_tv_movie); break;
                case 53:
                    name = context.getString(R.string.genere_thriller); break;
                case 10752:
                    name = context.getString(R.string.genere_war); break;
                case 37:
                    name = context.getString(R.string.genere_western); break;
            }
            genre = genre + ", " + name;
        }
        return genre.substring(genre.length() > 0 ? 1 : 0);
    }

    public static String formatIdioma(Context context, String tagISO) {

        switch (tagISO) {
            case "pt":
                return context.getString(R.string.idioma_portugues);
            case "en":
                return context.getString(R.string.idioma_ingles);
            case "es":
                return context.getString(R.string.idioma_espanhol);
            default:
                return "";
        }

    }
}
