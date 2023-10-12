package com.example.superheros.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.util.ListResourceBundle

data class Hero(
    @DrawableRes val imageResourceId: Int,
    @StringRes val heroResourceID: Int,
    @StringRes val descriptionResourceID: Int,
    @StringRes val vulnResourceId: Int,
    @StringRes val vulndetailResourceId: Int,
    @StringRes val heroWeakness: Int
)

