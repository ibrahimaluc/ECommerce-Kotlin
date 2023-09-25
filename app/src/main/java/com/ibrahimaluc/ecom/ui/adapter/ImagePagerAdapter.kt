package com.ibrahimaluc.ecom.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ibrahimaluc.ecom.R
import com.ibrahimaluc.ecom.data.util.ProductConstant
import com.ibrahimaluc.ecom.databinding.ItemImageCarouselBinding

class ImagePagerAdapter(
    private val context: Context,
    private val imageList: List<String>,

    ) : RecyclerView.Adapter<ImagePagerAdapter.ImagePagerViewHolder>() {

    private var selectedPosition = 0
    private lateinit var buttonContainer: LinearLayout

    class ImagePagerViewHolder(var binding: ItemImageCarouselBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePagerViewHolder {
        return ImagePagerViewHolder(
            ItemImageCarouselBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImagePagerViewHolder, position: Int) {
        val imageUrl = imageList[position]
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.product_placeholder_gray)
        Glide.with(context)
            .load(ProductConstant.BASE_URL_MEDIA + imageUrl)
            .apply(requestOptions)
            .into(holder.binding.ivProductImage)


    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun setButtonContainer(container: LinearLayout) {
        buttonContainer = container
        createButtons()  // Butonları oluştur
    }

    private fun createButtons() {
        for (i in imageList.indices) {
            val button = Button(context)
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            button.text = (i + 1).toString()
            button.setOnClickListener {
                setSelectedPosition(i)
            }
            buttonContainer.addView(button)
        }
        setSelectedPosition(0)
    }

    private fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
        updateButtonStates()
    }

    private fun updateButtonStates() {
        for (i in 0 until buttonContainer.childCount) {
            val button = buttonContainer.getChildAt(i) as Button
            button.isSelected = i == selectedPosition
        }
    }
}