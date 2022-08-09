package com.example.whowantstobeamillionaire.dialogs


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.example.whowantstobeamillionaire.R
import java.util.*
import kotlin.math.roundToInt


class AudienceDialog(context: Context?) : Dialog(context!!) {
    private val present: Array<TextView?>
    private val barVote: Array<TextView?>
    private var indexTrue = 0
    private var index1 = 0
    private var index2 = 0
    private var index3 = 0
    private val values = mutableListOf<Int>()
    private val random: Random
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var i = 0
    private val max: Int
    private var pst: String? = null
    fun voteAnswer() {
        val layoutParams = barVote[indexTrue]!!.layoutParams
        val layoutParams1 = barVote[index1]!!.layoutParams
        val layoutParams2 = barVote[index2]!!.layoutParams
        val layoutParams3 = barVote[index3]!!.layoutParams
        handler = Handler()
        runnable = Runnable {
            i += 2
            layoutParams.height = i

            pst = (i.toFloat() / max * 100).roundToInt().toString() +"%"
            barVote[indexTrue]!!.layoutParams = layoutParams
            present[indexTrue]!!.text = pst
            if (i <= values[1]) {
                layoutParams1.height = i
                barVote[index1]!!.layoutParams = layoutParams1
                present[index1]!!.text = pst
            }
            if (i <= values[2]) {
                layoutParams2.height = i
                barVote[index2]!!.layoutParams = layoutParams2
                present[index2]!!.text = pst
            }
            if (i <= values[3]) {
                layoutParams3.height = i
                barVote[index3]!!.layoutParams = layoutParams3
                present[index3]!!.text = pst
            }
            if (i < values[0]) runnable.let { handler.postDelayed(it, 1) } else {
                val pst1: Int
                val pst2: Int
                val pst3: Int
                val pst4: Int
                pst1 = (values[0].toFloat() / max * 100).toInt()
                pst2 = (values[1].toFloat() / max * 100).toInt()
                pst3 = (values[2].toFloat() / max * 100).toInt()
                pst4 = 100 - (pst3 + pst1 + pst2)
                present[indexTrue]!!.text = "$pst1%"
                present[index1]!!.text = "$pst2%"
                present[index2]!!.text = "$pst3%"
                present[index3]!!.text = "$pst4%"
                findViewById<View>(R.id.btn_close).visibility = View.VISIBLE
            }
        }
        handler.post(runnable)
    }

    fun prepareVote(trueCase: Int, cs: String) {
        indexTrue = trueCase - 1
        if (cs !== "") {
            values.add(Math.round(random.nextInt(max / 2) as Float + max / 2 + 1))
            values.add(0)
            values.add(0)
            values.add(max - values[0])
            index1 = cs[0].toString().toInt()
            index2 = cs[1].toString().toInt()
            for (i in 0..3) {
                if (i != index1 && i != index2 && i != indexTrue) {
                    index3 = i
                    return
                }
            }
        } else {
            for (i in 0..3) {
                if (i != indexTrue) {
                    index1 = i
                    for (j in i + 1..3) {
                        if (j != indexTrue) {
                            index2 = j
                            for (k in j + 1..3) {
                                if (k != indexTrue) {
                                    index3 = k
                                    values.add((random.nextInt(max * 3 / 4)))
                                    values.add(random.nextInt(max - values[0]))
                                    values.add(random.nextInt(max - values[0] - values[1]))
                                    values.add(max - (values[0] + values[1] + values[2]))
                                    Collections.sort(values)
                                    Collections.reverse(values)
                                    return
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.audience_layout)
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        max = findViewById<View>(R.id.rl_1).layoutParams.height * 4 / 7
        random = Random()
        present = arrayOfNulls(4)
        present[0] = findViewById<View>(R.id.tv_pesent_1) as TextView
        present[1] = findViewById<View>(R.id.tv_pesent_2) as TextView
        present[2] = findViewById<View>(R.id.tv_pesent_3) as TextView
        present[3] = findViewById<View>(R.id.tv_pesent_4) as TextView
        barVote = arrayOfNulls(4)
        barVote[0] = findViewById<View>(R.id.bar_1) as TextView
        barVote[1] = findViewById<View>(R.id.bar_2) as TextView
        barVote[2] = findViewById<View>(R.id.bar_3) as TextView
        barVote[3] = findViewById<View>(R.id.bar_4) as TextView
        findViewById<View>(R.id.btn_close).setOnClickListener { dismiss() }
        findViewById<View>(R.id.btn_close).visibility = View.GONE
    }
}