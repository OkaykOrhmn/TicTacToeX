package com.rhmn.tictactoex.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rhmn.tictactoex.model.Paint
import com.rhmn.tictactoex.R.drawable
import com.rhmn.tictactoex.model.User
import com.rhmn.tictactoex.ui.theme.TicTacToeXTheme
import com.rhmn.tictactoex.ui.theme.oSymbol
import com.rhmn.tictactoex.ui.theme.xSymbol
import com.rhmn.tictactoex.utils.Tools.Companion.findActivity

class HumanGame : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeXTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicTacToe2Player()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun TicTacToe2Player() {
    val context = LocalContext.current
    val activity = context.findActivity()
    val intent = activity?.intent
    var player1 by remember {
        mutableStateOf(intent!!.getStringExtra("player1").toString())
    }
    var player2 by remember {
        mutableStateOf(intent!!.getStringExtra("player2").toString())
    }


    LaunchedEffect(Unit) {
        if (player1.isEmpty()) {
            player1 = "Player 1"
        }
        if (player2.isEmpty()) {
            player2 = "Player 2"

        }

    }

    val winList = arrayOf(
        arrayOf(0, 1, 2),
        arrayOf(3, 4, 5),
        arrayOf(6, 7, 8),
        arrayOf(0, 3, 6),
        arrayOf(1, 4, 7),
        arrayOf(2, 5, 8),
        arrayOf(0, 4, 8),
        arrayOf(2, 4, 6),
    )
    var playerIcons by remember { mutableStateOf(User.NONE) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    var clickCount = 0
    var user1Win by remember { mutableStateOf(0) }
    var user2Win by remember { mutableStateOf(0) }

    var visibles = mutableStateListOf<Paint>().apply {

        for (i in 0..9) {
            add(Paint())
        }
    }




    fun getDrawableUser(): Int {
        return if (playerIcons == User.PLAYER_O) {
            playerIcons = User.PLAYER_X
            drawable.osymbol

        } else {
            playerIcons = User.PLAYER_O
            drawable.xsymbol


        }
    }

    fun clickOnLay(position: Int) {
        clickCount++;
        if (!visibles[position].visibility) {

            var pic = getDrawableUser()
            var isMatch = false
            visibles[position] =
                visibles[position].copy(visibility = true, paint = pic, user = playerIcons)

            if (clickCount > 4) {

                for (win in winList) {
                    var lists: ArrayList<User> = ArrayList()
                    win.forEach {
                        lists.add(visibles[it].user)
                    }
                    if (lists.all { it == lists[0] && it != User.NONE }) {
                        isMatch = true
                        if (lists[0] == User.PLAYER_O) {
                            user1Win++
                            Toast.makeText(context, "$player1 Win The Game", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "$player2 Turn to start the game!", Toast.LENGTH_LONG).show()

                        } else {
                            user2Win++
                            Toast.makeText(context, "$player2 Win The Game", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "$player1 Turn to start the game!", Toast.LENGTH_LONG).show()


                        }
                    }


                    if (isMatch) {
                        visibles.apply {
                            clear()
                            for (i in 0..9) {
                                add(Paint())
                            }
                        }
                        clickCount = 0
                        break

                    }
                }
            }

            if (clickCount == 9) {
                visibles.apply {
                    clear()
                    for (i in 0..9) {
                        add(Paint())
                    }
                }
                Toast.makeText(context, "Nobody Win The Game", Toast.LENGTH_SHORT).show()
                if (playerIcons == User.PLAYER_O) {
                    Toast.makeText(context, "$player2 Turn to start the game!", Toast.LENGTH_LONG).show()


                } else {
                    Toast.makeText(context, "$player1 Turn to start the game!", Toast.LENGTH_LONG).show()



                }
                clickCount = 0


            }
        }


    }

    Box(modifier = Modifier.fillMaxSize()) {


        Column {
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(32.dp, 8.dp)
                    .fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {


                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "X $player1",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End,
                            color = xSymbol
                        )
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(12.dp, 0.dp)
                        ) {
                            Text(
                                text = "$user1Win - $user2Win",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                color = Color.Gray
                            )
                        }
                        Text(
                            text =  "$player2 O",
                            modifier = Modifier.weight(1f),
                            color = oSymbol
                        )


                    }


                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                        .onSizeChanged {
                            size = it
                        }
                ) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
//                            .fillMaxHeight()
                            .size(
                                width = size.width.dp,
                                height = size.width.dp
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier = Modifier
                                        .weight(2f)
                                        .combinedClickable(enabled = true, onClick = {
                                            clickOnLay(0)


                                        })
                                ) {

                                    Row {
                                        AnimatedVisibility(
                                            visible = visibles[0].visibility,
                                            enter = fadeIn(
                                                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                                                initialAlpha = 0.4f
                                            ),
                                            exit = fadeOut(
                                                // Overwrites the default animation with tween
                                                animationSpec = tween(durationMillis = 250)
                                            )
                                        ) {

                                            Image(
                                                painter = painterResource(visibles[0].paint),
                                                "content description",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(24.dp)
                                            )
                                        }
                                    }

                                }
                                Divider(
                                    thickness = 1.dp,
                                    color = xSymbol,
                                    modifier = Modifier
                                        .fillMaxHeight()  //fill the max height
                                        .width(1.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .weight(2f)
                                        .combinedClickable(enabled = true, onClick = {
                                            clickOnLay(1)
                                        })
                                ) {

                                    Row {
                                        AnimatedVisibility(
                                            visible = visibles[1].visibility,
                                            enter = fadeIn(
                                                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                                                initialAlpha = 0.4f
                                            ),
                                            exit = fadeOut(
                                                // Overwrites the default animation with tween
                                                animationSpec = tween(durationMillis = 250)
                                            )
                                        ) {

                                            Image(
                                                painter = painterResource(visibles[1].paint),
                                                "content description",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(24.dp)
                                            )
                                        }
                                    }
                                }
                                Divider(
                                    thickness = 1.dp,
                                    color = xSymbol,
                                    modifier = Modifier
                                        .fillMaxHeight()  //fill the max height
                                        .width(1.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .weight(2f)
                                        .combinedClickable(enabled = true, onClick = {
                                            clickOnLay(2)
                                        })
                                ) {

                                    Row {
                                        AnimatedVisibility(
                                            visible = visibles[2].visibility,
                                            enter = fadeIn(
                                                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                                                initialAlpha = 0.4f
                                            ),
                                            exit = fadeOut(
                                                // Overwrites the default animation with tween
                                                animationSpec = tween(durationMillis = 250)
                                            )
                                        ) {

                                            Image(
                                                painter = painterResource(visibles[2].paint),
                                                "content description",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(24.dp)
                                            )
                                        }
                                    }
                                }


                            }

                            Divider(color = oSymbol, thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier = Modifier
                                        .weight(2f)
                                        .combinedClickable(enabled = true, onClick = {
                                            clickOnLay(3)
                                        })
                                ) {

                                    Row {
                                        AnimatedVisibility(
                                            visible = visibles[3].visibility,
                                            enter = fadeIn(
                                                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                                                initialAlpha = 0.4f
                                            ),
                                            exit = fadeOut(
                                                // Overwrites the default animation with tween
                                                animationSpec = tween(durationMillis = 250)
                                            )
                                        ) {

                                            Image(
                                                painter = painterResource(visibles[3].paint),
                                                "content description",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(24.dp)
                                            )
                                        }
                                    }
                                }
                                Divider(
                                    thickness = 1.dp,
                                    color = xSymbol,
                                    modifier = Modifier
                                        .fillMaxHeight()  //fill the max height
                                        .width(1.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .weight(2f)
                                        .combinedClickable(enabled = true, onClick = {
                                            clickOnLay(4)
                                        })
                                ) {

                                    Row {
                                        AnimatedVisibility(
                                            visible = visibles[4].visibility,
                                            enter = fadeIn(
                                                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                                                initialAlpha = 0.4f
                                            ),
                                            exit = fadeOut(
                                                // Overwrites the default animation with tween
                                                animationSpec = tween(durationMillis = 250)
                                            )
                                        ) {

                                            Image(
                                                painter = painterResource(visibles[4].paint),
                                                "content description",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(24.dp)
                                            )
                                        }
                                    }

                                }
                                Divider(
                                    thickness = 1.dp,
                                    color = xSymbol,
                                    modifier = Modifier
                                        .fillMaxHeight()  //fill the max height
                                        .width(1.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .weight(2f)
                                        .combinedClickable(enabled = true, onClick = {
                                            clickOnLay(5)
                                        })
                                ) {

                                    Row {
                                        AnimatedVisibility(
                                            visible = visibles[5].visibility,
                                            enter = fadeIn(
                                                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                                                initialAlpha = 0.4f
                                            ),
                                            exit = fadeOut(
                                                // Overwrites the default animation with tween
                                                animationSpec = tween(durationMillis = 250)
                                            )
                                        ) {

                                            Image(
                                                painter = painterResource(visibles[5].paint),
                                                "content description",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(24.dp)
                                            )
                                        }
                                    }

                                }


                            }

                            Divider(color = oSymbol, thickness = 1.dp)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier = Modifier
                                        .weight(2f)
                                        .combinedClickable(enabled = true, onClick = {
                                            clickOnLay(6)
                                        })
                                ) {

                                    Row {
                                        AnimatedVisibility(
                                            visible = visibles[6].visibility,
                                            enter = fadeIn(
                                                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                                                initialAlpha = 0.4f
                                            ),
                                            exit = fadeOut(
                                                // Overwrites the default animation with tween
                                                animationSpec = tween(durationMillis = 250)
                                            )
                                        ) {

                                            Image(
                                                painter = painterResource(visibles[6].paint),
                                                "content description",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(24.dp)
                                            )
                                        }
                                    }

                                }

                                Divider(
                                    thickness = 1.dp,
                                    color = xSymbol,
                                    modifier = Modifier
                                        .fillMaxHeight()  //fill the max height
                                        .width(1.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .weight(2f)
                                        .combinedClickable(enabled = true, onClick = {
                                            clickOnLay(7)
                                        })
                                ) {

                                    Row {
                                        AnimatedVisibility(
                                            visible = visibles[7].visibility,
                                            enter = fadeIn(
                                                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                                                initialAlpha = 0.4f
                                            ),
                                            exit = fadeOut(
                                                // Overwrites the default animation with tween
                                                animationSpec = tween(durationMillis = 250)
                                            )
                                        ) {

                                            Image(
                                                painter = painterResource(visibles[7].paint),
                                                "content description",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(24.dp)
                                            )
                                        }
                                    }
                                }

                                Divider(
                                    thickness = 1.dp,
                                    color = xSymbol,
                                    modifier = Modifier
                                        .fillMaxHeight()  //fill the max height
                                        .width(1.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .weight(2f)
                                        .combinedClickable(enabled = true, onClick = {
                                            clickOnLay(8)
                                        })
                                ) {

                                    Row {
                                        AnimatedVisibility(
                                            visible = visibles[8].visibility,
                                            enter = fadeIn(
                                                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                                                initialAlpha = 0.4f
                                            ),
                                            exit = fadeOut(
                                                // Overwrites the default animation with tween
                                                animationSpec = tween(durationMillis = 250)
                                            )
                                        ) {

                                            Image(
                                                painter = painterResource(visibles[8].paint),
                                                "content description",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(24.dp)
                                            )
                                        }
                                    }
                                }


                            }
                        }

                    }


                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
//                    Button(
//                        onClick = { /*TODO*/ }, modifier = Modifier
//                            .padding(0.dp, 24.dp),
//                        shape = RoundedCornerShape(100)
//                    ) {
//                        Icon(imageVector = Icons.Default.Settings, contentDescription = "")
//                    }
                }


            }


        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    TicTacToeXTheme {
        TicTacToe2Player()
    }
}