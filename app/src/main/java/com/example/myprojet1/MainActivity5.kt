package com.example.myprojet1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprojet1.data.Product
import com.example.myprojet1.ui.theme.MyProjet1Theme

class MainActivity5 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyProjet1Theme { // Correct capitalization
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GreetingScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GreetingScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    Button(onClick = {
        with(sharedPreferences.edit()) {
            putBoolean("isLoggedIn", false)
            apply()
        }
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        (context as ComponentActivity).finish() // Close MainActivity5
    }) {
        Text(text = "Logout")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting(name = "User")
        Spacer(modifier = Modifier.height(16.dp))

        ListComposable()

        Spacer(modifier = Modifier.height(35.dp))
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Welcome, Motaz!",
        modifier = modifier,
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyProjet1Theme {
        GreetingScreen()
    }
}

@Composable
fun ListComposable(modifier: Modifier = Modifier) {
    val products = remember {
        mutableStateListOf(
            Product("Product 1", "Description 1", 100.99, R.drawable.car1),
            Product("Product 2", "Description 2", 60.99, R.drawable.car2),
            Product("Product 3", "Description 3", 150.99, R.drawable.car3)
        )
    }

    // Create a scrollable list using LazyColumn
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ItemComposable(product = product)
        }
    }
}

@Composable
fun ItemComposable(modifier: Modifier = Modifier, product: Product) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .padding(16.dp)
            .clickable {
                // Create an Intent to navigate to ProductDetailsActivity
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra("productName", product.name)
                intent.putExtra("productPrice", product.price)
                intent.putExtra("productImage", product.imageUrl)
                intent.putExtra("productDescription", product.description)
                context.startActivity(intent)
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            Text(text = product.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = product.imageUrl),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$${product.price}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}
