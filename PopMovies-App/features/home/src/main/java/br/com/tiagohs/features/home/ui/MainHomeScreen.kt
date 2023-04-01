package br.com.tiagohs.popmovies.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import br.com.tiagohs.features.home.models.HomeBottomItem

@Composable
fun MainHomeRoute(
    bottomBarItems: List<HomeBottomItem>,
    onClickBottomNavigationItem: (route: String) -> Unit,
    navHost: @Composable (PaddingValues) -> Unit
) {

    MainHomeScreen(
        onClickBottomNavigationItem = onClickBottomNavigationItem,
        bottomBarItems = bottomBarItems,
        navHost = navHost
    )
}

@Composable
fun MainHomeScreen(
    bottomBarItems: List<HomeBottomItem>,
    onClickBottomNavigationItem: (route: String) -> Unit,
    navHost: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = {
            var selectedItem by remember { mutableStateOf(0) }

            NavigationBar {
                bottomBarItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
                            )
                        },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index

                            onClickBottomNavigationItem(item.route)
                        }
                    )
                }

            }
        }
    ) {
        navHost(it)
    }
}