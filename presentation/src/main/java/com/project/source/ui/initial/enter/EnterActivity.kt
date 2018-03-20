package com.project.source.ui.initial.enter

import android.os.Bundle
import com.project.source.navigation.Screens
import com.project.source.ui.dashboard.DashboardActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Replace

class EnterActivity : BaseActivity() {

    override val navigator: Navigator = NavigatorImpl()
    override val layoutId = R.layout.activity_initial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            navigator.applyCommand(Replace(Screens.INITIAL_ENTER, null))
        }
    }

    private inner class NavigatorImpl : BaseNavigator() {

        override fun createFragment(screenKey: String, data: Any?) =
                when (screenKey) {
                    Screens.INITIAL_ENTER -> EnterFragment()
                    Screens.INITIAL_SIGN_IN -> SignInFragment()
                    else -> null
                }

        override fun createActivityIntent(screenKey: String, data: Any?) =
                when (screenKey) {
                    Screens.DASHBOARD -> intentFor<DashboardActivity>()
                    else -> null
                }
    }
}
