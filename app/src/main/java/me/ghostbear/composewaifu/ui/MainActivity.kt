package me.ghostbear.composewaifu.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.Charset
import me.ghostbear.composewaifu.R
import me.ghostbear.composewaifu.ui.favorites.FavoriteScreen
import me.ghostbear.composewaifu.ui.favorites.FavoriteViewModel
import me.ghostbear.composewaifu.ui.gallery.GalleryScreen
import me.ghostbear.composewaifu.ui.gallery.GalleryViewModel
import me.ghostbear.composewaifu.ui.picture.PictureScreen
import me.ghostbear.composewaifu.ui.picture.PictureViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavigation {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
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
    }
}

val items = listOf(
    Screen.Home,
    Screen.Favorite
)

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home : Screen("home", R.string.home, Icons.Outlined.Home)
    object Favorite : Screen("favorite", R.string.favorites, Icons.Outlined.FavoriteBorder)
}
