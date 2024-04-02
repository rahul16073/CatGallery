package com.example.catgallery.presentation

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.catgallery.domain.model.CatData
import com.example.catgallery.ui.theme.CatGalleryTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.ViewModelLifecycle

@AndroidEntryPoint
class CatActivity : ComponentActivity() {
    private lateinit var viewModel: CatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CatViewModel::class.java]
        setContent {
            SetContentView(this)
        }
    }

    @Composable
    private fun SetContentView(mCtx: Context){
            CatGalleryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if(viewModel.isNetworkAvailable(this)) {
                        viewModel.fetchCatList()
                        Column(modifier = Modifier) {
                            SetCatListLazyView(catList = viewModel.catList)
                        }
                    }
                    else{
                        Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    @Composable
    private fun SetCatListLazyView(catList: List<CatData>){
        LazyColumn(modifier = Modifier.padding(all = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally){
            itemsIndexed(catList){index, item->
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
}

@Composable
fun ItemView(catData: CatData, index: Int){
        Column(modifier = Modifier.width(200.dp)){
            AsyncImage(
                model = catData.url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .height(200.dp),
                alignment = Alignment.Center
            )
            Text(text = "cat Pic: "+(index+1), modifier = Modifier.padding(top = 5.dp).fillMaxWidth()
            , textAlign = TextAlign.Center)
        }
}
