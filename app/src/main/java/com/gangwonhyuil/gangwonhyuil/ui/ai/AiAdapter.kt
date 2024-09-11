package com.gangwonhyuil.gangwonhyuil.ui.ai

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gangwonhyuil.gangwonhyuil.data.remote.ai.AiDTO
import com.gangwonhyuil.gangwonhyuil.databinding.AiLeftItemBinding
import com.gangwonhyuil.gangwonhyuil.databinding.AiRightItemBinding

class AiAdapter(private val mContext: Context
): RecyclerView.Adapter<ViewHolder>() {

    private var items: MutableList<AiDTO> = mutableListOf(
//        AiDTO("안녕하세요!\nAI 강원이 입니다.\n워케이션에 관해서 궁금하신 것이 있으신가요?", LocalDateTime.now().format(
//        DateTimeFormatter.ofPattern("HH시 mm분")), true)
    )

    private companion object {
        const val TYPE_RIGHT = 0
        const val TYPE_LEFT = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        Log.d("sdc", "Ai Chating = $items")

        return when (viewType) {

            TYPE_RIGHT -> {
                val binding = AiRightItemBinding.inflate(inflater, parent, false)
                RightHolder(binding)
            }

            TYPE_LEFT -> {
                val binding = AiLeftItemBinding.inflate(inflater, parent, false)
                LeftHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("sdc", "onbindviewholder Ai Chating = ${items[position]}")
//        (holder as LeftHolder).bind(items[position])
//        when (val currentItem = items[position]) {
//            is AiDTO -> (holder as LeftHolder).bind(currentItem)
//            is AiDTO -> (holder as RightHolder).bind(currentItem)
//        }
        when (getItemViewType(position)) {
            TYPE_LEFT -> {
                (holder as LeftHolder).bind(items[position])
            }
            TYPE_RIGHT -> {
                (holder as RightHolder).bind(items[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("sdc", "getItemViewType Ai Chating = ${position}")
        return if (items[position].isAiChat) TYPE_LEFT else TYPE_RIGHT
    }

    fun additem(chatingList: MutableList<AiDTO>) {
        items = chatingList
        Log.d("sdc", "additem Ai Chating = $items")
        notifyDataSetChanged()
    }

    inner class LeftHolder(val binding: AiLeftItemBinding) : ViewHolder(binding.root) {
        val lefttime = binding.tvLeftTime
        val leftmessage = binding.tvLeftMessage

        fun bind(item: AiDTO) {
            lefttime.text = item.time
            leftmessage.text = item.message
        }
    }

    inner class RightHolder(val binding: AiRightItemBinding) :
        ViewHolder(binding.root) {
        val righttime = binding.tvRightTime
        val rightmessage = binding.tvRightMessage

        fun bind(item: AiDTO) {
            righttime.text = item.time
            rightmessage.text = item.message
        }
    }
}