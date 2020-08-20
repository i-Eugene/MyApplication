package com.ieugene.kotlinlab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ieugene.kotlinlab.bean.MyName
import com.ieugene.kotlinlab.databinding.ActivityKotlinBinding

class KotlinActivity : AppCompatActivity() {

    private val myName = MyName("Aleks Haecky")

    private lateinit var binding: ActivityKotlinBinding

    //    private lateinit var textView: TextView
//    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_kotlin)

        binding.myName = this.myName

//        val button = findViewById<Button>(R.id.button)
//        textView = findViewById(R.id.text_view)
//        textView.text = getString(R.string.dice_rolled)
        binding.button.setOnClickListener { showToast() }
        binding.countUp.setOnClickListener {
//            val digits = textView.text
//            textView.text = digits.toString().toInt().inc().toString()

            startActivity(Intent(this, KotlinDrawerActivity::class.java))
        }

//        imageView = findViewById(R.id.dice_image)
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
        binding.diceImage.setImageResource(drawableResource)
//        textView.text = randomInt.toString()
//        binding.textView.text = randomInt.toString()
        binding.apply {
            myName?.name = randomInt.toString()
            invalidateAll()
        }

        Toast.makeText(this, binding.myName?.name, Toast.LENGTH_SHORT).show()
    }
}