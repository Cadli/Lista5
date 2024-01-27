package pl.edu.uwr.pum.lista5

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlin.math.roundToInt


@Composable
fun GradesScreen(
    navController: NavController,
    viewModel: SubjectViewModel,
    onItemClick: (Subject) -> Unit

    ) {

    val subjects by viewModel.subjectsState.collectAsStateWithLifecycle()


    var sum by remember { mutableFloatStateOf(0.0f) }
    var count by remember { mutableFloatStateOf(0.0f) }
    var mean by remember { mutableFloatStateOf(0.0f) }

    LaunchedEffect(subjects) {
        sum = subjects.map { it.grade }.sum()
        count = subjects.size.toFloat()
        mean = if (count != 0.0f) sum / count else 0.0f
        mean = (mean * 100.0).roundToInt() / 100.0f
    }

    CustomScaffold(
        title = "Moje oceny",
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(subjects) { sub ->
                        SubjectItem(subject = sub, onItemClick = onItemClick)
                    }

                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 0.dp)
                ) {
                    SubjectItem(subject = Subject(name = "Średnia ocen", grade = mean), onItemClick = {})
                }
            }
        },
        bottomBar = {
            Button(
                onClick = { navController.navigate("add") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    text = "Nowy",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Composable
fun SubjectItem(
    subject: Subject,
    onItemClick: (Subject) -> Unit
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 2.dp, start = 15.dp, end = 15.dp)
            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(20.dp))
            .clickable { onItemClick(subject) }
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = subject.name,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(20.dp)
        )
        Text(
            text = subject.grade.toString(),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = if (subject.grade == 2.0f) Color.Red else MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(20.dp)
        )
    }
}