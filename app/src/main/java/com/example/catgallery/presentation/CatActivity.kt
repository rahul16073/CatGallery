package com.example.catgallery.presentation

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.catgallery.R
import com.example.catgallery.domain.model.CatData
import com.example.catgallery.ui.theme.CatGalleryTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.ViewModelLifecycle

@AndroidEntryPoint
class CatActivity : ComponentActivity() {
    private lateinit var viewModel: CatViewModel
    private var mCtx: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CatViewModel::class.java]
        mCtx = this
        if (viewModel.isNetworkAvailable(this))
            viewModel.fetchCatList()
        else
            Toast.makeText(mCtx, "Network not available", Toast.LENGTH_LONG).show()
        setContent {
            SetContentView(this)
        }
    }

    @Composable
    private fun SetContentView(mCtx: Context) {
        CatGalleryTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(modifier = Modifier) {
                    TopAppBarView()
                    TryAgainView(isError = viewModel.isError.value, text = "Error Occurred", buttonText = "Try Again")
                    SetCatListLazyView(catList = viewModel.catList)
               }
            }
        }
    }

    @Composable
    private fun SetCatListLazyView(catList: List<CatData>) {
        LazyColumn(
            modifier = Modifier.padding(all = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(catList) { index, item ->
                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    ItemView(catData = item, index)
                }
            }
        }
    }

    @Composable
    fun ItemView(catData: CatData, index: Int) {
        Column(modifier = Modifier.width(200.dp)) {
            AsyncImage(
                model = catData.url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .height(200.dp),
                alignment = Alignment.Center
            )
            Text(
                text = "cat Pic: " + (index + 1),
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopAppBarView(){
        TopAppBar(
            title = {
                Text(text = "Cat Gallery", fontSize = 18.sp, color = colorResource(
                    id = R.color.teal_700
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp), textAlign = TextAlign.Center)
            },
            modifier = Modifier
                .height(30.dp)
        )
    }
    @Composable
    private fun TryAgainView(isError: Boolean, text: String, buttonText: String) {
        if (isError) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {

                Text(
                    text = text,
                    color = colorResource(id = R.color.color_FB7134),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = buttonText.uppercase(),
                    fontWeight = FontWeight.Light,
                    color = colorResource(id = R.color.white),
                    fontSize = 15.sp,
                    modifier = Modifier
                        .clickable { viewModel.fetchCatList() }
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    colorResource(id = R.color.color_FB3D34),
                                    colorResource(id = R.color.color_F51106)
                                )
                            )
                        )
                        .padding(start = 22.dp, end = 22.dp, top = 7.dp, bottom = 7.dp)
                )
            }
        }
    }
}
