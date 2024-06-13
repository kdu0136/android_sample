package com.example.annotationsguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.annotationsguide.ui.theme.AnnotationsGuideTheme
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : ComponentActivity() {
    private val api by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor())
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(
                            HttpLoggingInterceptor.Level.BODY
                        )
                    )
                    .build()
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MyApi::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            api.getPost()
            api.getUser()
        }

        val user = User(
            name = "name",
            birthDate = "2000-01-01"
        )
    }
}

data class User(
    val name: String,
    @AllowedRegex("\\d{4}-\\d{2}-\\d{2}") val birthDate: String
) {
    init {
        val fields = User::class.java.declaredFields
        fields.forEach { field ->
            field.annotations.forEach { annotation ->
                if (field.isAnnotationPresent(AllowedRegex::class.java)) {
                    val regex = field.getAnnotation(AllowedRegex::class.java)?.regex
                    if (regex?.toRegex()?.matches(birthDate) == false) {
                        throw IllegalArgumentException("Invalid birth date: $birthDate")
                    }
                }
            }
        }
    }
}

@Target(AnnotationTarget.FIELD)
annotation class AllowedRegex(val regex: String)
