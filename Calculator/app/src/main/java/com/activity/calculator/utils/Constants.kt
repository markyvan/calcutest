package com.activity.calculator.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Pin
import com.activity.calculator.models.BottomNavItem



object Constants {
    val BottomNavItems = listOf(

        BottomNavItem(
            label = "Calculator",
            icon = Icons.Default.Calculate,
            route = "calculator"
        ),
        BottomNavItem(
            label = "Number Generator",
            icon = Icons.Default.Pin,
            route = "numgen"
        )
    )
}
