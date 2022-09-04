package com.yogeshpaliyal.common.constants

annotation class AccountType() {
    companion object {
        const val DEFAULT = 1 // used to store password and user information
        const val TOTP = 2 // used to store Time base - One time Password
       /* const val HOTP = 3
        const val MOTP = 4
        const val STEAM = 5*/
    }
}
