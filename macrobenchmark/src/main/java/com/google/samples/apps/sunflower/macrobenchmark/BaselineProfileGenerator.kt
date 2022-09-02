package com.google.samples.apps.sunflower.macrobenchmark

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalBaselineProfilesApi::class)
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun startPlantListPlantDetail() {
        rule.collectBaselineProfile(PACKAGE_NAME) {
            // start the app flow
            pressHome()
            startActivityAndWait()

            // go to plant list flow
            val plantListTab = device.findObject(By.descContains("Plant list"))
            plantListTab.click()
            device.waitForIdle()
            // sleep for animations to settle
            Thread.sleep(500)

            // go to plant detail flow
            val plantList = device.findObject(By.res(packageName, "plant_list"))
            val listItem = plantList.children[0]
            listItem.click()
            device.wait(Until.gone(By.res(packageName, "plant_list")), 5_000)
        }
    }
}
