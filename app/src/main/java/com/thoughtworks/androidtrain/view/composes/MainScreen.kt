import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.sharp.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MyScreen() {

    var currentPage by remember { mutableStateOf("Discover") }
    // 创建一个Scaffold，它将包含我们的顶部和底部栏
    //val innerPadding = 20.dp
    Scaffold(
        topBar = { TopBar("Discover") },
        bottomBar = { // 定义底部栏
            BottomNavigation(
                backgroundColor = Color.DarkGray, // 设置底部栏背景颜色
                elevation = 4.dp // 设置阴影效果
            ) {
                // 在底部栏中添加选项
                BottomNavigationItem(
                    icon = { Icon(Icons.Sharp.Email, contentDescription = "Message") },
                    label = { Text(text = "chat", color = Color.White, fontSize = 16.sp ) },
                    selected = false,
                    onClick = { currentPage = "chat"}
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Settings") },
                    label = { Text(text = "Contacts", color = Color.White, fontSize = 16.sp) },
                    selected = false,
                    onClick = { currentPage = "Contacts" }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = "Settings") },
                    label = { Text(text = "Discover", color = Color.White, fontSize = 16.sp) },
                    selected = true, // 默认选中
                    onClick = { currentPage = "Discover"}
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text(text = "me", color = Color.White, fontSize = 16.sp) },
                    selected = false,
                    onClick = { currentPage = "me" }
                )
            }
        },
        content = { innerPadding ->
            when (currentPage) {
                "Discover" -> {
                    // 当前页面为Home时，显示Home页面的内容
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(innerPadding)
                            .background(Color.Gray),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        DiscoverItem("Moments", Icons.Default.Notifications)
                        DiscoverItem("Channels", Icons.Default.Notifications)
                        DiscoverItem("Live", Icons.Default.Notifications)
                        DiscoverItem("Scan", Icons.Default.Notifications)
                        DiscoverItem("Listen", Icons.Default.Notifications)
                    }
                }
                else -> {
                    BlankPage()
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(name:String){
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name, color = Color.White, fontSize = 20.sp)
    }
}

@Composable
fun DiscoverItem(name:String,icon: ImageVector){
    Row(modifier = Modifier.padding(24.dp).fillMaxWidth().background(Color.Gray)) {
        Icon(icon, contentDescription = "Settings",Modifier.padding(vertical = 6.dp))
        Text(text = name,color = Color.White, fontSize = 20.sp)
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Settings",modifier = Modifier
            .weight(1f) )
    }
}

@Composable
fun BlankPage() {
    // 空白页面的内容，这里可以留空或者放置一些基本的UI组件
    Box(modifier = Modifier.fillMaxSize()) {
        // 空白页面的内容
    }
}