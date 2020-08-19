package com.ieugene.kotlinlab

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class KotlinActivity : AppCompatActivity() {

    //    private lateinit var textView: TextView
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        val button = findViewById<Button>(R.id.button)
//        textView = findViewById(R.id.text_view)
//        textView.text = getString(R.string.dice_rolled)
        button.setOnClickListener { showToast() }
        findViewById<Button>(R.id.count_up).setOnClickListener {
//            val digits = textView.text
//            textView.text = digits.toString().toInt().inc().toString()
        }

        imageView = findViewById(R.id.dice_image)
    }

    private fun showToast() {
        val randomInt = (1..6).random()
        val drawableResource = when (randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        imageView.setImageResource(drawableResource)
//        textView.text = randomInt.toString()
        Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()
    }
}