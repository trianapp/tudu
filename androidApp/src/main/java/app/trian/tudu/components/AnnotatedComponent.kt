package app.trian.tudu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.base.BaseMainApp


sealed class AnnotationTextItem(var text: String) {
    data class Text(var label: String) : AnnotationTextItem(label)
    data class Button(var label: String) : AnnotationTextItem(label)
}

@Composable
fun CheckBoxWithAction(
    checked: Boolean = false,
    labels: List<AnnotationTextItem> = listOf(),
    onCheckedChange: (Boolean) -> Unit = {},
    onTextClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val annotates = buildAnnotatedString {
        labels.forEachIndexed { index, data ->
            when (data) {
                is AnnotationTextItem.Button -> {
                    append(" ")
                    pushStringAnnotation(
                        tag = "text_${index}",
                        annotation = "text_${index}"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        append(data.text)
                    }
                    pop()
                }
                is AnnotationTextItem.Text -> {
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    ) {
                        append(data.text)
                    }
                }
            }
        }
    }
    Row(
        modifier = modifier.padding(
            vertical = 4.dp,
            horizontal = 4.dp
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = Color.Gray
            ),
            modifier = Modifier
                .size(16.dp)

        )

        Spacer(modifier = Modifier.width(8.dp))

        ClickableText(
            text = annotates,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSecondary
            ),
            onClick = { offset ->
                labels.forEachIndexed { index, _ ->
                    annotates.getStringAnnotations(
                        tag = "text_${index}",
                        start = offset,
                        end = offset
                    )
                        .firstOrNull()?.let {
                            onTextClick(index)
                        }
                }

            }
        )
    }
}

@Composable
fun TextWithAction(
    labels: List<AnnotationTextItem> = listOf(),
    onTextClick: (Int) -> Unit = {},
) {
    val annotates = buildAnnotatedString {
        labels.forEachIndexed { index, data ->
            when (data) {
                is AnnotationTextItem.Button -> {
                    append(" ")
                    pushStringAnnotation(
                        tag = "text_${index}",
                        annotation = "text_${index}"
                    )
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        append(data.text)
                    }
                    pop()
                }
                is AnnotationTextItem.Text -> {
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    ) {
                        append(data.text)
                    }
                }
            }
        }
    }
    ClickableText(
        text = annotates,
        style = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSecondary
        ),
        onClick = { offset ->
            labels.forEachIndexed { index, _ ->
                annotates.getStringAnnotations(
                    tag = "text_${index}",
                    start = offset,
                    end = offset
                )
                    .firstOrNull()?.let {
                        onTextClick(index)
                    }
            }

        }
    )

}

@Preview
@Composable
fun PreviewCheckboxInput() {
    BaseMainApp {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            CheckBoxWithAction(
                labels = listOf(
                    AnnotationTextItem.Text("Tes"),
                    AnnotationTextItem.Button("Ini Button"),
                    AnnotationTextItem.Text("Tes 1"),
                    AnnotationTextItem.Button("Ini Button 1")
                ),
                onTextClick = {}
            )
        }

    }
}