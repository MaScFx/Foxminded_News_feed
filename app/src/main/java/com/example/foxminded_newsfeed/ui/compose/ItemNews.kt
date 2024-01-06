package com.example.foxminded_newsfeed.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.foxminded_newsfeed.R
import com.example.foxminded_newsfeed.domain.model.NewsSource
import com.example.foxminded_newsfeed.ui.theme.ItemsBackground
import com.example.foxminded_newsfeed.ui.theme.PrimaryOrange

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemNews(
    imgUrl: String, title: String, newsSource: NewsSource, time: String, isFavorites: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(132.dp)
            .background(ItemsBackground)
            .padding(bottom = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 26.dp, bottom = 9.dp, top = 9.dp, end = 14.dp)
        ) {
            GlideImage(
                model = imgUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(114.dp)
                    .height(114.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(46.dp)
                        .fillMaxWidth()
                        .padding(top = 3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(74.dp)
                            .height(32.dp)
                            .align(Alignment.TopEnd)
                            .clip(RoundedCornerShape(9.dp))
                            .background(PrimaryOrange)
                    ) {
                        Text(
                            text = newsSource.name,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.poppins)),
                            modifier = Modifier.align(Alignment.Center)
                        )

                    }
                    Text(
                        text = time,
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(R.font.poppins)),
                        modifier = Modifier.align(Alignment.BottomStart)
                    )
                }

                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                    maxLines = 3
                )


            }
        }
        val favoriteIcon = if (isFavorites) R.drawable.bookmark_selected
        else R.drawable.bookmark

        val contentDescription = if (isFavorites) R.string.article_in_favorites
        else R.string.article_is_not_favorites

        Image(
            painter = painterResource(id =favoriteIcon ) ,
            contentDescription = stringResource(contentDescription),
            modifier = Modifier
                .padding(bottom = 17.dp, end = 23.dp)
                .align(Alignment.BottomEnd)
                .height(28.dp)
                .width(28.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ItemNewsPreview() {
    ItemNews(
        imgUrl = "https://cpad.ask.fm/865/242/195/-9996995-2020i7r-er5rgsls2hkd5p0/large/_pcfqBsO9Ck.jpg",
        title = "Good news!! Your DOG Win five million dollars! Graz!",
        time = "3 minutes ago",
        newsSource = NewsSource.BBC,
        isFavorites = true
    )
}