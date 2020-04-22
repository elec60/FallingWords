package com.sma6871.fallingwords.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sma6871.fallingwords.R
import com.sma6871.fallingwords.presentation.viewmodel.GameViewModel
import com.sma6871.fallingwords.utils.getColorX
import kotlinx.android.synthetic.main.fragment_result.*
import kotlinx.android.synthetic.main.fragment_welcome.btnStart
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ResultDialogFragment : DialogFragment() {

    private val viewModel: GameViewModel by sharedViewModel()


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setResult()

        setListeners()
    }

    private fun setListeners() {
        btnStart.setOnClickListener {
            viewModel.nextQuestion()
            dismiss()
        }
    }

    private fun setResult() {
        if (viewModel.question?.isAnsweredCorrectly == true) {
            txtTitle.text = getString(R.string.correct_answer)
            txtTitle.setTextColor(context?.getColorX(R.color.correct) ?: return)
            imgResult.setImageResource(R.drawable.ic_correct)
        } else {
            txtTitle.text = getString(R.string.wrong_answer)
            txtTitle.setTextColor(context?.getColorX(R.color.wrong) ?: return)
            imgResult.setImageResource(R.drawable.ic_wrong)
        }
    }
}