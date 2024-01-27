package pl.edu.uwr.pum.lista5

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.edu.uwr.pum.lista5.ui.theme.Lista5Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lista5Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val viewModel: SubjectViewModel = viewModel(
                        LocalViewModelStoreOwner.current!!,
                        "SubjectViewModel",
                        SubjectViewModelFactory(LocalContext.current.applicationContext as Application)
                    )

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "grades") {
                        composable("grades") {
                            GradesScreen(
                                navController = navController,
                                viewModel = viewModel,
                                onItemClick = { subject -> navController.navigate("edit/${subject.id}") }
                            )
                        }
                        composable("add") { AddScreen(navController, viewModel) }
                        composable("edit/{id}") { backStackEntry ->
                            val subjectId = backStackEntry.arguments?.getString("id") ?: ""
                            EditScreen(navController, viewModel, subjectId)
                        }



                    }
                }
            }
        }
    }
}

