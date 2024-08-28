package com.gangwonhyuil.gangwonhyuil.ui.ai

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gangwonhyuil.gangwonhyuil.data.remote.Ai.AiDTO
import com.gangwonhyuil.gangwonhyuil.databinding.AiLeftItemBinding
import com.gangwonhyuil.gangwonhyuil.databinding.AiRightItemBinding

class AiAdapter(private val mContext: Context
): RecyclerView.Adapter<ViewHolder>() {

    private var leftitem: MutableList<AiDTO> = mutableListOf()
    private var rightitem: MutableList<AiDTO> = mutableListOf()

    companion object {
        const val TYPE_RIGHT = 0
        const val TYPE_LEFT = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        Log.d("sdc", "Ai Chating = $leftitem")

        return when (viewType) {

            TYPE_RIGHT -> {
                val binding = AiRightItemBinding.inflate(inflater, parent, false)
                RightHolder(binding)
            }

            TYPE_LEFT -> {
                val binding = AiLeftItemBinding.inflate(inflater, parent, false)
                LeftHolder(binding)
            }
//            TYPE_PROFILE -> ProfileViewHolder(LayoutInflater.from(parent.context).inflate(RvProfileItemBinding, parent, false))
//            TYPE_MATCH -> MactchViewHolder(LayoutInflater.from(parent.context).inflate(RvInfoItemBinding, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
        /** 기존코드
        val binding = RvProfileItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProfileViewHolder(binding)
         */
    }

    override fun getItemCount(): Int {
        return leftitem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val currentItem = leftitem[position]) {
            is AiDTO -> (holder as LeftHolder).bind(currentItem)
            is AiDTO -> (holder as RightHolder).bind(currentItem)
        }
        /* 바뀌기 전 기존코드
        when (val item = item[position]) {
            is SummonerMatch.SummonerModel -> (holder as ProfileViewHolder).bind(item)
            is SummonerMatch.MatchInfoModel -> (holder as MactchViewHolder).bind(item)
            else -> {

            }
        }
        */

        /** 기존코드
        val currentItem = item[position]
        holder.name.text = currentItem.name
        holder.level.text = currentItem.level.toString()
        holder.bind(item[position])
         */
    }

    override fun getItemViewType(position: Int): Int {
        return if (leftitem[position] is AiDTO) TYPE_RIGHT else TYPE_LEFT
        /* 바뀌기 전 코드
        return if(item[position] is SummonerMatch.SummonerModel) TYPE_PROFILE else TYPE_MATCH
         */
    }

    fun additem(chatingList: MutableList<AiDTO>) {
        leftitem.addAll(chatingList)
    }

    inner class LeftHolder(val binding: AiLeftItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val lefttime = binding.tvLeftTime
        val leftmessage = binding.tvLeftMessage

        fun bind(item: AiDTO) {
            lefttime.text = item.time
            leftmessage.text = item.message
        }
    }

    inner class RightHolder(val binding: AiRightItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val righttime = binding.tvRightTime
        val rightmessage = binding.tvRightMessage

        fun bind(item: AiDTO) {
            righttime.text = item.time
            rightmessage.text = item.message
        }
    }
}