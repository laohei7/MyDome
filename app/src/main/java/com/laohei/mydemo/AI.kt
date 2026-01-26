package com.laohei.mydemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// Custom Purple Palette
val DeepPurple = Color(0xFF6200EE)
val LightPurple = Color(0xFFF3E5F5)
val BannerGradient = Brush.horizontalGradient(listOf(Color(0xFFBB86FC), Color(0xFF03DAC6)))

@Preview
@Composable
fun DashboardScreen() {
    Scaffold(
        containerColor = Color(0xFFF8F9FA)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item { HeaderWithActions() }

            item { SearchBarSection() }

            item { PromoBanner() }

            item { MenuGridSection() }

            // Extra padding at bottom
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun HeaderWithActions() {
    val convexCurveShape = GenericShape { size, _ ->
        lineTo(size.width, 0f)
        lineTo(size.width, size.height - 60f)
        quadraticBezierTo(
            size.width / 2, size.height,
            0f, size.height - 60f
        )
        close()
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(280.dp)) {
        // Purple Curved Background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(convexCurveShape)
                .background(DeepPurple)
                .padding(horizontal = 24.dp, vertical = 40.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Hello,", color = Color.White.copy(alpha = 0.8f), fontSize = 16.sp)
                    Text(
                        "David Friedman",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                // Avatar with Border
                Surface(
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    color = Color.White,
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        // Floating Quick Actions Card
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QuickActionItem(Icons.Default.Favorite, "Video Call")
                QuickActionItem(Icons.Default.Notifications, "Notification")
                QuickActionItem(Icons.Default.Call, "Voice Call")
            }
        }
    }
}

@Composable
fun QuickActionItem(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(45.dp)
                .background(LightPurple, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = DeepPurple)
        }
        Text(
            label,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun SearchBarSection() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Searching for...", color = Color.Gray) },
        trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .height(56.dp),
        shape = RoundedCornerShape(50.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = DeepPurple.copy(alpha = 0.5f)
        )
    )
}

@Composable
fun PromoBanner() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(120.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(BannerGradient)
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "To Get Unlimited\nUpgrade Your Account",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(60.dp).alpha(0.5f)
            )
        }
    }
}

@Composable
fun MenuGridSection() {
    val menuItems = listOf(
        "Inbox" to Icons.Default.Email, "Maps" to Icons.Default.Email,
        "Chats" to Icons.Default.Add, "Report" to Icons.Default.Add,
        "Calendar" to Icons.Default.DateRange, "Tips" to Icons.Default.Add,
        "Settings" to Icons.Default.Settings, "More" to Icons.Default.Add
    )

    // Grid needs a fixed height or to be inside an item with a specified height in a LazyColumn
    Column(modifier = Modifier.padding(24.dp)) {
        Text(
            "Menu",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Using a simple Column/Row approach for the grid inside LazyColumn to avoid nested scroll issues
        val rows = menuItems.chunked(4)
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { (label, icon) ->
                    MenuGridItem(label, icon)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MenuGridItem(label: String, icon: ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(75.dp)
    ) {
        Surface(
            modifier = Modifier.size(65.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    icon,
                    contentDescription = label,
                    tint = Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Text(
            label,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 8.dp),
            color = Color.DarkGray
        )
    }
}