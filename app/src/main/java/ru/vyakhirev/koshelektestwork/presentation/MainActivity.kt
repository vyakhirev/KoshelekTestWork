package ru.vyakhirev.koshelektestwork.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.vyakhirev.koshelektestwork.R
import ru.vyakhirev.koshelektestwork.presentation.difference.DifferenceFragment
import ru.vyakhirev.koshelektestwork.presentation.info_ask.InfoAskFragment
import ru.vyakhirev.koshelektestwork.presentation.info_bid.InfoBidFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        openFragment(InfoAskFragment())
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.action_ask -> {
                    openFragment(InfoAskFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_bid -> {
                    openFragment(InfoBidFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_details -> {
                    openFragment(DifferenceFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment, fragment.tag)
        transaction.commit()
    }
}