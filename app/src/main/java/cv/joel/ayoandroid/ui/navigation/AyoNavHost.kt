package cv.joel.ayoandroid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cv.joel.ayoandroid.ui.home.HomeScreen
import cv.joel.ayoandroid.ui.lesson.LessonScreen


@Composable
fun AyoNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = NavRoutes.HOME) {
        composable(NavRoutes.HOME) {
            HomeScreen(onLessonClick = { lessonId ->
                navController.navigate(NavRoutes.lesson(lessonId))
            })
        }
        composable(NavRoutes.LESSON) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: return@composable
            LessonScreen(lessonId)
        }
    }
}
