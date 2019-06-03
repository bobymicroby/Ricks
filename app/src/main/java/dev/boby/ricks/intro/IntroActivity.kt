package dev.boby.ricks.intro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColorStateList
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.ricks.R
import dev.boby.ricks.util.View
import dev.boby.ricks.util.pure.Update
import dev.boby.ricks.util.setChar
import kotlinx.android.synthetic.main.activity_intro.*


data class Model(
    val initial0: Char?,
    val initial1: Char?,
    val initial2: Char?,
    val key0: Char,
    val key1: Char,
    val key2: Char,
    val canPlay: Boolean
)


class IntroActivity : AppCompatActivity(), View<Model> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        val initialState = Model(null, null, null, 'R', 'I', 'K', false)
        view(initialState)
    }

    override fun view(model: Model) {

        if (model.canPlay) {
            play_button.strokeColor = getColorStateList(this, R.color.black)
        } else {
            play_button.strokeColor = getColorStateList(this, R.color.dark_gray)
        }
        play_button.isEnabled = model.canPlay

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
}
