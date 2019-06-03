package dev.boby.ricks.intro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColorStateList
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.ricks.R
import dev.boby.elmo.Sandbox
import dev.boby.ricks.util.View
import dev.boby.ricks.util.lightStatusBar
import dev.boby.ricks.util.pure.Update
import dev.boby.ricks.util.setChar
import kotlinx.android.synthetic.main.activity_intro.*
import kotlin.random.Random


data class Model(
    val initial0: Char?,
    val initial1: Char?,
    val initial2: Char?,
    val key0: Char,
    val key1: Char,
    val key2: Char,
    val canPlay: Boolean
)

sealed class Msg {
    data class TapOnKey(val char: Char) : Msg()
    data class Refresh(val randomSeed:Long) : Msg()
    object Backspace : Msg()
}

class Update : Update<Model, Msg> {

    private val charPool: CharRange = 'A'..'Z'

    override fun update(msg: Msg, model: Model): Model {
        return when (msg) {
            is Msg.TapOnKey -> {
                when {
                    model.initial0 == null -> model.copy(initial0 = msg.char)
                    model.initial1 == null -> model.copy(initial1 = msg.char)
                    model.initial2 == null -> model.copy(initial2 = msg.char, canPlay = true)
                    else -> model
                }
            }
            is Msg.Refresh -> {
                val random = Random(msg.randomSeed)
                model.copy(key0 = charPool.random(random), key1 = charPool.random(random), key2 = charPool.random(random))

            }
            Msg.Backspace -> {
                when {
                    model.initial2 != null -> model.copy(initial2 = null, canPlay = false)
                    model.initial1 != null -> model.copy(initial1 = null)
                    model.initial0 != null -> model.copy(initial0 = null)
                    else -> model
                }
            }
        }
    }
}


class IntroActivity : AppCompatActivity(), View<Model> {

    private lateinit var sandbox: Sandbox<Msg>

    override fun onCreate(savedInstanceState: Bundle?) {
        lightStatusBar()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val initialState =  Model(null, null, null, 'R', 'I', 'K', false)

        sandbox = Sandbox.create(initialState, Update(), this)

        refresh.setOnClickListener {
            sandbox.accept(Msg.Refresh(Random.nextLong()))
        }
        backspace.setOnClickListener {
            sandbox.accept(Msg.Backspace)
        }
        key_0.setOnClickListener {
            sandbox.accept(Msg.TapOnKey(key_0.text[0]))
        }
        key_1.setOnClickListener {
            sandbox.accept(Msg.TapOnKey(key_1.text[0]))
        }
        key_2.setOnClickListener {
            sandbox.accept(Msg.TapOnKey(key_2.text[0]))
        }

    }


    override fun view(model: Model) {

        play_button.isEnabled = model.canPlay
        if (model.canPlay) {
            play_button.strokeColor = getColorStateList(this, R.color.black)
        } else {
            play_button.strokeColor = getColorStateList(this, R.color.dark_gray)
        }
        key_0.isInvisible = model.canPlay
        key_1.isInvisible = model.canPlay
        key_2.isInvisible = model.canPlay
        refresh.isInvisible = model.canPlay

        key_0.setChar(model.key0)
        key_1.setChar(model.key1)
        key_2.setChar(model.key2)

        pickle_0.isVisible = model.initial0 == null
        pickle_1.isVisible = model.initial1 == null
        pickle_2.isVisible = model.initial2 == null

        initial_0.setChar(model.initial0)
        initial_1.setChar(model.initial1)
        initial_2.setChar(model.initial2)

    }



    override fun onDestroy() {
        super.onDestroy()
        sandbox.dispose()
    }
}
