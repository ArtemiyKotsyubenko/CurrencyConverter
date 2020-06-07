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


public class MainActivity : AppCompatActivity() {
    var second: String = "USD"
    var first: String = "EUR"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                Toast.makeText(
                    this@MainActivity,
                    "reading value by spinner1 is correct\n",//
                    Toast.LENGTH_LONG
                ).show()
                first = spinnerFrom.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //var first: String = "EUR"
                Toast.makeText(
                    this@MainActivity,
                    "ничего не выбрано, первая валюта - евро",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "reading value by spinner2 is correct\n",//
                    Toast.LENGTH_LONG
                ).show()
                second = spinnerTo.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
//                Toast.makeText(
//                    this@MainActivity,
//                    "???\n",//
//                    Toast.LENGTH_LONG
//                ).show()
                //var first: String = "USD"
                Toast.makeText(
                    this@MainActivity,
                    "ничего не выбрано, вторая валюта - доллар",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        var currencyList: Array<String> =
            resources.getStringArray(R.array.values_list) // обьявляем массив с названиями валют

        buttonConv.setOnClickListener {

            //функции вызываемые нажатием на кнопку
//                Toast.makeText(
//                    this@MainActivity,
//                    "Convert button works\n",//
//                    Toast.LENGTH_LONG
//                ).show()
            val task = CurrencyTask(this)
            task.execute()

        }
    }

    class CurrencyTask constructor(private var activity: MainActivity) :
        AsyncTask<Void, Void, String>() {
        //     init {
//            Toast.makeText(
//                activity,
//                "Constructor works\n",//
//                Toast.LENGTH_LONG
//            ).show()
        //       }

        override fun onPreExecute() {
            super.onPreExecute()
        }



        override fun doInBackground(vararg params: Void?): String {

            val response: Response = khttp.get(
                url = "https://api.exchangeratesapi.io/latest",
                params = mapOf(
                    "symbols" to activity.second,  //должно работать, но это не точно
                    "base" to activity.first
                )           )
            val obj: JSONObject = response.jsonObject
            //var valueName = "${obj["rates"]}" получаем название и курс
            val nameValue: JSONObject = obj.getJSONObject("rates")
            val valueStr: String = nameValue.getString(activity.second)  //получаем курс
            val rate: Double = valueStr.toDouble()
            val input: EditText = activity.findViewById(R.id.input)

            //var inputValue: Double = input.text.toString().toDouble()
            //var countingresult: Double = (inputValue * rate)
            var textResult: TextView = activity.findViewById(R.id.textResult)

            return "some number"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var textResult: TextView = activity.findViewById(R.id.textResult)
            textResult.text = result
        }
    }
}