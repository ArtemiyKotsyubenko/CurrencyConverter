package com.example.currency_converter

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import khttp.responses.Response
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.activity_main.spinnerFrom
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity() {

    fun getApi(){
        val response: Response = khttp.get(
            url = "https://api.exchangeratesapi.io/latest",
            params = mapOf(
                "symbols" to "$spinnerTo.selectedItem",  //должно работать, но это не точно
                "base" to "$spinnerFrom.selectedItem"))

        val obj: JSONObject = response.jsonObject
        var textRes:TextView? = null
        textRes=  findViewById(R.id.textResult)
        textRes?.text = "${obj["rates"]}"
    }

    private fun setUpSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.values_list,
            android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        //spinnerFrom.onItemSelectedListener = onItemSelectedListener0 // код ниже,выводит в графу - надо ли?
        spinnerTo.adapter = adapter
        //spinnerTo.onItemSelectedListener = onItemSelectedListener1//дубль два
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var currencyList: Array<String> =
            resources.getStringArray(R.array.values_list) // обьявляем массив с названиями валют
        setUpSpinner()                        // создаем адаптер для спиннера -  вызов функции

        buttonConv.setOnClickListener {
            //функции вызываемые нажатием на кнопку
            fun onClick(p: View?) {
                getApi()                    // делаем запрос
                //button_convert()           // получаем циферки из запроса
                //counting()                // считаем циферки
                val task = CurrencyAsynk()
                task.execute()

            }
        }
    }
}

    class CurrencyAsynk() : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String? {
            // ...
            return null;
        }

        override fun onPreExecute() {
            super.onPreExecute()
            // ...
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            // ...
        }
    }












      //  Toast.makeText(
           // this, "Spinner 1 " + spinnerFrom.selectedItem.toString() +
           //       "\nSpinner 2 " + spinnerTo.selectedItem.toString(), Toast.LENGTH_LONG) }
