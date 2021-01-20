package kz.edu.astanait.zhetesabylaikhancalculator.ui.distance_calculator

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
import kz.edu.astanait.zhetesabylaikhancalculator.databinding.FragmentDistanceCalculatorBinding
import kz.edu.astanait.zhetesabylaikhancalculator.utils.DistanceAndWeightUtils
import java.lang.NumberFormatException

class DistanceCalculatorFragment : Fragment() {

    private lateinit var binding: FragmentDistanceCalculatorBinding
    private var choosedDistanceFrom: Int = 0
    private var choosedDistanceTo: Int = 0
    private var choosedDistanceUtilFrom: Double = 0.0
    private var choosedDistanceUtilTo: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_distance_calculator,
            container,
            false
        )
        choosedRadioButtons()

        binding.etNumberDistanceCalculator.addTextChangedListener(textWatcher)
        binding.rgDistanceFrom.setOnCheckedChangeListener { group, checkedId ->
            choosedRadioGroupFrom(checkedId)
        }

        binding.rgDistanceTo.setOnCheckedChangeListener { group, checkedId ->
            choosedRadioGroupTo(checkedId)
        }

        return binding.root
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

    companion object {
        @JvmStatic
        fun newInstance() =
            DistanceCalculatorFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun choosedRadioButtons() {
        choosedDistanceFrom = binding.rgDistanceFrom.checkedRadioButtonId
        choosedDistanceTo = binding.rgDistanceTo.checkedRadioButtonId
        choosedRadioGroupFrom(choosedDistanceFrom)
        choosedRadioGroupTo(choosedDistanceTo)
    }

    private fun choosedRadioGroupFrom(radioButtonId: Int) {
        when (radioButtonId) {
            R.id.rbDistanceFromKm -> choosedDistanceUtilFrom = DistanceAndWeightUtils.KILO
            R.id.rbDistanceFromMillies -> choosedDistanceUtilFrom = DistanceAndWeightUtils.NANO
            R.id.rbDistanceFromMm -> choosedDistanceUtilFrom = DistanceAndWeightUtils.MILLI
        }
        createResult()
    }

    private fun choosedRadioGroupTo(radioButtonId: Int) {
        when (radioButtonId) {
            R.id.rbDistanceToKm -> choosedDistanceUtilTo = DistanceAndWeightUtils.KILO
            R.id.rbDistanceToMillies -> choosedDistanceUtilTo = DistanceAndWeightUtils.NANO
            R.id.rbDistanceToMm -> choosedDistanceUtilTo = DistanceAndWeightUtils.MILLI
        }
        createResult()
    }

    private fun createResult(){
        try {
            binding.tvDistanceResult.text = (binding.etNumberDistanceCalculator.text.toString()
                .toDouble() * (choosedDistanceUtilFrom / choosedDistanceUtilTo)).toString()
        } catch (exception: NumberFormatException) {
            Toast.makeText(context!!, "Try to write double number with dot", Toast.LENGTH_SHORT)
                .show()
        }
    }
}