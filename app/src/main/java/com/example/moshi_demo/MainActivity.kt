package com.example.moshi_demo

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var output: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        output = findViewById(R.id.textView)

        demoMoshi()
    }

    private fun demoMoshi() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(LocalDateAdapter())
            .build()

        val adapter = moshi.adapter(User::class.java)

        val json = """
            {
              "id": 1,
              "name": "Janez Novak",
              "email": "janez@example.com",
              "is_active": true,
              "age": 22,
              "registered": "2024-12-01"
            }
        """.trimIndent()

        try {
            // JSON -> Objekt
            val user = adapter.fromJson(json)

            // Objekt -> JSON
            val jsonBack = adapter.toJson(user)

            output.text = """
                JSON -> Object:
                $user

                Object -> JSON:
                $jsonBack
            """.trimIndent()

        } catch (e: Exception) {
            output.text = "Napaka pri obdelavi JSON-a:\n${e.message}"
            Log.e("MOSHI", "Napaka", e)
        }

        demoListParsing(moshi)
        demoException(moshi)
    }

    private fun demoListParsing(moshi: Moshi) {
        val jsonList = """
            [
              { "id": 1, "name": "Ana", "email": "ana@mail.com", "is_active": true, "age": 21, "registered": "2024-01-01" },
              { "id": 2, "name": "Miha", "email": "miha@mail.com", "is_active": false, "age": 25, "registered": "2023-10-10" }
            ]
        """

        val type = Types.newParameterizedType(List::class.java, User::class.java)
        val adapter = moshi.adapter<List<User>>(type)

        val users = adapter.fromJson(jsonList)

        Log.d("MOSHI", "Seznam uporabnikov: $users")
    }

    private fun demoException(moshi: Moshi) {
        val badJson = """{ "name": "Napaka" }"""

        val adapter = moshi.adapter(User::class.java)

        try {
            adapter.fromJson(badJson)
        } catch (e: Exception) {
            Log.e("MOSHI", "Prišlo je do izjeme!", e)
        }
    }
}
