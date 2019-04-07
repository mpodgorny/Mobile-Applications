package com.example.hangman

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.view.View

class MainActivity : AppCompatActivity() {

    var usedLetters : MutableList<String> = mutableListOf<String>()
    var visible = "" //what is displayed on the screen
    var currentWord = "" //what we actually looking for
    var mistakes = 1
    val hangGuys= arrayOf(R.drawable.h1,R.drawable.h2,R.drawable.h3,R.drawable.h4,R.drawable.h5,R.drawable.h6,R.drawable.h7,R.drawable.h8, R.drawable.h9, R.drawable.h10, R.drawable.h11)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newGame()
    }

    fun newGame() {
        button.setText("SUBMIT")
        button.setOnClickListener {
            onSubmit(it)
        }
        imageView.setImageResource(hangGuys[0])
        usedLetters.clear()
        mistakes=0
        val words: Array<String> = resources.getStringArray(R.array.words)
        val r = Random()
        currentWord = words[r.nextInt(words.lastIndex)]
        visible = "?".repeat(currentWord.length)
        findViewById<TextView>(R.id.guessingText).text = visible
    }

    fun onSubmit(View: View){

        var content = editText.text.toString().toLowerCase()
        if(content.length !=1) {
            editText.error = "Put only single characters!"
        } else if(usedLetters.contains(content)){
            editText.error = "Character already tried!"
        } else {
            editText.error = null
            usedLetters.add(content)
            revealLetters(content)
        }

    }

    fun revealLetters(content: String) {
        if(currentWord.contains(content)){
            while(currentWord.contains(content)){
                var  temp = visible.toCharArray()

                temp[currentWord.indexOf(content)] = content.single()
                visible=temp.joinToString("")
                var cW = currentWord.toCharArray()

                cW[currentWord.indexOf(content)]='?'

                currentWord=cW.joinToString("")
            }
            if(!visible.contains('?')){
                findViewById<TextView>(R.id.guessingText).text = " You won! your word was: $visible"
                button.setText("NEW GAME")
                button.setOnClickListener {
                    newGame()
                }
            }else {
                findViewById<TextView>(R.id.guessingText).text = visible
            }
        } else {
            mistakes++
            if(mistakes>9){
                imageView.setImageResource(hangGuys[10])
                findViewById<TextView>(R.id.guessingText).text = "You lose!"
                button.setText("NEW GAME")
                button.setOnClickListener {
                    newGame()
                }

            }else {
                imageView.setImageResource(hangGuys[mistakes])
            }
        }
    }
}
