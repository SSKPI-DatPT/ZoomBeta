package com.inis.famo.ui.screen.fragment.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.inis.famo.R
import com.inis.famo.data.model.BestSellingEntity
import com.inis.famo.databinding.HomeBinding
import com.inis.famo.ui.adapter.HomeBestSellingAdapter
import com.inis.famo.ui.adapter.HomePagerAdapter
import com.inis.famo.ui.base.BaseFragment
import com.inis.famo.ui.screen.activity.cart.CartActivity
import com.inis.famo.utils.AppHelper.Companion.prepareListBestSelling
import com.inis.famo.utils.AppHelper.Companion.prepareListFragment
import com.inis.famo.utils.AppHelper.Companion.prepareListPager
import com.inis.famo.utils.AppHelper.Companion.prepareListTab
import com.inis.famo.utils.onPageSelected
import com.inis.famo.utils.showSnackMessage

class HomeFragment : BaseFragment<HomeBinding, HomeViewModel>(R.layout.fragment_home) {

    companion object {
        const val TAG = "HomeFragment"
        fun newInstance() = HomeFragment()
    }

    private var homePagerAdapter: HomePagerAdapter? = null
    private var homeBestSellingAdapter: HomeBestSellingAdapter? = null
    private var currentItem = 0

    override fun viewModelClass() = HomeViewModel::class.java

    @SuppressLint("CheckResult")
    override fun HomeBinding.initComponents() {
        initPageAdapter()
        homeBestSellingAdapter = HomeBestSellingAdapter(
            context = requireContext(),
            onItemClick = ::onBestSellingClick
        ).apply {
            submitList(prepareListBestSelling())
        }
        binding.rcvHomeHotBuy.adapter = homeBestSellingAdapter
    }

    private fun initPageAdapter() {
        val listProduct = prepareListPager()
        val listFragment = prepareListFragment(listProduct = listProduct)
        val listTab = prepareListTab()

        homePagerAdapter = HomePagerAdapter(
            fragmentManager = parentFragmentManager,
            listFragment = listFragment,
            listTitleTab = listTab
        )

        binding.homeViewPager.adapter = homePagerAdapter
        binding.tabProduct.setupWithViewPager(binding.homeViewPager)
        binding.homeViewPager.offscreenPageLimit = listFragment.size
        binding.homeViewPager.currentItem = currentItem
        binding.homeViewPager.onPageSelected {
            currentItem = it
        }

        binding.homeViewPager.addOnPageChangeListener(object : TabLayout.TabLayoutOnPageChangeListener(binding.tabProduct) {
            override fun onPageScrollStateChanged(state: Int) {
                binding.swipeContainer.isEnabled = state == ViewPager.SCROLL_STATE_IDLE
            }
        })
    }

    private fun onBestSellingClick(bestSelling: BestSellingEntity) {
        Toast.makeText(context, bestSelling.product_name, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("StringFormatMatches")
    override fun observeLiveData() {
    }

    override fun HomeBinding.addEvent() {
        with(binding) {
            tvHomeViewAll.setOnClickListener { tvHomeViewAll.showSnackMessage("View all") }
            ivHomeCart.setOnClickListener {
                startActivity(CartActivity.intent(context = requireContext()))
            }
        }
    }
}