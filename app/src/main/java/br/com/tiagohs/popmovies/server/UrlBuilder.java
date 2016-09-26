package br.com.tiagohs.popmovies.server;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.util.enumerations.Method;
import br.com.tiagohs.popmovies.util.enumerations.Param;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;

/**
 * Created by Tiago Henrique on 25/08/2016.
 */
public class UrlBuilder {
    private final String BARRA = "/";

    private StringBuilder mUrl;
    private String mAppendToResponse;
    private Uri.Builder mUrlBuilder;
    private Map<String, String> mParameters;

    public UrlBuilder() {
        mUrl = new StringBuilder();
        mParameters = new HashMap<>();
    }

    public UrlBuilder addBaseUrl(String baseURL) {
        mUrl.append(baseURL);
        return this;
    }

    public UrlBuilder addId(int id) {
        mUrl.append(String.valueOf(id));
        return this;
    }

    public UrlBuilder addIdBySubMethod(int id) {
        mUrl.append(String.valueOf(id) + BARRA);
        return this;
    }

    public UrlBuilder addMethod(Method method) {
        mUrl.append(method.getValue() + BARRA);
        return this;
    }

    public UrlBuilder addSubMethod(SubMethod subMethod) {
        mUrl.append(subMethod.getValue());
        return this;
    }

    public UrlBuilder addParameters(Map<String, String> parameters) {
        this.mParameters = parameters;
        return this;
    }

    public UrlBuilder addAppendToResponse(String[] appendToResponse) {
        mAppendToResponse = toList(appendToResponse);
        return this;
    }

    public String build() {
        mUrlBuilder = Uri.parse(mUrl.toString()).buildUpon();

        if (mParameters != null)
            for (String paramName : mParameters.keySet())
                mUrlBuilder.appendQueryParameter(paramName, mParameters.get(paramName));

        if (mAppendToResponse != null)
            mUrlBuilder.appendQueryParameter(Param.APPEND.getParam(), mAppendToResponse);

        return mUrlBuilder.toString();
    }

    private String toList(final String[] appendToResponse) {
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
    }
}
