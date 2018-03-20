package com.project.source.navigation

import ru.terrakok.cicerone.Router

class ShakRouter : Router() {

    fun finishWithResult(resultCode: Int, result: Any) {
        sendResult(resultCode, result)
        executeCommand(Finish())
    }
}