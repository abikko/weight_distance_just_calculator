package kz.edu.astanait.zhetesabylaikhancalculator.model

import androidx.fragment.app.Fragment

interface Communicator {
    fun setFragment(fragment: Fragment)
    fun setFragment(fragment: Fragment, isRoot: Boolean)
}