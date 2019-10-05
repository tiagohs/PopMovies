# PopMovies

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a9f549fe009d410f807065fce0f17bf4)](https://www.codacy.com/app/tiagohs/PopMovies?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=tiagohs/PopMovies&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/tiagohs/PopMovies.svg?branch=master)](https://travis-ci.org/tiagohs/PopMovies)

### <a href="https://github.com/tiagohs/PopMovies-iOS">Clique aqui</a> para conhecer a versão iOS desse App!

<p>Uma rede social de filmes relativamente simples, onde você pode:</p>

<ul>
<li>Login com Facebook, Twitter ou Google, obtendo estátisticas de quantas horas você já gastou assistindo filmes, além de saber os seus gêneros favoritos;</li>
<li>Marcar seus filmes favoritos, quais filmes você já assistiu, os que quer assistir e os que não quer ver nunca;</li>
<li>Obter Rankings no IMDB, Rotten Tomatoes e Metascore;</li>
<li>Obter reviews do IMDB, Rotten Tomatoes e da Comunidade TMDB;</li>
<li>Obter informações sobre os atores, diretores e produtores, incluindo suas filmografias;</li>
<li>Assistir trailers originais, legendados ou dublados;</li>
<li>Visualizar e baixar Walpapers de vários filmes em diversas Qualidades;</li>
</ul>

<b>Donwload do APK</b>: Vá para a página de releases e baixe a última versão: <a href="https://github.com/tiagohs/PopMovies/releases">DOWNLOAD</a>

<p align="center">
  <img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/gif.gif" width="360" height="640">
</p>

### Configuração:

Antes de usar, você deverá preenxer no arquivo gradle.properties os seguintes campos:
<ul>
<li>MOVIEDB_API="" -> API Key do <a href="https://www.themoviedb.org/">theMovieDB.org</a></li>
<li>TWITTER_SECRET="" -> Secret Token para realizar o Login com o <a href="https://apps.twitter.com/">Twitter com o Fabric</a></li>
<li>TWITTER_KEY="" -> Key para realizar o Login com o <a href="https://apps.twitter.com/">Twitter com o Fabric</a></li>
<li>GOOGLE_KEY="" -> API Key para abrir os <a href="https://developers.google.com/youtube/android/player/?hl=pt-br">vídeos com o Youtube </a></li>
</ul>

Além diso, no Arquivo string.xml, adicione no campo "facebook_app_id" o id do Facebook, para o login utilizando a rede social funcionar corretamente.


### Base de Dados:
<ul>
<li><a href="https://www.themoviedb.org/"><b>TheMovieDB</b></a> - The Movie Database (TMDb) is a popular, user editable database for movies and TV shows.</li>
<li><a href="https://github.com/jvanbaarsen/omdb"><b>OMDB API</b></a> - The OMDb API is a free web service to obtain movie information, all content and images on the site are contributed and maintained by our users.</li>
</ul>

### Padrões de Projetos Adotados:

<ul>
<li><a href="http://antonioleiva.com/mvp-android">MVP</a> - Model View Presenter.</li>
</ul>

### Libaries utilizadas nesse Projeto:

<ul>
<li><a href="https://github.com/ReactiveX/RxAndroid">RxAndroid 2</a> - RxJava bindings for Android </li>
<li><a href="https://github.com/ReactiveX/RxJava">RxJava 2</a> - Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM. </li>
<li><a href="https://github.com/square/retrofit">Retrofit 2</a> - Type-safe HTTP client for Android and Java by Square, Inc.</li>
<li><a href="https://github.com/square/dagger">Dagger 2</a> - A fast dependency injector for Android and Java.</li>
<li><a href="https://github.com/JakeWharton/butterknife">Butterknife</a> - Bind Android views and callbacks to fields and methods.</li>
<li><a href="https://github.com/square/picasso">Picasso</a> - A powerful image downloading and caching library for Android .</li>
<li><a href="https://github.com/google/gson">Gson</a> - A powerful image downloading and caching library for Android. </li>
<li><a href="https://github.com/FasterXML/jackson-core">Jackson</a> - Core part of Jackson that defines Streaming API as well as basic shared abstractions <a href="http://wiki.fasterxml.com/JacksonHome">http://wiki.fasterxml.com/JacksonHome</a>. </li>
<li><a href="https://github.com/afollestad/material-dialogs">Material-dialogs
</a> - A beautiful, fluid, and customizable dialogs API. </li>
<li><a href="https://github.com/jd-alexander/LikeButton">LikeButton</a> - Twitter's heart animation for Android. </li>
<li><a href="https://github.com/square/okhttp">okhttp</a> - An HTTP+HTTP/2 client for Android and Java applications. </li>
<li><a href="https://github.com/balysv/material-ripple">Material Riple</a> - Android L Ripple effect wrapper for Views. </li>
<li><a href="https://github.com/lopspower/CircularImageView">CircularImageView</a> - Create circular ImageView in Android in the simplest way possible. </li>
<li><a href="https://github.com/amulyakhare/TextDrawable">TextDrawable</a> - This light-weight library provides images with letter/text like the Gmail app. It extends the Drawable class thus can be used with existing/custom/network ImageView classes. Also included is a fluent interface for creating drawables and a customizable ColorGenerator. </li>
<li><a href="https://github.com/chrisbanes/PhotoView">PhotoView</a> - Implementation of ImageView for Android that supports zooming, by various touch gestures. </li>
<li><a href="https://github.com/pnikosis/materialish-progress">Material-ish Progress</a> - A material style progress wheel compatible with 2.3. </li>
<li><a href="https://github.com/syedowaisali/crystal-range-seekbar">Crystal Range Seekbar</a> - An extended version of seekbar and range seekbar with basic and advanced customization. </li>
</ul>

## Screens

<p align="center">
<img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/login-screen.png" width="280" height="540"> <img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/image_lancamentos.png" width="280" height="540">
<img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/image_movies_list.png" width="280" height="540">
</p>

<p align="center">
<img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/image_estatisticas.png" width="280" height="540"> <img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/image_filme.png" width="280" height="540">
<img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/midia-screen.png" width="280" height="540">
</p>

<p align="center">
<img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/image_persons_list.png" width="280" height="540"> <img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/image_search.png" width="280" height="540">
<img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/image_person.png" width="280" height="540">
</p>

<p align="center">
<img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/image_wallpaper.png" width="280" height="540"> <img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/genres-screen.png" width="280" height="540">
<img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/image_perfil.png" width="280" height="540">
</p>

<p align="center">
<img src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/tablet-2.png" width="800" height="538">
</p>

### Desenvolvido por:

Tiago Henrique da Silva - tiago.hsilva@al.infnet.edu.br / tiago.silva.93@hotmail.com

<p><a href="https://www.facebook.com/tiago.henrique.16">
  <img alt="Follow me on Facebook" src="https://image.freepik.com/free-icon/facebook-symbol_318-37686.png" data-canonical-src="https://image.freepik.com/free-icon/facebook-symbol_318-37686.png" style="max-width:100%;" height="60" width="60">
</a>
<a href="https://br.linkedin.com/in/tiago-henrique-395868b7">
  <img alt="Add me to Linkedin" src="http://image.flaticon.com/icons/svg/34/34405.svg" data-canonical-src="http://image.flaticon.com/icons/svg/34/34405.svg" style="max-width:100%;" height="60" width="60">
</a>
<a href="http://tiagohs.net/">
  <img alt="Site Portfolio" src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/portfolio.png" data-canonical-src="https://raw.githubusercontent.com/tiagohs/PopMovies/master/arts/portfolio.png" style="max-width:100%;" height="60" width="60">
</a></p>

## License

    Copyright 2015 Tiago Henrique da Silva

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
