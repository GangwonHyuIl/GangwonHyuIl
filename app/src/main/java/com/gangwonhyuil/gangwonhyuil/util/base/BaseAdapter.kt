package com.gangwonhyuil.gangwonhyuil.util.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T : Eigenvalue> :
    ListAdapter<Eigenvalue, BaseAdapter.BaseViewHolder<T>>(
        Eigenvalue.diffCallback
    ) {
    override fun getItemViewType(position: Int): Int = getItem(position).viewType

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<T> {
        val binding = createBinding(parent, viewType)
        return createViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<T>,
        position: Int,
    ) {
        holder.bind(getItem(position) as T)
    }

    abstract class BaseViewHolder<T : Eigenvalue>(
        view: View,
    ) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: T)
    }

    protected abstract fun createBinding(
        parent: ViewGroup,
        viewType: Int,
    ): ViewBinding

    protected abstract fun createViewHolder(binding: ViewBinding): BaseViewHolder<T>
}