package com.example.spartans_supplements_sobile.screens

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spartans_supplements_sobile.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /**/ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.details),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                actions = {
                    IconButton(onClick = { /* */ }) {
                        Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = "Cart")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Row(
                        modifier = Modifier
                            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                            .padding(horizontal = 16.dp, vertical = 12.dp),

                        verticalAlignment = Alignment.CenterVertically,

                        horizontalArrangement = Arrangement.spacedBy(16.dp)

                    ) {
                        Text(text = "-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = "1", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { /*  */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text(
                            stringResource(id = R.string.add_to_cart),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        },
        containerColor = Color.White
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),

            ) {
            Image(
                painter = painterResource(id = R.drawable.black_square),
                contentDescription = "Black Square",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(250.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "MUSCLEPRO",
                color = Color.Gray
            )
            Text(
                text = "Premium Whey Protein Isolate ",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "$45.99",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Decription",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "High-quality whey protein isolate designed to\n" +
                        "support muscle growth and recovery. Contains\n" +
                        "25g of protein per serving with zero sugar and\n" +
                        "low carbs. Perfect for post-workout shakes or\n" +
                        "daily protein supplementation.",
                color = Color.Gray

            )
        }


    }

}