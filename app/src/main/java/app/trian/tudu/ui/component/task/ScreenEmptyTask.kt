package app.trian.tudu.ui.component.task

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trian.tudu.R
import app.trian.tudu.ui.component.customShape.TriangleEdgeShape
import app.trian.tudu.ui.theme.TuduTheme

/**
 * Screen Empty state
 * author Trian Damai
 * created_at 01/02/22 - 11.30
 * site https://trian.app
 */

@Composable
fun ScreenEmptyTask(
    modifier: Modifier=Modifier,
    onNewTask:()->Unit
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxHeight(
            fraction = 0.9f
        )
    ) {
        Box(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth()

        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_bubble_create_new_task),
                contentDescription ="" ,
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .clickable { onNewTask() }
            )
            Column(
                modifier = modifier.align(Alignment.TopCenter)
            ) {
                Spacer(modifier = modifier.height(4.dp))
                Text(
                    "Click here or + to create your first task",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    ),
                    modifier = modifier
                        .fillMaxWidth(fraction = 0.5f)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewScreenEmptyTask(){
    TuduTheme {
        ScreenEmptyTask(){}
    }
}