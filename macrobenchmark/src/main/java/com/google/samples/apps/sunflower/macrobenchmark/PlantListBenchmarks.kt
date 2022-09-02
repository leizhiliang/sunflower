package com.google.samples.apps.sunflower.macrobenchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlantListBenchmarks {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun openPlantList() = openPlantList(CompilationMode.None())

    @Test
    fun plantListCompilationPartial() = openPlantList(CompilationMode.Partial())

    @Test
    fun plantListCompilationFull() = openPlantList(CompilationMode.Full())

    private fun openPlantList(compilationMode: CompilationMode) =
        benchmarkRule.measureRepeated(
            packageName = PACKAGE_NAME,
            metrics = listOf(FrameTimingMetric()),
            compilationMode = compilationMode,
            iterations = 5,
            startupMode = StartupMode.COLD,
            setupBlock = {
                pressHome()
                // Start the default activity, but don't measure the frames yet
                startActivityAndWait()
            }
        ) {
            goToPlantListTab()
        }
}

fun MacrobenchmarkScope.goToPlantListTab() {
    // Find the tab with plants list
    val plantListTab = device.findObject(By.descContains("Plant list"))
    plantListTab.click()

    // Wait until plant list has children
    val recyclerHasChild = By.hasChild(By.res(packageName, "plant_list"))
    device.wait(Until.hasObject(recyclerHasChild), 5_000)

    // Wait until idle
    device.waitForIdle()
}
