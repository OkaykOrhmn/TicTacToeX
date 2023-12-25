package com.rhmn.tictactoex

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rhmn.tictactoex.ui.home.HumanGame
import com.rhmn.tictactoex.ui.theme.TicTacToeXTheme
import com.rhmn.tictactoex.ui.theme.oSymbol
import com.rhmn.tictactoex.ui.theme.primary
import com.rhmn.tictactoex.ui.theme.xSymbol

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeXTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting() {
    val mContext = LocalContext.current
    var player1 by remember { mutableStateOf(TextFieldValue("")) }
    var player2 by remember { mutableStateOf(TextFieldValue("")) }

    @Composable
    fun OutLineTextFieldPl1() {
        OutlinedTextField(
            value = player1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = xSymbol
            ),
            label = { Text(text = "Enter Your Name Player 1", color = xSymbol) },
            onValueChange = {
                player1 = it
            }
        )
    }

    @Composable
    fun OutLineTextFieldPl2() {
        OutlinedTextField(
            value = player2,
            label = { Text(text = "Enter Your Name Player 2", color = oSymbol) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = oSymbol
            ),
            onValueChange = {
                player2 = it
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TopAppBar(
            title = {
            },
            navigationIcon = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TicTacToe X",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                }

            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.tertiary),
            modifier = Modifier
                .shadow(
                    elevation = 5.dp,
                    spotColor = Color.DarkGray,
                    shape = RoundedCornerShape(0.dp)
                )
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()


                ) {

                    Image(
                        painter = painterResource(id = R.drawable.xsymbol),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(4.dp)
                            .size(120.dp)
                    )



                    Image(
                        painter = painterResource(id = R.drawable.osymbol),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(4.dp)
                            .size(120.dp)
                    )


                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        OutLineTextFieldPl1()
                        OutLineTextFieldPl2()
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Choose Your Play Mode...",
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.BottomCenter)
                        )
                    }


                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    ) {
                        Button(
                            onClick = {
                                var intent = Intent(mContext, HumanGame::class.java)

                                intent.putExtra("player1",player1.text)
                                intent.putExtra("player2",player2.text)
                                mContext.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth(.42f),
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primary
                            )
                        ) {
                            Text(text = "With A Friend")

                        }

                        Button(
                            enabled = false,
                            onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth(.42f),
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            )
                        ) {
                            Text(text = "With An AI", color = primary)

                        }
                    }

//                    Box(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .weight(1f)
//                    ) {
//                        Button(
//                            onClick = { /*TODO*/ },
//                            modifier = Modifier,
//                            shape = CircleShape,
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = Color.White
//                            ),
//
//                            ) {
//                            Icon(
//                                imageVector = Icons.Default.Settings,
//                                contentDescription = "",
//                                modifier = Modifier.size(24.dp),
//                                tint = primary
//                            )
//
//                        }
//                    }

                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TicTacToeXTheme {
        Greeting()
    }
}