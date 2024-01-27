package pl.edu.uwr.pum.lista5

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Left
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  AddScreen(navController: NavController, viewModel: SubjectViewModel) {

    var text by remember { mutableStateOf("") }
    var rating by remember { mutableFloatStateOf(3.0f) }
    val validValues = listOf(2.0f, 3.0f, 3.5f, 4.0f, 4.5f, 5.0f)


    CustomScaffold(
        title = "Dodaj Nową Ocenę",
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = {
                        Text(
                            text = "Nazwa przedmiotu",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(10.dp)
                    ) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                )

                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Ocena: ",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 30.sp)

                    Slider(
                        value = rating,
                        onValueChange = { rating = findClosestValidValue(it, validValues) },
                        valueRange = validValues.first()..validValues.last(),
                        steps = validValues.size - 1,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp, end = 8.dp)
                    )
                    Text(
                        text = "   %.1f".format(rating),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold)
                }


            }
        },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.addSubject(Subject(name = text, grade = rating))
                    navController.navigate("grades") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                enabled = text.isNotEmpty()
            ) {
                Text(
                    text = "Dodaj",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 30.sp
                )
            }
        }
    )
}



