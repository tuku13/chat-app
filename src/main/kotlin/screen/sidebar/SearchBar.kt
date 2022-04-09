package screen.sidebar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import theme.Theme

@Composable
fun SearchBar(
    onValueChange: (String) -> Unit,
    collapseIconOnClick: () -> Unit,
    theme: Theme
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(79.dp)
            .border(BorderStroke(1.dp, color = theme.border))
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            var text by remember { mutableStateOf("") }

            CompositionLocalProvider(
                LocalTextSelectionColors provides TextSelectionColors(
                    handleColor = theme.blue,
                    backgroundColor = theme.blue
                )
            ) {
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        onValueChange(it)
                    },
                    textStyle = TextStyle(

                    ),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .border(
                            border = BorderStroke(0.dp, Color.Transparent),
                            shape = CutCornerShape(0)
                        ),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = theme.body,
                        cursorColor = theme.cursor,
                        placeholderColor = theme.body,
                        backgroundColor = theme.background,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(0),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = theme.body
                        )
                    },
                    placeholder = {
                        Text("Search")
                    }
                )
            }

            Icon(
                imageVector = Icons.Default.DoubleArrow,
                tint = theme.blue,
                contentDescription = null,
                modifier = Modifier
                    .rotate(180.0f)
                    .size(40.dp)
                    .clickable { collapseIconOnClick() }
            )
        }
    }
}