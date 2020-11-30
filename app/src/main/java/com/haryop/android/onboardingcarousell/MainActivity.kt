package com.haryop.android.onboardingcarousell

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.haryop.android.onboardingcarousell.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val pagerAdapter = OnboardingStepPagerAdapter(this)
        viewBinding.vp2OnboardingStep.adapter = pagerAdapter
        viewBinding.vp2OnboardingStep.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
        }
        viewBinding.vp2OnboardingStep.setPageTransformer(pageTransformer)
        val itemDecoration = HorizontalMarginItemDecoration(
            this,
            R.dimen.viewpager_current_item_horizontal_margin
        )
        viewBinding.vp2OnboardingStep.addItemDecoration(itemDecoration)
        TabLayoutMediator(viewBinding.tabs, viewBinding.vp2OnboardingStep) { tab, position ->
            viewBinding.vp2OnboardingStep.currentItem = position
        }.attach()
        val count = viewBinding.vp2OnboardingStep.adapter?.itemCount ?: 0
        for (index in 0..count) {
            if (index == viewBinding.tabs.selectedTabPosition) {
                viewBinding.tabs.getTabAt(index)?.setCustomView(
                    R.layout.image_view_indicator_selected
                )
            } else {
                viewBinding.tabs.getTabAt(index)?.setCustomView(R.layout.image_view_indicator)
            }
        }
        // viewBinding.tabs.setupWithViewPager();
        viewBinding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.setCustomView(null)?.setCustomView(R.layout.image_view_indicator)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.setCustomView(null)?.setCustomView(R.layout.image_view_indicator_selected)
            }
        })
        viewBinding.vp2OnboardingStep.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //syncProgressBar(position)
            }
        })
        syncProgressBar(2)
    }



    private fun syncProgressBar(onboardingStep: Int) {
        when(onboardingStep) {
            0 -> {
                calculateProgressBarWidth(0.0)
            }
            1 -> {
                calculateProgressBarWidth(33.0)
            }
            else -> {
                calculateProgressBarWidth(66.0)
            }
        }
    }

    private fun calculateProgressBarWidth(percentage: Double) {
        viewBinding.llProgressPlaceholder.post {
            val progressBarPlaceHolderWidth = viewBinding.llProgressPlaceholder.width
            val finalWidth = (progressBarPlaceHolderWidth * (percentage / 100)).toInt()
            ValueAnimator.ofInt(0, finalWidth).apply {
                duration = 1000
                interpolator = AccelerateDecelerateInterpolator()
                addUpdateListener {
                    val layoutParams = viewBinding.llProgress.layoutParams
                    layoutParams.width = it.animatedValue as Int
                    Log.d("animvalue", "" + it.animatedValue as Int)
                    viewBinding.llProgress.layoutParams = layoutParams
                }
                start()
            }
        }
    }
}