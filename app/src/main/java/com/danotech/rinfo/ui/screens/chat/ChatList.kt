package com.danotech.rinfo.ui.screens.chat

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults.flingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.ProfileImageBitmap
import com.danotech.rinfo.ui.components.ProfileImageShimmer
import com.danotech.rinfo.ui.components.TruncateText

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatList(
    chats: List<Chat>,
    innerPadding: PaddingValues,
    onChatClick: () -> Unit = {}
) {
    LazyColumn(
        contentPadding = innerPadding,
        flingBehavior = flingBehavior()
    ) {
        items(chats) { chat ->
            ChatListItem(
                chat = chat,
                onChatClick = onChatClick
            )
        }
    }
}

@Composable
fun ChatListItem(
    chat: Chat,
    onChatClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val logoImage: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.no_image)

    val bitmap = remember {
        mutableStateOf(logoImage)
    }
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth(),
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    onChatClick()
                }
        ) {
            val imageSize = 35.dp
            ProfileImageShimmer(
                size = imageSize,
                isLoading = false
            ) {
                ProfileImageBitmap(
                    size = 40.dp,
                    bitmap = bitmap.value
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = chat.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = chat.time,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                    )
                }

                TruncateText(
                    text = chat.message,
                    maxWords = 8,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.44f)
                )
            }
        }
    }
    HorizontalDivider()
}


data class Chat(
    @DrawableRes val image: Int = R.drawable.no_image,
    val name: String = "",
    val message: String = "",
    val time: String = ""
)