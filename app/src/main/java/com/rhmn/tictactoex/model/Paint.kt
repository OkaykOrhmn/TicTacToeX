package com.rhmn.tictactoex.model

import com.rhmn.tictactoex.R

data class Paint(
    var visibility: Boolean = false,
    var paint: Int = R.drawable.osymbol,
    var user: User = User.NONE,
)
