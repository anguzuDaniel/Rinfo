package com.danotech.rinfo.ui.screens.business

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImagePagerAdapter(private val imageList: List<ImageItem>) : RecyclerView.Adapter<ImagePagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePagerViewHolder {
        val imageView = ImageView(parent.context).apply {
            scaleType = ImageView.ScaleType.FIT_CENTER
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        return ImagePagerViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: ImagePagerViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size
}

class ImagePagerViewHolder(private val imageView: ImageView) : RecyclerView.ViewHolder(imageView) {
    fun bind(imageItem: ImageItem) {
        imageView.setImageBitmap(imageItem.bitmap)
    }
}
