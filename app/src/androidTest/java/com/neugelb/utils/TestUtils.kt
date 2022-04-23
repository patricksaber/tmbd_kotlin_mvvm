package com.neugelb.utils

import android.content.res.Resources
import android.view.View
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

object TestUtils {
    class NthMatcher internal constructor(private val id: Int, private val n: Int) : TypeSafeMatcher<View>(
        View::class.java) {
        companion object {
            var matchCount: Int = 0
        }
        init {
            var matchCount = 0
        }
        private var resources: Resources? = null
        override fun describeTo(description: Description) {
            var idDescription = Integer.toString(id)
            if (resources != null) {
                try {
                    idDescription = resources!!.getResourceName(id)
                } catch (e: Resources.NotFoundException) {
                    // No big deal, will just use the int value.
                    idDescription = String.format("%s (resource name not found)", id)
                }

            }
            description.appendText("with id: $idDescription")
        }

        public override fun matchesSafely(view: View): Boolean {
            resources = view.resources
            if (id == view.id) {
                matchCount++
                if(matchCount == n) {
                    return true
                }
            }
            return false
        }
    }

}