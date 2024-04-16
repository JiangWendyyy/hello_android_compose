import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.sharp.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.material.bottomappbar.BottomAppBar
import com.thoughtworks.androidtrain.model.entity.Tweet
import com.thoughtworks.androidtrain.view.composes.BottomItem
import com.thoughtworks.androidtrain.view.composes.TweetItem


@Composable
fun Menu() {
    Surface(color = MaterialTheme.colors.primary) {
        Column {
            Title("Discover")
            Center()
            BottomAppBar()

        }
    }
}

@Composable
fun Title(name: String){
    Text(text = name)
}

@Composable
fun Center(modifier: Modifier = Modifier){
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        var item = 1
        while (item<5){
            DiscoverItem()
            item += 1
        }
    }
}

@Composable
fun DiscoverItem(){
    Text(text = "item")
}

@Composable
fun BottomAppBar() {
    // 定义底部栏
    BottomNavigation(
        backgroundColor = Color.Blue, // 设置底部栏背景颜色
        elevation = 4.dp // 设置阴影效果
    ) {
        // 在底部栏中添加选项
        BottomNavigationItem(
            icon = { Icon(Icons.Sharp.Email, contentDescription = "Message") },
            label = { Text(text = "chat") },
            selected = false, // 默认选中
            onClick = { /* 处理点击事件 */ }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Star, contentDescription = "Settings") },
            label = { Text(text = "Discover") },
            selected = true, // 默认选中
            onClick = { /* 处理点击事件 */ }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Settings") },
            label = { Text(text = "Contacts") },
            selected = false, // 默认选中
            onClick = { /* 处理点击事件 */ }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text(text = "me") },
            selected = false, // 默认选中
            onClick = { /* 处理点击事件 */ }
        )
    }
}