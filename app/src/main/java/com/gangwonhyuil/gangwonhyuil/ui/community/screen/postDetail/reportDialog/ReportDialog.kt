package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail.reportDialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.RadioGroup
import com.gangwonhyuil.gangwonhyuil.databinding.DialogRepostPostBinding
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail.OnReportDialogClickListener
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail.PostDetailItem
import timber.log.Timber

interface ReportableItem

class ReportDialog(
    context: Context,
    private val reportableItem: ReportableItem,
    private val onClickListener: OnReportDialogClickListener,
) : Dialog(context) {
    private lateinit var binding: DialogRepostPostBinding
    private var reason = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogRepostPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCanceledOnTouchOutside(true)
        setCancelable(true)
        initWindow(context)

        initView()
    }

    private fun initView() {
        initTextViews()
        initRadioGroup()
        initButtons()
    }

    private fun initTextViews() {
        with(binding) {
            when (reportableItem) {
                is PostDetailItem.PostContent -> {
                    tvContentLabel.text = "게시글"
                    tvContent.text = reportableItem.content
                    tvWriter.text = reportableItem.writerName
                }

                is PostDetailItem.CommentItem -> {
                    tvContentLabel.text = "댓글"
                    tvContent.text = reportableItem.content
                    tvWriter.text = reportableItem.writerName
                }
            }
        }
    }

    private fun initRadioGroup() {
        val group1 = binding.radioGroup1
        val group2 = binding.radioGroup2

        with(group1) {
            clearCheck()
            setOnCheckedChangeListener(createRadioGroupListener(group2))
        }
        with(group2) {
            clearCheck()
            setOnCheckedChangeListener(createRadioGroupListener(group1))
        }
    }

    private fun createRadioGroupListener(theOtherGroup: RadioGroup): (RadioGroup, Int) -> Unit =
        { _, id ->
            findViewById<RadioButton>(id)?.let { radioButton ->
                if (radioButton.isChecked) {
                    theOtherGroup.clearCheck()
                    reason = radioButton.text.toString()
                    Timber.d("reason: $reason")
                }
            }
        }

    private fun initButtons() {
        with(binding) {
            btnDismiss.setOnClickListener {
                dismiss()
            }
            btnConfirm.setOnClickListener {
                if (reason.isEmpty()) {
                    binding.tvReasonMessage.setTextColor(android.graphics.Color.RED)
                } else {
                    onClickListener.onReportClick(reportableItem, reason)
                    dismiss()
                }
            }
        }
    }

    private fun Dialog.initWindow(
        context: Context,
        width: Float = 0.9f,
        height: Float = 0.9f,
    ) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val xy: Pair<Int, Int> =
            if (Build.VERSION.SDK_INT < 30) {
                val display = windowManager.defaultDisplay
                val size = Point()
                display.getSize(size)
                (size.x * width).toInt() to (size.y * height).toInt()
            } else {
                val rect = windowManager.currentWindowMetrics.bounds
                (rect.width() * width).toInt() to (rect.height() * height).toInt()
            }

        window?.let {
            it.setBackgroundDrawableResource(android.R.color.transparent)
            it.setLayout(xy.first, xy.second)
        }
    }
}