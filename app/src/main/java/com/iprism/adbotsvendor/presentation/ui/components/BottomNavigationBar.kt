package com.iprism.adbotsvendor.presentation.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {

        val items = listOf(
            Triple(Screen.Home, R.drawable.home_img, stringResource(R.string.home)),
            Triple(Screen.Analytics, R.drawable.analytics_img, stringResource(R.string.analytics)),
            Triple(Screen.Profile, R.drawable.profile_img, stringResource(R.string.profile))
        )

        items.forEach { (screen, icon, label) ->

            val isSelected = currentRoute?.startsWith(screen.route) == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },

                icon = {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = label,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )
                },

                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },

                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent // remove background highlight
                )
            )
        }
    }
}