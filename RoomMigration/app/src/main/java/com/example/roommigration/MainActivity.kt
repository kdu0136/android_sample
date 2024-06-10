package com.example.roommigration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.roommigration.ui.theme.RoomMigrationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "users.db",
        ).addMigrations(UserDatabase.migration3To4).build()

        lifecycleScope.launch {
            db.dao.getSchools().forEach(::println)
//            db.dao.getUsers().forEach(::println)
        }

        (1..10).forEach {
            lifecycleScope.launch {
//                db.dao.insertSchool(
//                    School(
//                        name = "test$it",
//                    )
//                )
//                db.dao.insertUser(
//                    User(
//                        email = "test@test$it.com",
//                        userName = "test$it",
//                    )
//                )
            }
        }
    }
}
