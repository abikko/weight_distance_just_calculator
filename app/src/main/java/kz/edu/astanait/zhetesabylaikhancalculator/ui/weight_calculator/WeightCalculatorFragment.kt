package kz.edu.astanait.zhetesabylaikhancalculator.ui.weight_calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kz.edu.astanait.zhetesabylaikhancalculator.R
import kz.edu.astanait.zhetesabylaikhancalculator.databinding.FragmentWeightCalculatorBinding
import kz.edu.astanait.zhetesabylaikhancalculator.utils.DistanceAndWeightUtils
import java.lang.NumberFormatException


class WeightCalculatorFragment : Fragment() {
    private val TAG : String = "WCF"


    private lateinit var binding: FragmentWeightCalculatorBinding
    private var choosedWeightFrom: Int = 0
    private var choosedWeightTo: Int = 0
    private var choosedWeightUtilFrom: Double = 0.0
    private var choosedWeightUtilTo: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_weight_calculator, container, false)
        choosedRadioButtons()
        binding.etNumberWeightCalculator.addTextChangedListener(textWatcher)
        binding.rgWeightFrom.setOnCheckedChangeListener { group, checkedId ->
            choosedRadioGroupFrom(checkedId)
        }
        binding.rgWeightTo.setOnCheckedChangeListener { group, checkedId ->
            choosedRadioGroupTo(checkedId)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WeightCalculatorFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            createResult()
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    private fun choosedRadioButtons() {
        choosedWeightFrom = binding.rgWeightFrom.checkedRadioButtonId
        choosedWeightTo = binding.rgWeightTo.checkedRadioButtonId
        choosedRadioGroupFrom(choosedWeightFrom)
        choosedRadioGroupTo(choosedWeightTo)
    }

    private fun choosedRadioGroupFrom(radioButtonId: Int){
        when (radioButtonId) {
            R.id.rbWeightFromKg -> choosedWeightUtilFrom = DistanceAndWeightUtils.KILO
            R.id.rbWeightFromNg -> choosedWeightUtilFrom = DistanceAndWeightUtils.NANO
            R.id.rbWeightFromMg -> choosedWeightUtilFrom = DistanceAndWeightUtils.MILLI
        }
        createResult()
    }

    private fun choosedRadioGroupTo(radioButtonId: Int) {
        when (radioButtonId) {
            R.id.rbWeightToKg -> choosedWeightUtilTo = DistanceAndWeightUtils.KILO
            R.id.rbWeightToNg -> choosedWeightUtilTo = DistanceAndWeightUtils.NANO
            R.id.rbWeightToMg -> choosedWeightUtilTo = DistanceAndWeightUtils.MILLI
        }
        createResult()
    }
    private fun createResult(){
        try {
            binding.tvWeightResult.text = (binding.etNumberWeightCalculator.text.toString()
                .toDouble() * (choosedWeightUtilFrom / choosedWeightUtilTo)).toString()
        } catch (exception: NumberFormatException) {
            Toast.makeText(context!!, "Try to write double number with dot", Toast.LENGTH_SHORT)
                .show()
        }
    }
}