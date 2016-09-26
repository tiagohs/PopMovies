package br.com.tiagohs.popmovies.util;

import android.content.Context;
import android.icu.text.NumberFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.CarrerMoviesDTO;

public class MovieUtils {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static Calendar c = Calendar.getInstance();

    public static void sortMoviesByDate(List<CarrerMoviesDTO> movieListDTO) {
        Collections.sort(movieListDTO, new Comparator<CarrerMoviesDTO>() {
            public int compare(CarrerMoviesDTO movie1, CarrerMoviesDTO movie2) {
                return movie2.getDate().compareTo(movie1.getDate());
            }
        });
    }

    public static boolean isEmptyValue(String value) {
        return value == null || value.equals("") || value.equals(" ");
    }

    public static String getDateDayWeek(int dayWeek) {
        c.set(Calendar.DAY_OF_WEEK, dayWeek);
        return formatter.format(c.getTime());
    }

    public static String getDateToday() {
        Calendar c = Calendar.getInstance();
        return formatter.format(c.getTime());
    }

    public static String getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return String.valueOf(c.get(Calendar.YEAR));
    }

    public static String getDateBefore(int numDays) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -numDays);
        return formatter.format(c.getTime());
    }

    public static String getDateAfter(int numDays) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, numDays);
        return formatter.format(c.getTime());
    }


    public static String formatCurrency(long orcamento) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
            return format.format(orcamento);
        } else {
            java.text.NumberFormat formatter = java.text.NumberFormat.getCurrencyInstance(Locale.getDefault());
            return formatter.format(orcamento);
        }
    }

    public static int getAge(int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if(a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }

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

    public static String formateDate(Context context, String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            return dateString;
        }

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);

        return dateFormat.format(date);
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

    public static String formatList(List<String> list) {

        if (list.size() > 0) {
            String listFormatada = list.get(0);

            for (int cont = 1; cont < list.size(); cont++)
                listFormatada += ", " + list.get(cont);
            return listFormatada;
        } else
            return "--";
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
