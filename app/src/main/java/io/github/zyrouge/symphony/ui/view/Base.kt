package io.github.zyrouge.symphony.ui.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.zyrouge.symphony.MainActivity
import io.github.zyrouge.symphony.Symphony
import io.github.zyrouge.symphony.services.groove.GrooveKinds
import io.github.zyrouge.symphony.ui.helpers.FadeTransition
import io.github.zyrouge.symphony.ui.helpers.Routes
import io.github.zyrouge.symphony.ui.helpers.ScaleTransition
import io.github.zyrouge.symphony.ui.helpers.SlideTransition
import io.github.zyrouge.symphony.ui.helpers.ViewContext
import io.github.zyrouge.symphony.ui.helpers.getRouteParameter
import io.github.zyrouge.symphony.ui.theme.SymphonyTheme
import io.github.zyrouge.symphony.utils.Preferences

/**
 * This Kotlin code defines a composable function called BaseView that sets up the navigation structure for an Android application. It uses the Jetpack Compose library to create the UI and the NavHost component to handle navigation between different screens.
 */
@Composable
fun BaseView(symphony: Symphony, activity: MainActivity) {
    val context = ViewContext(
        symphony = symphony,
        activity = activity,
        //navController is used to navigate between different screens.
        navController = rememberNavController(),
    )

    SymphonyTheme(context) {
        Surface(color = MaterialTheme.colorScheme.background) {
            /**
             * It uses the NavHost component to define the navigation routes for the application.
             * The startDestination is set to Routes.Home.route, which means that the Home screen is displayed when the application is launched.
             */
            NavHost(
                navController = context.navController,
                startDestination = Routes.Login.route,
            ) {
                //主页
                composable(
                    Routes.Home.template(),
                    enterTransition = { FadeTransition.enterTransition() },
                ) {
                    HomeView(context)
                }
                //跳转当前播放页
                composable(
                    Routes.NowPlaying.template(),
                    enterTransition = { SlideTransition.slideUp.enterTransition() },
                    exitTransition = { FadeTransition.exitTransition() },
                    popEnterTransition = { FadeTransition.enterTransition() },
                    popExitTransition = { SlideTransition.slideDown.exitTransition() },
                ) {
                    NowPlayingView(context)
                }
                //跳转播放队列
                composable(
                    Routes.Queue.template(),
                    enterTransition = { SlideTransition.slideUp.enterTransition() },
                    exitTransition = { SlideTransition.slideDown.exitTransition() },
                ) {
                    QueueView(context)
                }
                //跳转设置页
                composable(
                    Routes.Settings.template(),
                    enterTransition = { ScaleTransition.scaleDown.enterTransition() },
                    exitTransition = { ScaleTransition.scaleUp.exitTransition() },
                ) {
                    SettingsView(context)
                }
                //跳转artists
                composable(
                    Routes.Artist.template(),
                    enterTransition = { SlideTransition.slideLeft.enterTransition() },
                    exitTransition = { FadeTransition.exitTransition() },
                ) { backStackEntry ->
                    ArtistView(context, backStackEntry.getRouteParameter())
                }
                //跳转albums专辑视图
                composable(
                    Routes.Album.template(),
                    enterTransition = { SlideTransition.slideLeft.enterTransition() },
                    exitTransition = { FadeTransition.exitTransition() },
                ) { backStackEntry ->
                    AlbumView(context, backStackEntry.getRouteParameter())
                }
                //跳转搜索试图
                composable(
                    Routes.Search.template(),
                    enterTransition = { SlideTransition.slideDown.enterTransition() },
                    exitTransition = { SlideTransition.slideUp.exitTransition() },
                ) { backStackEntry ->
                    SearchView(
                        context,
                        backStackEntry.getRouteParameter()
                            .takeIf { it != "null" }
                            ?.let { GrooveKinds.valueOf(it) }
                    )
                }
                //跳转artists页
                composable(
                    Routes.AlbumArtist.template(),
                    enterTransition = { SlideTransition.slideLeft.enterTransition() },
                    exitTransition = { FadeTransition.exitTransition() },
                ) { backStackEntry ->
                    AlbumArtistView(context, backStackEntry.getRouteParameter())
                }
                composable(
                    Routes.Genre.template(),
                    enterTransition = { SlideTransition.slideLeft.enterTransition() },
                    exitTransition = { FadeTransition.exitTransition() },
                ) { backStackEntry ->
                    GenreView(context, backStackEntry.getRouteParameter())
                }
                composable(
                    Routes.Playlist.template(),
                    enterTransition = { SlideTransition.slideLeft.enterTransition() },
                    exitTransition = { FadeTransition.exitTransition() },
                ) { backStackEntry ->
                    PlaylistView(context, backStackEntry.getRouteParameter())
                }
                composable(
                    Routes.Lyrics.template(),
                    enterTransition = { SlideTransition.slideUp.enterTransition() },
                    exitTransition = { SlideTransition.slideDown.exitTransition() },
                ) {
                    LyricsView(context)
                }
                //Login
                composable(
                    Routes.Login.template(),
                    enterTransition = { SlideTransition.slideUp.enterTransition() },
                    exitTransition = { SlideTransition.slideDown.exitTransition() },
                ) {
                    LoginView(context)
                }
            }
        }
    }
}
