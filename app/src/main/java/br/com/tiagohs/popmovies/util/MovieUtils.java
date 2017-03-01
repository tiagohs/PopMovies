package br.com.tiagohs.popmovies.util;

import android.content.Context;
import android.icu.text.NumberFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.db.GenreDB;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.dto.CarrerMoviesDTO;
import br.com.tiagohs.popmovies.model.media.Translation;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.model.movie.Movie;

public class MovieUtils {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static Calendar c = Calendar.getInstance();

    public static List<Movie> convertMovieDBToMovie(List<MovieDB> moviesDB) {
        List<Movie> movies = new ArrayList<>();

        for (MovieDB movieDB : moviesDB) {
            Movie movie = new Movie();
            movie.setId(movieDB.getIdServer());
            movie.setTitle(movieDB.getTitle());
            movie.setPosterPath(movieDB.getPosterPath());

            movies.add(movie);
        }

        return movies;
    }

    public static List<GenreDB> genreToGenreDB(List<Genre> genres) {
        GenreDB genreDB = null;
        List<GenreDB> genreDBs = new ArrayList<>();

        for (Genre genre : genres) {
            genreDB = new GenreDB();

            genreDB.setGenrerID(genre.getId());
            genreDB.setGenrerName(genre.getName());

            genreDBs.add(genreDB);
        }

        return genreDBs;
    }

    public static String formatIncludeLanguages(List<Translation> translations) {
        StringBuilder languages = new StringBuilder();

        for (Translation translation : translations) {
            languages.append(translation.getLanguage());
            languages.append(",");
        }

        return languages.toString();

    }

    public static int getYearByDate(String dateString) {
        Date date = null;

        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            return 0;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static void sortMoviesByDate(List<CarrerMoviesDTO> movieListDTO) {
        Collections.sort(movieListDTO, new Comparator<CarrerMoviesDTO>() {
            public int compare(CarrerMoviesDTO movie1, CarrerMoviesDTO movie2) {
                return movie2.getDate().compareTo(movie1.getDate());
            }
        });
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


    public static String formatCurrency(long currency) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
            return format.format(currency);
        } else {
            java.text.NumberFormat formatter = java.text.NumberFormat.getCurrencyInstance(Locale.getDefault());
            return formatter.format(currency);
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

    public static int getAge(Calendar calendar) {
        return getAge(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String formateDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            return dateString;
        }

        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return dateFormat.format(date);
    }

    public static Calendar formateStringToCalendar(String dateString) {
        Calendar cal = Calendar.getInstance();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(dateString));
        } catch (ParseException e) {
            return Calendar.getInstance();
        }

        return cal;
    }

    public static int[] getAllGenrerIDs() {
        return new int[]{28, 12, 16, 35, 80, 99, 18, 10751, 14, 36, 27, 10402, 9648, 10749, 878, 10770, 53, 10752, 37};
    }

    public static int[] getAllGenrerNames() {
        return new int[]{R.string.genere_action, R.string.genere_adventure, R.string.genere_animation, R.string.genere_comedy,
                         R.string.genere_crime, R.string.genere_documentary, R.string.genere_drama, R.string.genere_family,
                         R.string.genere_fantasy, R.string.genere_history, R.string.genere_horror,
                         R.string.genere_music, R.string.genere_mystery, R.string.genere_romance, R.string.genere_science_fiction,
                         R.string.genere_tv_movie, R.string.genere_thriller, R.string.genere_war, R.string.genere_western};
    }

    public static int[] getAllGenrerBackgroundResoucers() {
        return new int[]{R.drawable.img_gener_action, R.drawable.img_gener_adventure, R.drawable.img_gener_animation, R.drawable.img_gener_comedy,
                         R.drawable.img_gener_crime, R.drawable.img_gener_documentary, R.drawable.img_gener_drama, R.drawable.img_gener_family,
                         R.drawable.img_gener_fantasy, R.drawable.img_gener_history, R.drawable.img_gener_horror,
                         R.drawable.img_gener_music, R.drawable.img_gener_mistery, R.drawable.img_gener_romance, R.drawable.img_gener_science_fiction,
                         R.drawable.img_gener_tv_movie, R.drawable.img_gener_thriller, R.drawable.img_gener_war, R.drawable.img_gener_westeron};
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
