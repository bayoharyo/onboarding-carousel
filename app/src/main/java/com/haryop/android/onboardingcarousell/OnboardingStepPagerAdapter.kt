package com.haryop.android.onboardingcarousell

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haryop.android.onboardingcarousell.databinding.ItemOnboardingStep1Binding
import com.haryop.android.onboardingcarousell.databinding.ItemOnboardingStep2Binding
import com.haryop.android.onboardingcarousell.databinding.ItemOnboardingStep3Binding
import com.haryop.android.onboardingcarousell.model.OnboardingModel

class OnboardingStepPagerAdapter(private val context: Context) :
    RecyclerView.Adapter<OnboardingStepPagerAdapter.OnboardingStepViewHolder>() {

    // dataset

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingStepViewHolder {
        var view: View
        when (viewType) {
            0 -> {
                val viewBinding = ItemOnboardingStep1Binding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
                view = viewBinding.root
                Glide.with(context)
                    .asGif()
                    .load(R.drawable.maps)
                    .into(viewBinding.ivGif)
            }
            1 -> {
                view = ItemOnboardingStep2Binding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                ).root
            }
            else -> {
                view = ItemOnboardingStep3Binding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                ).root
            }
        }
        return OnboardingStepViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingStepViewHolder, position: Int) {
        return
    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class OnboardingStepViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(onboardingModel: OnboardingModel) {
        }
    }
}