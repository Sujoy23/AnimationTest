package com.example.animationtest.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animationtest.models.ItemData

class HomeViewModel : ViewModel() {
//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text

    private val itemData = ItemData(
        itemName = "Cheese Burger \n With Cheddar",
        itemDescription = "This is a default item description. This is just the demonstration of description nothing usefull",
        itemPrice = 19.32f
    )


    val itemLiveData = MutableLiveData<ItemData>().apply {
        value = itemData
    }
}