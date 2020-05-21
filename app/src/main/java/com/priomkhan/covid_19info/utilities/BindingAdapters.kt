package com.priomkhan.covid_19info.utilities

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("flagImage")
fun flagImage(view: ImageView, imageUrl: String) {
    Glide.with(view.context)
        .load(imageUrl)
        .override(view.layoutParams.width, view.layoutParams.height)
        .into(view)
}

@BindingAdapter("countryName")
fun countryName(view: TextView, value: String?) {
    val text: String = if (value.equals(null)) {
        "Country : Not in List"
    } else {
        "Country : ${value}"

    }
    view.text = text
}

@BindingAdapter("confirmedCase")
fun confirmedCaseHeader(view: TextView, value: Long) {
    var text: String = if (value.equals(0.0)) {
        "Total Cases: 0"
    } else {
        "Total Cases: $value"

    }

    view.text = text
}

@BindingAdapter("deaths")
fun deaths(view: TextView, value: Long) {
    var text: String = if (value.equals(0.0)) {
        "Total Deaths : 0"
    } else {
        "Total Deaths : $value"

    }

    view.text = text
}

@BindingAdapter("recovered")
fun recovered(view: TextView, value: Long) {
    var text: String = if (value.equals(0.0)) {
        "Total Recovered : 0"
    } else {
        "Total Recovered : $value"

    }

    view.text = text
}

@BindingAdapter("todayCases")
fun todayCases(view: TextView, value: Long) {
    var text: String = if (value.equals(0)) {
        "Today's Cases : No Update Yet."
    } else {
        "Today's Cases : $value"

    }

    view.text = text
}

@BindingAdapter("todayDeaths")
fun todayDeaths(view: TextView, value: Long) {
    var text: String = if (value.equals(0)) {
        "Today's Deaths : No Update Yet."
    } else {
        "Today's Deaths : $value"

    }

    view.text = text
}

//@BindingAdapter("userBio")
//fun userBio(view: TextView, value: String?){
//    var text: String = if(value.equals(null)){
//        "Biography: Not Specified"
//    }else{
//        "$value"
//
//    }
//    view.text = text
//}
