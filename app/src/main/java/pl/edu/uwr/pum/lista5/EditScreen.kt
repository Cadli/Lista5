package pl.edu.uwr.pum.lista5

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pl.edu.uwr.pum.lista5.Constants.gradesBank


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    navController: NavController,
    viewModel: SubjectViewModel,
    subjectId: String
) {

    val subjectState by viewModel.getSubjectById(subjectId).collectAsState(initial = null)

    val name = subjectState?.name.orEmpty()
    val grade = subjectState?.grade ?: 0.0f

    var text by remember { mutableStateOf(name) }
    var rating by remember { mutableFloatStateOf(grade) }

    LaunchedEffect(subjectState) {
        text = name
        rating = grade
    }

    CustomScaffold(
        title = "Edytuj",
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
                    label = { Text(
                        text = "Nazwa przedmiotu",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(10.dp)
                    ) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    textStyle = TextStyle(fontSize = 25.sp),
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
                        onValueChange = { rating = findClosestValidValue(it, gradesBank) },
                        valueRange = gradesBank.first()..gradesBank.last(),
                        steps = gradesBank.size - 1,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.updateSubject(Subject(id = subjectId.toInt(), name = text, grade = rating))
                        navController.navigate("grades") },
                    enabled = text.isNotEmpty(),
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "Zapisz",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 30.sp
                    )
                }

                Button(
                    onClick = {
                        viewModel.deleteSubject(subjectId)
                        navController.navigate("grades") },
                    enabled = text.isNotEmpty(),
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "Usuń",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 30.sp
                    )
                }
            }
        }
    )
}


