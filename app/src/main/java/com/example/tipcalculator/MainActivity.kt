package com.example.tipcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//}
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val billTextBox: EditText = binding.editTextBill
        billTextBox.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
        binding.buttonCalculateTip.setOnClickListener{
            val billAmt: String = (billTextBox.text).toString()
            val billDbl : Double? = billAmt.toDoubleOrNull()
            calcBill(billDbl)
        }
    }
    private fun calcBill(billDbl: Double?) {
        if (billDbl == null) {
            binding.editTextBill.error = "Enter the bill amount!"
            displayTip(0.0)
            return
        }
        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId)
        {
            R.id.radioBtnAmazing -> 20.0
            R.id.radioBtnGood -> 15.0
            R.id.radioBtnOkay -> 10.0
            else -> 20.0
        }
        var final : Double = (billDbl*tipPercentage)/100
        if(binding.switchRoundUpTip.isChecked){
            final = kotlin.math.ceil(final)
        }
        displayTip(final)
    }
    private fun displayTip(amt: Double)
    {
        val amtInDollars: String = NumberFormat.getCurrencyInstance().format(amt)
        binding.textView3.text = amtInDollars
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}