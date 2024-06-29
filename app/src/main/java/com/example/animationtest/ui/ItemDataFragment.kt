package com.example.animationtest.ui

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.example.animationtest.databinding.FragmentItemDataBinding
import com.example.animationtest.models.ItemData


class ItemDataFragment(val itemData: ItemData) : Fragment() {

    private var _binder: FragmentItemDataBinding? = null
    private val binding get() = _binder!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val view = inflater.inflate(R.layout.fragment_item_data, container, false)
        _binder = FragmentItemDataBinding.inflate(inflater, container, false)
        val view: View = binding.root

        binding.itemName.text = itemData.itemName
        binding.itemDescription.text = itemData.itemDescription
        binding.itemPrice.text = "$ ${itemData.itemPrice}"

        val valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        valueAnimator.setDuration(1500)
        valueAnimator.addUpdateListener { valueAnimator ->
            val fractionAnim = valueAnimator.animatedValue as Float
            binding.orderItem.setBackgroundColor(
                ColorUtils.blendARGB(
                    Color.parseColor("#FFFFFF"),
                    Color.parseColor("#FFEB3B"),
                    fractionAnim
                )
            )
        }
        valueAnimator.start()


        return view
    }

}