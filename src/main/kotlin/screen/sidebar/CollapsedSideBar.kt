package screen.sidebar

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CollapsedSideBar(
    collapseIconOnClick: () -> Unit
) {
    val list = (1..15).toList()
    val scrollState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(79.dp)
            .border(BorderStroke(1.dp, color = Color(0xFFE5E5E5))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(79.dp)
                .fillMaxWidth()
                .border(BorderStroke(1.dp, color = Color(0xFFE5E5E5)))
                .align(Alignment.CenterHorizontally)
                .clickable { collapseIconOnClick() }
        ) {
            Icon(
                imageVector = Icons.Default.DoubleArrow,
                tint = Color(0xFF99DCEC),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center)
            )
        }

        Box(modifier = Modifier.fillMaxWidth().weight(1.0f)) {
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                state = scrollState
            ) {
                items(list) { _ ->
                    Box(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .border(
                                border = BorderStroke(1.dp, color = Color(0xFFE5E5E5)),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 8.dp)
                    ) {
                        NetworkImage(
                            url = "https://lh3.googleusercontent.com/2hDpuTi-0AMKvoZJGd-yKWvK4tKdQr_kLIpB_qSeMau2TNGCNidAosMEvrEXFO9G6tmlFlPQplpwiqirgrIPWnCKMvElaYgI-HiVvXc=w600",
                            modifier = Modifier.clip(CircleShape).size(79.dp)
                        )
                    }
                }
            }

            VerticalScrollbar(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(
                    scrollState = scrollState
                )
            )
        }
    }
}