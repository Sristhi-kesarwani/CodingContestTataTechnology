package com.iav.contestdataprovider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.iav.contestdataprovider.databinding.ActivityMainBinding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val randomStrings = mutableListOf<RandomString>()
    private val adapter = RandomStringAdapter(randomStrings)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.generateButton.setOnClickListener {
            val length = binding.inputLength.text.toString().toIntOrNull() ?: 0
            fetchRandomString(length)
        }

        binding.clearButton.setOnClickListener {
            randomStrings.clear()
            adapter.notifyDataSetChanged()
        }
    }

    private fun fetchRandomString(maxLength: Int) {
        val randomString = (1..maxLength)
            .map { ('a'..'z').random() }
            .joinToString("")
        val jsonString = """
            {
                "randomText": {
                    "value": "$randomString",
                    "length": $maxLength,
                    "created": "${getCurrentTime()}"
                }
            }
        """

        val jsonObject = JSONObject(jsonString)
        val randomText = jsonObject.getJSONObject("randomText").getString("value")
        val length = jsonObject.getJSONObject("randomText").getInt("length")
        val created = jsonObject.getJSONObject("randomText").getString("created")

        randomStrings.add(RandomString(randomText, length, created))
        adapter.notifyDataSetChanged()
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
