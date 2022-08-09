package com.example.whowantstobeamillionaire.fragments

import android.app.Dialog
import android.media.MediaPlayer
import android.media.MediaPlayer.create
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.view.GravityCompat
import com.example.whowantstobeamillionaire.R
import com.example.whowantstobeamillionaire.databinding.ActivityPlayerBinding
import com.example.whowantstobeamillionaire.dialogs.AudienceDialog
import com.example.whowantstobeamillionaire.manager.DatabaseManager
import com.example.whowantstobeamillionaire.model.Question



class PlayerFragment: BaseFragment(), View.OnClickListener {
    private var arrIdQues = mutableListOf<IntArray>()
    private lateinit var binding : ActivityPlayerBinding
    private var questions = mutableListOf<Question>()
    private var level : Int = 1
    private var timer:Int = 0
    private val handler: Handler? = null
    private var runnableTimer: Runnable? = null
    private lateinit var databaseManager: DatabaseManager
    private var isPlaying = false
    private var isReady = false
    private var is5050 = true
    private var isAskAudience = true
    private var isCall = true
    private var isChange = true
    private var isStop = false
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var countDownTimer: CountDownTimer

    private lateinit var dialogTimeUp: Dialog
    private lateinit var dialogScore: Dialog
    private lateinit var callDialog: Dialog
    private lateinit var audienceDialog: AudienceDialog
    private lateinit var dialogStop: Dialog
    private lateinit var dialogFalse: Dialog
    private lateinit var linearLayoutAnswer : LinearLayout
    private lateinit var linearLayout1 : LinearLayout
    private lateinit var linearLayout2 : LinearLayout
    private lateinit var linearLayout3 : LinearLayout
    private lateinit var linearLayout4 : LinearLayout

    //
    val ANS_A = intArrayOf(R.raw.ans_a, R.raw.ans_a2)
    val ANS_B = intArrayOf(R.raw.ans_b, R.raw.ans_b2)
    val ANS_C = intArrayOf(R.raw.ans_c, R.raw.ans_c2)
    val ANS_D = intArrayOf(R.raw.ans_d, R.raw.ans_d2)
    val ANS_NOW = intArrayOf(R.raw.ans_now1, R.raw.ans_now2, R.raw.ans_now3)
    val TRUE_A = intArrayOf(R.raw.true_a, R.raw.true_a2)
    val TRUE_B = intArrayOf(R.raw.true_b, R.raw.true_b2, R.raw.true_b3)
    val TRUE_C = intArrayOf(R.raw.true_c, R.raw.true_c2, R.raw.true_c3)
    val TRUE_D = intArrayOf(R.raw.true_d2, R.raw.true_d3)
    val LOSE_A = intArrayOf(R.raw.lose_a, R.raw.lose_a2)
    val LOSE_B = intArrayOf(R.raw.lose_b, R.raw.lose_b2)
    val LOSE_C = intArrayOf(R.raw.lose_c, R.raw.lose_c2)
    val LOSE_D = intArrayOf(R.raw.lose_d, R.raw.lose_d2)

    val QUEST_1 = intArrayOf(R.raw.ques1, R.raw.ques1_b)
    val QUEST_2 = intArrayOf(R.raw.ques2, R.raw.ques2_b)
    val QUEST_3 = intArrayOf(R.raw.ques3, R.raw.ques3_b)
    val QUEST_4 = intArrayOf(R.raw.ques4, R.raw.ques4_b)
    val QUEST_5 = intArrayOf(R.raw.ques5, R.raw.ques5_b)
    val QUEST_6 = intArrayOf(R.raw.ques6)
    val QUEST_7 = intArrayOf(R.raw.ques7, R.raw.ques7_b)
    val QUEST_8 = intArrayOf(R.raw.ques8, R.raw.ques8_b)
    val QUEST_9 = intArrayOf(R.raw.ques9, R.raw.ques9_b)
    val QUEST_10 = intArrayOf(R.raw.ques10)
    val QUEST_11 = intArrayOf(R.raw.ques11)
    val QUEST_12 = intArrayOf(R.raw.ques12)
    val QUEST_13 = intArrayOf(R.raw.ques13)
    val QUEST_14 = intArrayOf(R.raw.ques14)
    val QUEST_15 = intArrayOf(R.raw.ques15)
    val SOUND_5050 = intArrayOf(R.raw.sound5050, R.raw.sound5050_2)



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityPlayerBinding.inflate(inflater, container, false)
        setEvents()
        return binding.root
    }



    private fun playerActivity() {
        databaseManager = DatabaseManager(requireContext())
        questions.addAll(databaseManager.query15Question())
        for (i in 0 until questions.size - 1) {
            for (j in i + 1 until questions.size) {
                if (questions[i].level > questions[j].level) {
                    val question = questions[j]
                    questions[j] = questions[i]
                    questions[i] = question
                }
            }
        }
        Log.d("cau hoi:", questions[0].question)

    }

    private fun setQuestion() {
        val question = questions[level - 1]
        binding.tvCaseA.isEnabled = true
        binding.tvCaseB.isEnabled = true
        binding.tvCaseC.isEnabled = true
        binding.tvCaseD.isEnabled = true
        binding.tvCaseA.setBackgroundResource(R.drawable.player_answer_background_normal)
        binding.tvCaseB.setBackgroundResource(R.drawable.player_answer_background_normal)
        binding.tvCaseC.setBackgroundResource(R.drawable.player_answer_background_normal)
        binding.tvCaseD.setBackgroundResource(R.drawable.player_answer_background_normal)
        binding.tvLevel.text = "Câu: $level"
        binding.tvQuestion.text = question.question
        binding.tvCaseA.text = "A: ${question.caseA}"
        binding.tvCaseB.text = "B: ${question.caseB}"
        binding.tvCaseC.text = "C: ${question.caseC}"
        binding.tvCaseD.text = "D: ${question.caseD}"
        //App.getMusicPlayer().resumeBgMusic()
        timer = 30000
        binding.pgTimer.visibility = View.VISIBLE
        setClickAble(true)
        if (is5050) {
            binding.btn5050.isClickable = true
        }
        if (isAskAudience) {
            binding.btnAudience.isClickable = true
        }
        if (isCall) {
            binding.btnCall.isClickable = true
        }
        if (isChange) {
            binding.btnChange.isClickable = true
        }
    }


    private fun getNewQuestion() {
        level++
        Log.i("level", questions[level-1].level.toString())
        setQuestion()
        checkQuestion()
    }

    private fun setClickAble(b: Boolean) {
        binding.tvCaseA.isClickable = b
        binding.tvCaseB.isClickable = b
        binding.tvCaseC.isClickable = b
        binding.tvCaseD.isClickable = b
        binding.btnStop.isClickable = b
    }

    private fun openCallDialog() {
        callDialog = Dialog(requireContext())
        callDialog.setContentView(R.layout.call_dialog)
        callDialog.show()
        var button1: ImageButton = callDialog.findViewById(R.id.btn_help_01)
        var button2 : ImageButton = callDialog.findViewById(R.id.btn_help_02)
        var button3 : ImageButton = callDialog.findViewById(R.id.btn_help_03)
        var button4 : ImageButton = callDialog.findViewById(R.id.btn_help_04)
        var buttonClose : Button = callDialog.findViewById(R.id.btn_call_close)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        buttonClose.setOnClickListener{
            callDialog.cancel()
            binding.btnCall.setBackgroundResource(R.drawable.player_button_image_help_call_x)
        }
        linearLayoutAnswer = callDialog.findViewById(R.id.ln_answer)
        linearLayout1 = callDialog.findViewById(R.id.ln_call_01)
        linearLayout2 = callDialog.findViewById(R.id.ln_call_02)
        linearLayout3 = callDialog.findViewById(R.id.ln_call_03)
        linearLayout4 = callDialog.findViewById(R.id.ln_call_04)
    }

    private fun answer(int: Int): String {
        return when (int) {
            1 -> {
                "A"
            }
            2 -> {
                "B"
            }
            3 -> {
                "B"
            }
            else -> "D"
        }
    }

    private fun answerFalse(int: Int): String {
        return when (int) {
            1 -> {
                "B"
            }
            2 -> {
                "C"
            }
            3 -> {
                "D"
            }
            else -> "A"
        }
    }

    private fun setEvents() {

        binding.tvCaseA.setOnClickListener(this)
        binding.tvCaseB.setOnClickListener(this)
        binding.tvCaseC.setOnClickListener(this)
        binding.tvCaseD.setOnClickListener(this)
        binding.btnStop.setOnClickListener(this)
        binding.btnAudience.setOnClickListener(this)
        binding.btnCall.setOnClickListener(this)
        binding.btnChange.setOnClickListener(this)
        binding.btn5050.setOnClickListener(this)
        binding.ivPlayer.setOnClickListener(this)
        //binding.btnHide.setOnClickListener(this)
        binding.lnPlay.visibility = View.GONE
        binding.tvMoney.text = "0"
        startGame()
        fillArrIdsRawQues()
    }

    private fun setViewGone() {
        linearLayout1.visibility = View.GONE
        linearLayout2.visibility = View.GONE
        linearLayout3.visibility = View.GONE
        linearLayout4.visibility = View.GONE
    }
    private fun startGame() {
        isReady = true
        mediaPlayer = create(context, R.raw.gofind)
        binding.imgLoading.visibility = View.VISIBLE
        binding.imgLoading.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate_loading))
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
//            questionFragment.setBackgroundLevel(level)
            binding.imgLoading.clearAnimation()
            binding.imgLoading.visibility = View.INVISIBLE
            Handler().postDelayed({playGame()}, 2000)
        }
    }


    override fun onClick(view: View) {
        when(view.id) {
            R.id.tv_case_a -> {
                checkAnswer(view, ANS_A, TRUE_A, 1)
            }
            R.id.tv_case_b -> {
                checkAnswer(view, ANS_B, TRUE_B, 2)
            }
            R.id.tv_case_c -> {
                checkAnswer(view, ANS_C, TRUE_C, 3)
            }
            R.id.tv_case_d -> {
                checkAnswer(view, ANS_D, TRUE_D, 4)
            }
            R.id.btn_5050 -> {
                setClickAble(false)
                is5050 = false
                mediaPlayer.release()
                binding.btn5050.setBackgroundResource(R.drawable.player_button_image_help_5050_active)
                mediaPlayer = create(context, SOUND_5050.random())
                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.release()
                    mediaPlayer = MediaPlayer.create(context, R.raw.bgmusic)
                    mediaPlayer.start()
                    setClickAble(true)
                    var count  = 0
                    val RANDOM = intArrayOf(1,2,3,4)
                    var b = 0
                    while (count<2) {
                        var random = RANDOM.random()
                        Log.d("Random", random.toString())
                        Log.d("Truecase", getTrueAnswer().toString())
                        if (random != getTrueAnswer() && random != b) {
                            b = random
                            when(random) {
                                1 -> {
                                    binding.tvCaseA.text = ""
                                    binding.tvCaseA.isClickable = false
                                }
                                2 -> {
                                    binding.tvCaseB.text = ""
                                    binding.tvCaseB.isClickable = false
                                }
                                3 -> {
                                    binding.tvCaseC.text = ""
                                    binding.tvCaseC.isClickable = false
                                }
                                4 -> {
                                    binding.tvCaseD.text = ""
                                    binding.tvCaseD.isClickable = false
                                }
                            }
                            count++
                        }
                    }
                    binding.btn5050.setBackgroundResource(R.drawable.player_button_image_help_5050_x)
                    binding.btn5050.isClickable = false
                }
            }

            R.id.btn_audience -> {
                isAskAudience = false
                binding.btnAudience.setBackgroundResource(R.drawable.player_button_image_help_audience_active)
                setClickAble(false)
                var cs = ""
                if (!binding.tvCaseA.isEnabled) {
                        cs += 0
                }
                if (!binding.tvCaseA.isEnabled) {
                    cs += 1
                }
                if (!binding.tvCaseA.isEnabled) {
                    cs += 2
                }
                if (!binding.tvCaseA.isEnabled) {
                    cs += 3
                }

                audienceDialog = AudienceDialog(context)
                audienceDialog.prepareVote(questions[level - 1].trueCase, cs)
                audienceDialog.show()
                mediaPlayer.release()
                mediaPlayer = create(context, R.raw.khan_gia)
                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.release()
                    audienceDialog.voteAnswer()
                    mediaPlayer = create(context, R.raw.hoi_y_kien_chuyen_gia_01b)
                    audienceDialog.setOnDismissListener {
                        mediaPlayer.release()
                        setClickAble(true)
                        binding.btnAudience.setBackgroundResource(R.drawable.player_button_image_help_audience_x)
                        mediaPlayer = create(context, R.raw.bgmusic)
                        mediaPlayer.start()
                    }
                }
            }

            R.id.btn_stop -> {
                stopGame()
            }

            R.id.btn_change -> {
                isChange = false
                binding.btnChange.isClickable = false
                binding.btnChange.setBackgroundResource(R.drawable.player_button_image_help_change_question_active)
                countDownTimer.cancel()
                questions.clear()
                Handler().postDelayed({
                    playerActivity()
                    setQuestion()
                    checkQuestion()
                    binding.btnChange.setBackgroundResource(R.drawable.player_button_image_help_change_question_x)
                },1000)


            }

            R.id.btn_call -> {
                isCall = false
                binding.btnCall.setBackgroundResource(R.drawable.player_button_image_help_call_active)
                openCallDialog()
                binding.btnCall.isClickable = false
            }
            R.id.btn_help_01 -> {
                Log.d("Log", "click")
                val RAND = intArrayOf(1,1,1,1,1,1,0,0,0,0)
                val rand = RAND.random()
                setViewGone()
                linearLayoutAnswer.visibility = View.VISIBLE
                val textView : TextView = callDialog.findViewById(R.id.tv_answer)
                if (rand == 1) {
                    textView.text = "Theo tôi đáp án đúng là "+ answer(getTrueAnswer())
                } else {
                    textView.text = "Theo tôi đáp án đúng là "+ answerFalse(getTrueAnswer())
                }
            }
            R.id.btn_help_02 -> {
                val RAND = intArrayOf(1,1,1,1,1,1,1,1,0,0)
                val rand = RAND.random()
                setViewGone()
                linearLayoutAnswer.visibility = View.VISIBLE
                val textView : TextView = callDialog.findViewById(R.id.tv_answer)
                if (rand == 1) {
                    textView.text = "Theo tôi đáp án đúng là "+ answer(getTrueAnswer())
                } else {
                    textView.text = "Theo tôi đáp án đúng là "+ answerFalse(getTrueAnswer())
                }
            }
            R.id.btn_help_03 -> {
                val RAND = intArrayOf(1,1,1,1,1,1,1,0,0,0)
                val rand = RAND.random()
                setViewGone()
                linearLayoutAnswer.visibility = View.VISIBLE
                val textView : TextView = callDialog.findViewById(R.id.tv_answer)
                if (rand == 1) {
                    textView.text = "Theo tôi đáp án đúng là "+ answer(getTrueAnswer())
                } else {
                    textView.text = "Theo tôi đáp án đúng là "+ answerFalse(getTrueAnswer())
                }
            }

            R.id.btn_help_04 -> {
                val RAND = intArrayOf(1,1,1,1,1,1,1,1,0,0)
                val rand = RAND.random()
                setViewGone()
                linearLayoutAnswer.visibility = View.VISIBLE
                val textView : TextView = callDialog.findViewById(R.id.tv_answer)
                if (rand == 1) {
                    textView.text = "Theo tôi đáp án đúng là "+ answer(getTrueAnswer())
                } else {
                    textView.text = "Theo tôi đáp án đúng là "+ answerFalse(getTrueAnswer())
                }
            }
        }
    }


    private fun fillArrIdsRawQues() {
        arrIdQues.add(QUEST_1)
        arrIdQues.add(QUEST_2)
        arrIdQues.add(QUEST_3)
        arrIdQues.add(QUEST_4)
        arrIdQues.add(QUEST_5)
        arrIdQues.add(QUEST_6)
        arrIdQues.add(QUEST_7)
        arrIdQues.add(QUEST_8)
        arrIdQues.add(QUEST_9)
        arrIdQues.add(QUEST_10)
        arrIdQues.add(QUEST_11)
        arrIdQues.add(QUEST_12)
        arrIdQues.add(QUEST_13)
        arrIdQues.add(QUEST_14)
        arrIdQues.add(QUEST_15)
    }

    private fun getIdsRaw(lv: Int): IntArray {
        return arrIdQues[lv - 1]
    }

    private fun checkAnswer(view: View,ans : IntArray, trueAns: IntArray, id: Int ) {
        isPlaying = false
        countDownTimer.cancel()
        view.setBackgroundResource(R.drawable.bg_choose2)
        mediaPlayer.release()
        Log.i("Meme", getTrueAnswer().toString())
        Log.i("Meme", id.toString())
        binding.btnAudience.isClickable = false
        binding.btn5050.isClickable = false
        binding.btnStop.isClickable = false
        binding.btnChange.isClickable = false
        binding.btnCall.isClickable = false
        setClickAble(false)
        mediaPlayer = create(context, ans.random())
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            if (mediaPlayer != null) {
                mediaPlayer.release()
            }
            mediaPlayer = create(context, ANS_NOW.random())
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                if (getTrueAnswer() == id) {
                answerTrue(view, trueAns)
                } else {
                answerFalse(view, getIdLoseCase(getTrueAnswer()))
                }
            }
        }

    }

    private fun getIdLoseCase(lose : Int): IntArray {
        return when (lose) {
            1 -> {
                LOSE_A;
            }
            2 -> {
                LOSE_B;
            }
            3 -> {
                LOSE_C;
            }
            else -> {
                LOSE_D;
            }
        }

    }

    private fun answerTrue(view: View, trueAns: IntArray) {
        view.setBackgroundResource(R.drawable.bg_true2)
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_loop))
        mediaPlayer.release()
        mediaPlayer = create(context, trueAns.random())
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            if (level == 5) {
                val mediaPlayer1 = MediaPlayer.create(context, R.raw.chuc_mung_vuot_moc_01_0)
                mediaPlayer1.start()
                mediaPlayer1.setOnCompletionListener {
                    getNewQuestion()
                }
            } else {
                if (level ==10) {
                    val mediaPlayer1 = MediaPlayer.create(context, R.raw.chuc_mung_vuot_moc_02_0)
                    mediaPlayer1.start()
                    mediaPlayer1.setOnCompletionListener {
                        getNewQuestion()
                    }
                } else {
                    Handler().postDelayed({ getNewQuestion()}, 1000)
                }
            }
            setScore()
        }
    }



    private fun answerFalse(view: View, falseAns: IntArray) {
        view.setBackgroundResource(R.drawable.bg_faile2)
        when {
            getTrueAnswer() == 1 -> {
                binding.tvCaseA.setBackgroundResource(R.drawable.bg_true2)
                binding.tvCaseA.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_loop))
            }
            getTrueAnswer() == 2 -> {
                binding.tvCaseB.setBackgroundResource(R.drawable.bg_true2)
                binding.tvCaseB.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_loop))
            }
            getTrueAnswer() == 3 -> {
                binding.tvCaseC.setBackgroundResource(R.drawable.bg_true2)
                binding.tvCaseC.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_loop))
            }
            getTrueAnswer() == 4 -> {
                binding.tvCaseD.setBackgroundResource(R.drawable.bg_true2)
                binding.tvCaseD.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_loop))
            }
        }
        mediaPlayer.release()
        mediaPlayer = create(context,falseAns.random())
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {

            if (level>5) {
                saveScore(isStop)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, FirstFragment(), FirstFragment::class.java.name)
                    ?.commit()
            } else {
                dialogFalse = Dialog(requireContext())
                dialogFalse.setContentView(R.layout.dialog_demo)
                val textView = dialogFalse.findViewById<TextView>(R.id.dialog_info)
                textView.text = "Do you want to play game again?"
                dialogFalse.show()
                var buttonOk: Button = dialogFalse.findViewById(R.id.dialog_ok)
                var buttonCancel : Button = dialogFalse.findViewById(R.id.dialog_cancel)
                buttonOk.setOnClickListener{
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.fragment_container, PlayerFragment(), PlayerFragment::class.java.name)
                        ?.commit()
                    dialogFalse.cancel()
                }
                buttonCancel.setOnClickListener{
                    onFinish1()
                }
            }
        }

}

    private fun getTrueAnswer(): Int {
        return questions[level - 1].trueCase
    }


    private fun playGame() {
        binding.lnPlay.visibility = View.VISIBLE
        playerActivity()
        //musicManager.playBgMusic(R.raw.background_music)
        setQuestion()
        checkQuestion()
        handler?.post(runnableTimer!!)
    }
    private fun countDownTimer() {
        mediaPlayer.release()
        mediaPlayer = create(context, R.raw.background_music)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        countDownTimer = object : CountDownTimer(timer.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text =  (millisUntilFinished / 1000).toString()
            }


            override fun onFinish() {
                mediaPlayer.release()
                setClickAble(false)
                if (level>5) {
                    saveScore(isStop)
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.fragment_container, FirstFragment(), FirstFragment::class.java.name)
                        ?.commit()
                } else {
                    dialogTimeUp = Dialog(requireContext())
                    dialogTimeUp.setContentView(R.layout.dialog_time_up)
                    dialogTimeUp.show()
                    var buttonOk: Button = dialogTimeUp.findViewById(R.id.btn_ok)
                    var buttonCancel : Button = dialogTimeUp.findViewById(R.id.btn_cancel)
                    buttonOk.setOnClickListener{
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, PlayerFragment(), PlayerFragment::class.java.name)
                            ?.commit()
                        dialogTimeUp.cancel()
                    }
                    buttonCancel.setOnClickListener{
                        onFinish1()
                    }
                }
            }
        }
        countDownTimer.start()
    }

    private fun checkQuestion() {
        mediaPlayer.release()
        mediaPlayer = create(context, getIdsRaw(level).random() )
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
                countDownTimer()
            }
    }

    private fun setScore() {
        when(level) {
            1->{binding.tvMoney.text = "200,000"}
            2->{binding.tvMoney.text = "400,000"}
            3->{binding.tvMoney.text = "600,000"}
            4->{binding.tvMoney.text = "1,000,000"}
            5->{binding.tvMoney.text = "2,000,000"}
            6->{binding.tvMoney.text = "3,000,000"}
            7->{binding.tvMoney.text = "6,000,000"}
            8->{binding.tvMoney.text = "10,000,000"}
            9->{binding.tvMoney.text = "14,000,000"}
            10->{binding.tvMoney.text = "22,000,000"}
            11->{binding.tvMoney.text = "30,000,000"}
            12->{binding.tvMoney.text = "40,000,000"}
            13->{binding.tvMoney.text = "60,000,000"}
            14->{binding.tvMoney.text = "85,000,000"}
            15->{binding.tvMoney.text = "150,000,000"}
        }
    }

    private fun stopGame() {
        isStop = true
        dialogStop = Dialog(requireContext())
        dialogStop.setContentView(R.layout.dialog_demo)
        val textView = dialogStop.findViewById<TextView>(R.id.dialog_info)
        textView.text = "Do you want to stop game?"
        dialogStop.show()
        var buttonOk: Button = dialogStop.findViewById(R.id.dialog_ok)
        var buttonCancel : Button = dialogStop.findViewById(R.id.dialog_cancel)
        buttonOk.setOnClickListener{
            countDownTimer.cancel()
            mediaPlayer.release()
            if (level>5) {
                saveScore(isStop)
                dialogStop.cancel()
                onFinish1()
            } else {
                onFinish1()
            }
        }
        buttonCancel.setOnClickListener{
            dialogStop.cancel()
        }
    }

    private fun saveScore(stop:Boolean) {
        dialogScore = Dialog(requireContext())
        dialogScore.setContentView(R.layout.score_dialog)
        dialogScore.show()
        var textView: TextView = dialogScore.findViewById(R.id.tv_score)
        textView.text = binding.tvMoney.text
        var editText:EditText = dialogScore.findViewById(R.id.edt_name)
        var buttonOk: Button = dialogScore.findViewById(R.id.btn_ok)

        buttonOk.setOnClickListener{
            val string = editText.text
            val string1 = textView.text
            databaseManager.insertHighScore(string.toString(), string1.toString(), level)
            dialogScore.cancel()
            if (stop) {
                onFinish1()
            }
        }
    }

    override fun onFinish1() {
        mediaPlayer.release()
        super.onFinish1()
    }

}

