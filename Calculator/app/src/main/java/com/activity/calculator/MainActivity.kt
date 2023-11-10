    package com.activity.calculator

    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.compose.foundation.layout.PaddingValues
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.Icon
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Surface
    import androidx.compose.material3.Text
    import androidx.compose.material.BottomNavigation
    import androidx.compose.material.BottomNavigationItem
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.navigation.NavHostController
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.currentBackStackEntryAsState
    import androidx.navigation.compose.rememberNavController
    import com.activity.calculator.ui.components.CalculatorScreen
    import com.activity.calculator.ui.theme.CalculatorTheme
    import com.activity.calculator.utils.Constants

    class MainActivity : ComponentActivity() {
        @OptIn(ExperimentalMaterial3Api::class)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                CalculatorTheme {
                    val navController = rememberNavController()
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationBar(navController = navController, Constants.BottomNavItems)
                            }, content = { padding ->
                                NavHostContainer(navController = navController, padding = padding)
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun NavHostContainer(
        navController: NavHostController,
        padding: PaddingValues
    ) {

        NavHost(
            navController = navController,
            startDestination = "calculator",
            modifier = Modifier.padding(paddingValues = padding),

            builder = {
                // route : Home
                composable("calculator") {
                    CalculatorScreen()
                }
                // route : search
                composable("numgen") {
                    CalculatorScreen()
                }
            })
    }

    @Composable
    fun BottomNavigationBar(navController: NavHostController, bottomNavItems: List<BottomNavItem>) {

        BottomNavigation() {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            bottomNavItems.forEach { navItem ->
                val isItemSelected = currentRoute == navItem.route
                BottomNavigationItem(
                    selected = isItemSelected,
                    onClick = {
                        navController.navigate(navItem.route)
                    },
                    icon = {
                        Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                    },
                    label = {
                        Text(text = navItem.label)
                    },
                    alwaysShowLabel = true
                )
            }
        }
    }