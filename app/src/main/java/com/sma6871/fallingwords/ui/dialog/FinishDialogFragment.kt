package com.sma6871.fallingwords.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sma6871.fallingwords.R
import com.sma6871.fallingwords.presentation.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.fragment_finish.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FinishDialogFragment : DialogFragment() {

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
        return inflater.inflate(R.layout.fragment_finish, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setResult()

    }


    private fun setResult() {
        txtScore.text = getString(R.string.your_score_d_d).format(
            viewModel.getScore().value?.currentScore,
            viewModel.questionCount
        )
    }
}