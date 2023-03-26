package br.com.tiagohs.core.network

import br.com.tiagohs.core.network.setup.createClient
import br.com.tiagohs.core.network.setup.createGson
import br.com.tiagohs.core.network.setup.createRetrofit
import okhttp3.Interceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.lang.reflect.Type

fun createBaseNetworkModule(
    clientName: String,
    gsonName: String,
    retrofitName: String,
    baseUrl: String,
    typeAdapters: List<Pair<Type, Any>> = emptyList(),
    interceptors: List<Interceptor> = emptyList()
) = module {
    factory(
        named(
            name = clientName
        )
    ) {
        createClient(
            interceptors = interceptors
        )
    }
    factory(
        named(
            name = gsonName
        )
    ) {
        createGson(
            typeAdapters = typeAdapters
        )
    }
    single(
        named(
            name = retrofitName
        )
    ) {
        createRetrofit(
            get(named(name = clientName)),
            get(named(name = gsonName)),
            baseUrl
        )
    }
}