package app.trian.tudu.ui.component.task

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    modifier: Modifier=Modifier
) {
    val ctx = LocalContext.current
    val currentWidth = ctx
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
    ) {
       Column {

       }
        Column(
            modifier = modifier.padding(
                horizontal = 30.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_empty_task),
                contentDescription = stringResource(R.string.content_description_empty_task),
                contentScale = ContentScale.FillWidth,
                modifier = modifier.size(currentWidth / 3)
            )
            Text(
                text = "Get started with your first task",
                style=TextStyle(
                    color = MaterialTheme.colors.onBackground
                )
            )
        }
        Box(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth()

        ) {

            Image(
                painter = painterResource(id = R.drawable.bg_bubble_create_new_task),
                contentDescription = stringResource(R.string.content_description_empty_task) ,
                contentScale= ContentScale.FillWidth,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 45.dp)
                    .align(Alignment.Center)
            )
            Column(
                modifier = modifier.align(Alignment.Center)
            ) {

                Text(
                    "Click here or + to create your first task",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    ),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.height(10.dp))
            }
        }
        Column {

        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun PreviewScreenEmptyTask(){
    TuduTheme {
        ScreenEmptyTask()
    }
}