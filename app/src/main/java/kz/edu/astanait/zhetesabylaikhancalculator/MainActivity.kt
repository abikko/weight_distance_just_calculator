package kz.edu.astanait.zhetesabylaikhancalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kz.edu.astanait.zhetesabylaikhancalculator.databinding.ActivityMainBinding
import kz.edu.astanait.zhetesabylaikhancalculator.model.Communicator
import kz.edu.astanait.zhetesabylaikhancalculator.ui.distance_calculator.DistanceCalculatorFragment
import kz.edu.astanait.zhetesabylaikhancalculator.ui.main_calculator.MainCalculatorFragment
import kz.edu.astanait.zhetesabylaikhancalculator.ui.weight_calculator.WeightCalculatorFragment

class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_main -> {
                    setFragment(MainCalculatorFragment.newInstance())
                }
                R.id.menu_distance -> {
                    setFragment(DistanceCalculatorFragment.newInstance())
                }
                R.id.menu_weight -> {
                    setFragment(WeightCalculatorFragment.newInstance())
                }
            }
            true
        }

        setFragment(MainCalculatorFragment.newInstance(), true)
    }

    override fun setFragment(fragment: Fragment) {
        setFragment(fragment, false)
    }

    override fun setFragment(fragment: Fragment, isRoot: Boolean) {
        if (isRoot) {
            supportFragmentManager.popBackStack()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, fragment)
            .commit()
    }
}