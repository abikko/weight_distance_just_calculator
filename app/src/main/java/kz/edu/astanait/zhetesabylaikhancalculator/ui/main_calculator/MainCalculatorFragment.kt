package kz.edu.astanait.zhetesabylaikhancalculator.ui.main_calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kz.edu.astanait.zhetesabylaikhancalculator.R
import kz.edu.astanait.zhetesabylaikhancalculator.databinding.FragmentMainCalculatorBinding
import kotlin.math.exp
import kotlin.properties.Delegates

class MainCalculatorFragment : Fragment(), View.OnClickListener {
    private val TAG : String = "MCF"

    private lateinit var binding: FragmentMainCalculatorBinding
    private var lastCharIsOperator: Boolean = true
    private lateinit var operators: String
    private var sum: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        operators = "*+/-"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main_calculator, container, false)

        binding.btnMainCalculatorPlus.setOnClickListener(this)
        binding.btnMainCalculatorMultiply.setOnClickListener(this)
        binding.btnMainCalculatorDivide.setOnClickListener(this)
        binding.btnMainCalculatorMinus.setOnClickListener(this)

        binding.etNumberMainCalculator.addTextChangedListener(textWatcher)

        binding.btnMainCalculatorCalculate.setOnClickListener {
            if (binding.etNumberMainCalculator.text.toString() == "") {
                Toast.makeText(context!!, "Write an operation and numbers", Toast.LENGTH_SHORT)
                    .show()
            } else if (lastCharIsOperator) {
                Toast.makeText(context!!, "Wrong expression", Toast.LENGTH_SHORT).show()
            } else {
                calculate()
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainCalculatorFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun calculate() {
        val expression: String = binding.etNumberMainCalculator.text.toString()
        Log.d(TAG, "calculate: $expression, length: ${expression.length}")
        for (i in expression.indices) {
            if(operators.contains(expression[i])){
                sum = calculateExpression(expression,i,expression[i])
            }
        }
        binding.etNumberMainCalculator.setText(sum.toString())
        sum = 0.0
    }

    private fun calculateExpression(expression: String, i: Int, operator : Char) : Double{
        var sumForReturn : Double = sum
        var numberBeforeOperation: String = ""
        var numberAfterOperation: String = ""
        var index : Int = 0
        for (j in i+1 until expression.length) {
            if(index == expression.length){
                break
            }else{
                if (operators.contains(expression[j])) {
                    index = expression.length
                } else {
                    numberAfterOperation += expression[j]
                }
            }
        }
        for (j in 0 until i) {
            if (operators.contains(expression[j])) {
                numberBeforeOperation = ""
            } else {
                numberBeforeOperation += expression[j]
            }
        }
        if (operator == '*'){
            sumForReturn += (numberBeforeOperation.toDouble() * numberAfterOperation.toDouble())
        }
        if (operator == '/'){
            sumForReturn += (numberBeforeOperation.toDouble() / numberAfterOperation.toDouble())
        }
        if (operator == '+'){
            sumForReturn += (numberBeforeOperation.toDouble() + numberAfterOperation.toDouble())
        }
        if (operator == '-'){
            sumForReturn += (numberBeforeOperation.toDouble() - numberAfterOperation.toDouble())
        }
        return sumForReturn
    }

    override fun onClick(v: View?) {
        if (lastCharIsOperator) {
            Toast.makeText(context!!, "Please write a number don't operator", Toast.LENGTH_SHORT)
                .show()
        } else {
            when (v!!.id) {
                R.id.btnMainCalculatorMultiply -> {
                    binding.etNumberMainCalculator.setText(
                        binding.etNumberMainCalculator.text.toString() + "*"
                    )
                    lastCharIsOperator = true
                }
                R.id.btnMainCalculatorPlus -> {
                    binding.etNumberMainCalculator.setText(
                        binding.etNumberMainCalculator.text.toString() + "+"
                    )
                    lastCharIsOperator = true
                }
                R.id.btnMainCalculatorDivide -> {
                    binding.etNumberMainCalculator.setText(
                        binding.etNumberMainCalculator.text.toString() + "/"
                    )
                    lastCharIsOperator = true
                }
                R.id.btnMainCalculatorMinus -> {
                    binding.etNumberMainCalculator.setText(
                        binding.etNumberMainCalculator.text.toString() + "-"
                    )
                    lastCharIsOperator = true
                }
            }
        }
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            lastCharIsOperator = false
        }

        override fun afterTextChanged(s: Editable?) {
        }

    }

}