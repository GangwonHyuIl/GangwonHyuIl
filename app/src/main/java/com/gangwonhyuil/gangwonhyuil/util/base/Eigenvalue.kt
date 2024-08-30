package com.gangwonhyuil.gangwonhyuil.util.base

import androidx.recyclerview.widget.DiffUtil

interface Eigenvalue {
    val viewType: Int
    val eigenvalue: Any
    override operator fun equals(other: Any?): Boolean

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Eigenvalue>() {
            override fun areItemsTheSame(oldItem: Eigenvalue, newItem: Eigenvalue): Boolean {
                return oldItem.eigenvalue == newItem.eigenvalue
            }

            override fun areContentsTheSame(oldItem: Eigenvalue, newItem: Eigenvalue): Boolean {
                return oldItem == newItem
            }

        }
    }
}