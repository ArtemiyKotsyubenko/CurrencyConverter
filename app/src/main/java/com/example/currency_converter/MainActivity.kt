package com.example.currency_converter

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUriExposedException
import android.view.View
import android.widget.*
import khttp.responses.Response
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.spinnerFrom
import org.json.JSONObject
import java.util.*


public class MainActivity : AppCompatActivity() {
    private var second :String = "USD"
    private var first: String = "EUR"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var currencyList: Array<String> =
            resources.getStringArray(R.array.values_list) // обьявляем массив с названиями валют

        fun setUpSpinner() {
            val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.values_list,
                android.R.layout.simple_spinner_item
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFrom.adapter = adapter
            spinnerTo.adapter = adapter

            spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    first = spinnerFrom.selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    //var first: String = "EUR"
                    Toast.makeText(
                        this@MainActivity,
                        "ничего не выбрано, первая валюта - евро",
                        Toast.LENGTH_LONG
                    )
                }
            }

            spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    second = spinnerTo.selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    //var first: String = "USD"
                    Toast.makeText(
                        this@MainActivity,
                        "ничего не выбрано, вторая валюта - доллар",
                        Toast.LENGTH_LONG
                    )
                }

            }

        }




        buttonConv.setOnClickListener {
            //функции вызываемые нажатием на кнопку
            fun onClick(p: View?) {
                setUpSpinner()
                val task = CurrencyTask(this)
                task.execute()
            }
        }
    }


    class CurrencyTask constructor(private var activity: MainActivity) :
        AsyncTask<MainActivity, Void, Void>() {

        override fun onPreExecute() {
            super.onPreExecute()

            val response: Response = khttp.get(
                url = "https://api.exchangeratesapi.io/latest",
                params = mapOf(
                    "symbols" to "${activity.second}",  //должно работать, но это не точно
                    "base" to "${activity.first}"
                )
            )
            val obj: JSONObject = response.jsonObject
            //var valueName = "${obj["rates"]}" получаем название и курс
            val nameValue: JSONObject = obj.getJSONObject("rates")
            val valueStr: String = nameValue.getString(activity.second)  //получаем курс
            val rate: Double = valueStr.toDouble()
            val input: EditText = activity.findViewById(R.id.input)
            //var result: Double = (input * rate).toString()
            //return result
        }


//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            var textResult: TextView = findViewById(R.id.textResult)
//            textResult.text = result
//        }


        override fun doInBackground(vararg params: MainActivity?): Void {
            TODO("Not yet implemented")
        }
    }


}


//  Toast.makeText(
// this, "Spinner 1 " + spinnerFrom.selectedItem.toString() +
//       "\nSpinner 2 " + spinnerTo.selectedItem.toString(), Toast.LENGTH_LONG) }