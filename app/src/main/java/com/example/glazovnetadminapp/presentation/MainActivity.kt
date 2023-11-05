package com.example.glazovnetadminapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.glazovnetadminapp.presentation.announcements.AddAnnouncementScreen
import com.example.glazovnetadminapp.presentation.announcements.AnnouncementsScreen
import com.example.glazovnetadminapp.presentation.announcements.AnnouncementsViewModel
import com.example.glazovnetadminapp.presentation.home.HomeScreen
import com.example.glazovnetadminapp.presentation.posts.editPost.EditPostScreen
import com.example.glazovnetadminapp.presentation.posts.postDetails.PostDetailScreen
import com.example.glazovnetadminapp.presentation.posts.postsList.PostsScreen
import com.example.glazovnetadminapp.presentation.posts.postsList.PostsScreenViewModel
import com.example.glazovnetadminapp.presentation.settings.SettingsScreen
import com.example.glazovnetadminapp.presentation.tariffs.editTariffs.EditTariffScreen
import com.example.glazovnetadminapp.presentation.tariffs.tariffsList.TariffsScreen
import com.example.glazovnetadminapp.presentation.tariffs.tariffsList.TariffsScreenViewModel
import com.example.glazovnetadminapp.ui.theme.GlazovNetAdminAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GlazovNetAdminAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(navController)
                        }
                        composable("settings") {
                            SettingsScreen(navController = navController)
                        }
                        navigation(
                            startDestination = "posts_list",
                            route = "posts"
                        ) {
                            composable("posts_list") {
                                val viewModel = it.sharedViewModel<PostsScreenViewModel>(navController = navController)
                                PostsScreen(
                                    navController,
                                    viewModel
                                )
                            }
                            composable("post_details") {
                                val viewModel = it.sharedViewModel<PostsScreenViewModel>(navController = navController)
                                PostDetailScreen(
                                    navController,
                                    viewModel
                                )
                            }
                            composable("edit_post") {
                                val viewModel = it.sharedViewModel<PostsScreenViewModel>(navController = navController)
                                EditPostScreen(
                                    navController = navController,
                                    viewModel
                                )
                            }
                        }
                        navigation (
                            startDestination = "announcements_list",
                            route = "announcements"
                        ) {
                            composable("announcements_list") {
                                val viewModel = it.sharedViewModel<AnnouncementsViewModel>(navController = navController)
                                AnnouncementsScreen(
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }
                            composable("add_announcement") {
                                val viewModel = it.sharedViewModel<AnnouncementsViewModel>(navController = navController)
                                AddAnnouncementScreen(
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }
                        }
                        navigation(
                            startDestination = "tariffs_list",
                            route = "tariffs"
                        ) {
                            composable("tariffs_list") {
                                val viewModel = it.sharedViewModel<TariffsScreenViewModel>(navController = navController)
                                TariffsScreen(
                                    navController = navController,
                                    viewModel
                                )
                            }
                            composable("edit_tariff") {
                                val viewModel = it.sharedViewModel<TariffsScreenViewModel>(navController = navController)
                                EditTariffScreen(
                                    navController,
                                    viewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
