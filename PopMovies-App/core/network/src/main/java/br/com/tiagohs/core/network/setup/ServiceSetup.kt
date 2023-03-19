package br.com.tiagohs.core.network.setup

import br.com.tiagohs.core.network.BuildConfig
import br.com.tiagohs.core.network.errorHandler.ErrorHandlerAdapterFactory
import com.github.simonpercic.oklog3.OkLogInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.logging.Logger

fun createRetrofit(
    okHttpClient: OkHttpClient,
    gson: Gson,
    baseUrl: String
): Retrofit {
    return Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(ErrorHandlerAdapterFactory())
        .build()
}

fun createGson(
    typeAdapters: List<Pair<Type, Any>> = emptyList()
): Gson {
    val builder = GsonBuilder()

    typeAdapters.forEach {
        builder.registerTypeAdapter(it.first, it.second)
    }
    return builder
        .create()
}

fun createClient(interceptors: List<Interceptor> = emptyList()): OkHttpClient {
    val builder = OkHttpClient()
        .newBuilder()


    if (interceptors.isNotEmpty()) {
        interceptors.forEach {
            builder.addInterceptor(it)
        }
    }

    if (BuildConfig.DEBUG) {
        builder.apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            addInterceptor(OkLogInterceptor.builder().build())
        }
    }

    return builder.build()
}

fun addHeaders(
    headers: List<Pair<String, String>> = emptyList(),
): Interceptor {
    return Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("X-Requested-With", "XMLHttpRequest")
            .header("Content-Type", "application/json;charset=utf-8")

        if (headers.isNotEmpty()) {
            headers.forEach {
                requestBuilder.header(it.first, it.second)
            }
        }

        val request = requestBuilder.build()

        if (BuildConfig.DEBUG) {
            Logger.getLogger("REQUEST_APP").warning("REQUEST")
            Logger.getLogger("REQUEST_APP").warning("${request.url}")

            request.body?.let { body ->
                if (body is MultipartBody) {
                    Logger.getLogger("REQUEST_APP").warning("${body.parts}")
                    Logger.getLogger("REQUEST_APP").warning("$body")
                }
            }

            Logger.getLogger("REQUEST_APP").warning("END REQUEST")
        }

        chain.proceed(request)
    }
}