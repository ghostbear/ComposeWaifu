package me.ghostbear.presentation

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.ghostbear.ui.favorities.FavoriteScreen
import me.ghostbear.ui.favorities.FavoriteViewModel
import me.ghostbear.ui.gallery.GalleryScreen
import me.ghostbear.ui.gallery.GalleryViewModel
import me.ghostbear.ui.picture.PictureScreen
import me.ghostbear.ui.picture.PictureViewModel
import java.net.URLEncoder
import java.nio.charset.Charset

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            if (currentRoute == Screen.Home.route || currentRoute == Screen.Favorite.route) {
                BottomNavigation {
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        BottomNavigationItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(stringResource(screen.resourceId)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                Log.d("Home", "Recomposed")
                val galleryViewModel = hiltViewModel<GalleryViewModel>()
                GalleryScreen(galleryViewModel) { url: String ->
                    navController.navigate(
                        "picture/${
                            URLEncoder.encode(
                                url,
                                Charset.defaultCharset().name()
                            )
                        }"
                    )
                }
            }
            composable(Screen.Favorite.route) {
                val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
                FavoriteScreen(favoriteViewModel) { url: String ->
                    navController.navigate(
                        "picture/${
                            URLEncoder.encode(
                                url,
                                Charset.defaultCharset().name()
                            )
                        }"
                    )
                }
            }
            composable(
                route = "picture/{waifuUrl}",
                arguments = listOf(navArgument("waifuUrl") { type = NavType.StringType })
            ) { backStackEntry ->
                val waifuUrl = backStackEntry.arguments?.getString("waifuUrl") as String
                Log.d("NavHost", waifuUrl)
                val pictureViewModel: PictureViewModel = hiltViewModel()
                PictureScreen(navController, pictureViewModel, waifuUrl)
            }
        }
    }
}

val items = listOf(
    Screen.Home,
    Screen.Favorite
)

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.home, Icons.Outlined.Home)
    object Favorite : Screen("favorite", R.string.collection, Icons.Outlined.FavoriteBorder)
}