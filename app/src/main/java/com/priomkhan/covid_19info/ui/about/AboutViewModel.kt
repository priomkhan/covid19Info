package com.priomkhan.covid_19info.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Credits:\n" +
                "This application uses Open Source components. \n" +
                "You can find the source code of their open source projects along with license information below.\n" +
                "We acknowledge and are grateful to these developers for their contributions to open source.\n" +
                " Covid-19 Global and Country Data: \n" + "" +
                "Project : Novel Covid API : https://corona.lmao.ninja/"
    }


    //    TextView link = (TextView) findViewById(R.id.textView1);
    val linkText: String =
        "Visit the <a href='https://corona.lmao.ninja/'>https://corona.lmao.ninja/</a> web page."
//    link.setText(Html.fromHtml(linkText));
//    link.setMovementMethod(LinkMovementMethod.getInstance());


    val text: LiveData<String> = _text
}