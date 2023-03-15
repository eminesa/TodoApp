package com.note.noteapp.compose

import android.app.Activity
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.material.appbar.MaterialToolbar
import com.note.noteapp.R
import com.note.noteapp.compose.home.HomeScreen

enum class NotePage(
    @StringRes val titleResId: Int,
    @DrawableRes val drawableResId: Int
) {
    ALL_NOTE(R.string.all_note, R.drawable.ic_launcher_background),
    DONE_NOTE(R.string.done_note, R.drawable.ic_launcher_background)
}

@Composable
fun NoteApp(
    onPageChange: (NotePage) -> Unit = {},
    onAttached: (MaterialToolbar) -> Unit = {},
) {
    val navController = rememberNavController()
    SunFlowerNavHost(
        navController = navController,
        onPageChange = onPageChange,
        onAttached = onAttached
    )
}

@Composable
fun SunFlowerNavHost(
    navController: NavHostController,
    onPageChange: (NotePage) -> Unit = {},
    onAttached: (MaterialToolbar) -> Unit = {},
) {
    val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNoteClick = {
                    navController.navigate("plantDetail/${it.plantId}")
                },
                onPageChange = onPageChange,
                onAttached = onAttached
            )
        }
        composable(
            "plantDetail/{plantId}",
            arguments = listOf(navArgument("plantId") {
                type = NavType.StringType
            })
        ) {
            /*  PlantDetailsScreen(
                  onBackClick = { navController.navigateUp() },
                  onShareClick = {
                      createShareIntent(activity, it)
                  },
                  onGalleryClick = {
                      navController.navigate("gallery/${it.name}")
                  }
              ) */
        }
        composable(
            "gallery/{plantName}",
            arguments = listOf(navArgument("plantName") {
                type = NavType.StringType
            })
        ) {
            /* GalleryScreen(
                   onPhotoClick = {
                       //val uri = Uri.parse(it.user.attributionUrl)
                       //val intent = Intent(Intent.ACTION_VIEW, uri)
                       //activity.startActivity(intent)
                   },
                   onUpClick = {
                       navController.navigateUp()
                   })  */
        }
    }
}

// Helper function for calling a share functionality.
// Should be used when user presses a share button/menu item.
private fun createShareIntent(activity: Activity, plantName: String) {
    val shareText =
        activity.getString(androidx.appcompat.R.string.abc_action_bar_home_description, plantName)
    val shareIntent = ShareCompat.IntentBuilder(activity)
        .setText(shareText)
        .setType("text/plain")
        .createChooserIntent()
        .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    activity.startActivity(shareIntent)
}