package com.project.source.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.project.source.navigation.Screens
import com.project.source.navigation.ShakRouter
import com.project.source.ui.TicketsListener
import com.project.source.ui.ToolbarListener
import org.jetbrains.anko.intentFor
import java.util.*
import javax.inject.Inject

class DashboardActivity : BaseActivity(), ToolbarListener, TicketsListener {

    @Inject
    lateinit var router: ShakRouter
    @Inject
    lateinit var ticketsModel: TicketsModel

    override val layoutId = R.layout.activity_dashboard
    override val navigator = NavigatorImpl()

    private lateinit var fragmentAdapter: FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.inflateMenu(R.menu.menu_support)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.support -> SupportBottomFragment().show(supportFragmentManager)
                else -> {
                }
            }
            true
        }

        with(bottomNavigationView) {

            AHBottomNavigationItem(R.string.matches, R.drawable.ic_menu_ball, R.color.colorPrimary)
                    .let(::addItem)
            AHBottomNavigationItem(R.string.my_tickets, R.drawable.ic_menu_my_tickets, R.color.colorPrimary)
                    .let(::addItem)

            resetTicketsCount()

            titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
            accentColor = ContextCompat.getColor(this@DashboardActivity, R.color.colorPrimary)

            setOnTabSelectedListener { position, _ ->
                fragmentViewPager.setCurrentItem(position, true)
                true
            }
        }

        fragmentAdapter = FragmentAdapter(supportFragmentManager)
        fragmentViewPager.adapter = fragmentAdapter

        intent.getIntExtra(EXTRAS, MenuItem.MATCHES.ordinal)
                .takeIf { it != MenuItem.MATCHES.ordinal }
                ?.let(bottomNavigationView::setCurrentItem)

        router.setResultListener(BUY_TICKET_CODE) { resultData ->
            if (resultData is MenuItem) {
                bottomNavigationView.currentItem = resultData.ordinal
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        router.removeResultListener(BUY_TICKET_CODE)
    }

    override fun setToolbarConfig(titleStr: String, subTitleStr: String?, v: Boolean) {
        with(toolbar) {
            title = titleStr
            subTitleStr?.let { subtitle = it }
            visibility = when {
                v -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    override fun currentFragment(): Fragment? = fragmentAdapter.currentFragment

    override fun onTicketsCountChange() {
        resetTicketsCount()
    }

    private fun resetTicketsCount() {
        ticketsModel.ticketsCount().let {
            when (it) {
                0 -> ""
                else -> it.toString()
            }.let { bottomNavigationView?.setNotification(it, MenuItem.TICKETS.ordinal) }
        }
    }

    inner class FragmentAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

        private val fragments = ArrayList<BaseFragment>()
        var currentFragment: Fragment? = null

        init {
            fragments.clear()
            fragments.add(EventsFragment())
            fragments.add(TicketsFragment())
        }

        override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
            if (currentFragment != obj) {
                currentFragment = obj as Fragment
            }
            super.setPrimaryItem(container, position, obj)
        }

        override fun getItem(position: Int) = fragments[position]

        override fun getCount() = fragments.size
    }

    companion object {
        private const val EXTRAS = "EXTRAS"
        const val BUY_TICKET_CODE = 88

        fun newIntent(context: Context, menuItem: MenuItem = MenuItem.MATCHES) =
                context.intentFor<DashboardActivity>(EXTRAS to menuItem.ordinal)
    }

    inner class NavigatorImpl : BaseNavigator() {
        override fun createActivityIntent(screenKey: String, data: Any?) =
                when (screenKey) {
                    Screens.BUY -> BuyTicketsActivity.newIntent(this@DashboardActivity)
                    Screens.TICKET -> TicketActivity.newIntent(data as Int, this@DashboardActivity)
                    else -> super.createActivityIntent(screenKey, data)
                }
    }

    enum class MenuItem {
        MATCHES, TICKETS
    }
}
