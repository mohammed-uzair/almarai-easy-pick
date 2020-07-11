package com.almarai.easypick.utils.binding_adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.almarai.data.easy_pick_models.Product
import com.almarai.data.easy_pick_models.Route
import com.almarai.easypick.extensions.exhaustive
import com.almarai.easypick.utils.APP_SELECTED_LANGUAGE
import com.almarai.easypick.utils.AppLanguage

@BindingAdapter("routeDescription")
fun TextView.bindRouteDescription(route: Route?) {
    if (route != null) {
        text = when (APP_SELECTED_LANGUAGE) {
            AppLanguage.English -> route.description
            AppLanguage.Arabic -> route.descriptionArabic
            AppLanguage.Bangla -> route.description
            AppLanguage.Filipino -> route.description
            AppLanguage.Hindi -> route.description
            AppLanguage.Indonesian -> route.description
            AppLanguage.Kannada -> route.description
            AppLanguage.Malayalam -> route.description
            AppLanguage.Nepali -> route.description
            AppLanguage.Pashto -> route.description
            AppLanguage.Sinhala -> route.description
            AppLanguage.Tamil -> route.description
            AppLanguage.Telugu -> route.description
            AppLanguage.Urdu -> route.description
            AppLanguage.Vietnamese -> route.description
        }.exhaustive
    } else {
        text = ""
    }
}

@BindingAdapter("productDescription")
fun TextView.bindProductDescription(product: Product?) {
    if (product != null) {
        text = when (APP_SELECTED_LANGUAGE) {
            AppLanguage.English -> product.description
            AppLanguage.Arabic -> product.descriptionArabic
            AppLanguage.Bangla -> product.description
            AppLanguage.Filipino -> product.description
            AppLanguage.Hindi -> product.description
            AppLanguage.Indonesian -> product.description
            AppLanguage.Kannada -> product.description
            AppLanguage.Malayalam -> product.description
            AppLanguage.Nepali -> product.description
            AppLanguage.Pashto -> product.description
            AppLanguage.Sinhala -> product.description
            AppLanguage.Tamil -> product.description
            AppLanguage.Telugu -> product.description
            AppLanguage.Urdu -> product.description
            AppLanguage.Vietnamese -> product.description
        }.exhaustive
    } else {
        text = ""
    }
}