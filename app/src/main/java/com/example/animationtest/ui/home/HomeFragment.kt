package com.example.animationtest.ui.home

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.animationtest.R
import com.example.animationtest.databinding.FragmentHomeBinding
import com.example.animationtest.models.ItemData
import com.example.animationtest.ui.ItemDataFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var initialY: Float = 0f
    private var isFragmentVisible = false
    private lateinit var itemData: ItemData
    private var imageMove: ImageMoveInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ImageMoveInterface) {
            imageMove = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        imageMove = null
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.draggableImage.setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialY = event.rawY
                    binding.backgroundGradient.visibility = View.VISIBLE
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaY = event.rawY - initialY
                    if (deltaY < 0) {
                        v.translationY = deltaY
                        val progress = deltaY / -400f
                        updateBackgroundGradient(progress)
                    } else {
                        resetBackgroundGradient()
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (binding.draggableImage.translationY < -400) {
                        showFragment()
                    } else {
                        resetImage()
                        resetBackgroundGradient()
                    }

                    true
                }
                else -> false
            }
        }

        homeViewModel.itemLiveData.observe(viewLifecycleOwner) {
            this.itemData = it
        }
        return root
    }

    private fun showFragment() {
        isFragmentVisible = true
        binding.dataFragment.visibility = View.VISIBLE
        childFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.data_fragment, ItemDataFragment(itemData))
            .commit()

        ObjectAnimator.ofFloat(binding.draggableImage, "translationY", -400f).start()
    }

    private fun resetImage() {
        if(isFragmentVisible) {
            childFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .remove(childFragmentManager.findFragmentById(R.id.data_fragment)!!)
                .commit()
            isFragmentVisible = false
        }
        // reset image if not needed and left in between
        ObjectAnimator.ofFloat(binding.draggableImage, "translationY", 0f).start()
    }

    private fun updateBackgroundGradient(progress: Float) {
        // Clamp progress between 0 and 1
        val clampedProgress = progress.coerceIn(0f, 1f)
        binding.backgroundGradient.visibility = View.VISIBLE

        // Scale the background gradient based on progress
        val scale = 1f + clampedProgress * 1f
        binding.backgroundGradient.scaleX = scale
        binding.backgroundGradient.scaleY = scale

        // Set alpha based on progress
        binding.backgroundGradient.alpha = 1f - clampedProgress

        if(imageMove != null) {
            imageMove!!.hideNavHeight()
        }
    }

    private fun resetBackgroundGradient() {
        // Animate the gradient back to original size and alpha
        binding.backgroundGradient.animate()
            .scaleX(1f)
            .scaleY(1f)
            .alpha(0.5f)
            .setDuration(300)
            .withEndAction {
                if (binding.backgroundGradient.alpha == 0f) {
                    binding.backgroundGradient.visibility = View.GONE
                }
            }
            .start()
        if (imageMove !=null) {
            imageMove!!.resetNavHeight()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}