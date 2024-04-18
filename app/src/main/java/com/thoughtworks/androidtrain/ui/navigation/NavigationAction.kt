package com.thoughtworks.androidtrain.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.thoughtworks.androidtrain.R

object Route {
    const val CHAT = "Chats"
    const val CONTACTS = "Contacts"
    const val DISCOVER = "Discover"
    const val ME = "Me"
}

data class TopLevelDestination(
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val contentDescription: Int
)


val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = Route.CHAT,
        selectedIcon = R.drawable.chat_selected,
        unselectedIcon = R.drawable.chat,
        contentDescription = R.string.tab_chat
    ),
    TopLevelDestination(
        route = Route.CONTACTS,
        selectedIcon = R.drawable.contacts_selected,
        unselectedIcon = R.drawable.contacts,
        contentDescription = R.string.tab_contacts
    ),
    TopLevelDestination(
        route = Route.DISCOVER,
        selectedIcon = R.drawable.compass_selected,
        unselectedIcon = R.drawable.compass,
        contentDescription = R.string.tab_discover
    ),
    TopLevelDestination(
        route = Route.ME,
        selectedIcon = R.drawable.me_selected,
        unselectedIcon = R.drawable.me,
        contentDescription = R.string.tab_me
    )

)


class NavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }

            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (TopLevelDestination) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            NavigationBarItem(
                selected = selectedDestination == destination.route,
                onClick = { navigateToTopLevelDestination(destination) },
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = ImageVector.vectorResource(destination.selectedIcon),
                        contentDescription = stringResource(id = destination.contentDescription)
                    )
                },
                label = {
                    Text(text = stringResource(id = destination.contentDescription))
                }
            )
        }
    }
}