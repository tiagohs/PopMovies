package br.com.tiagohs.core.navigation.models

import androidx.navigation.NamedNavArgument

interface Destination {
    val route: String
    val arguments: List<NamedNavArgument>

    val screenName: String
    val withToolbar: Boolean
}