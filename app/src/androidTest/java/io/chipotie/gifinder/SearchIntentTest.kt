package io.chipotie.gifinder

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.chipotie.gifinder.ui.TrendingActivity
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
 * @author savirdev on 26/03/19
 */

@RunWith(AndroidJUnit4::class)
class SearchIntentTest{

    @get: Rule
    var intentRule: IntentsTestRule<TrendingActivity> = IntentsTestRule(TrendingActivity::class.java)

    @Test
    fun verifyTextOnSearchView(){
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("something"))
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(pressImeActionButton())

        intended(allOf(hasComponent(hasShortClassName(".ui.SearchActivity")), toPackage("io.chipotie.gifinder"), hasExtra(TrendingActivity.EXTRA_QUERY, "something")))
    }

}