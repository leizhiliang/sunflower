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
class PlantDetailBenchmarks {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun plantDetailCompilationNone() = benchmarkPlantDetail(CompilationMode.None())

    @Test
    fun plantDetailCompilationPartial() = benchmarkPlantDetail(CompilationMode.Partial())

    @Test
    fun plantDetailCompilationFull() = benchmarkPlantDetail(CompilationMode.Full())

    private fun benchmarkPlantDetail(compilationMode: CompilationMode) =
        benchmarkRule.measureRepeated(
            packageName = PACKAGE_NAME,
            metrics = listOf(FrameTimingMetric()),
            compilationMode = compilationMode,
            iterations = 10,
            startupMode = StartupMode.COLD,
            setupBlock = {
                startActivityAndWait()
                goToPlantListTab()
            }
        ) {
            goToPlantDetail()
        }
}

fun MacrobenchmarkScope.goToPlantDetail(index: Int? = null) {
    val plantListSelector = By.res(packageName, "plant_list")
    val recycler = device.findObject(plantListSelector)

    // select different item each iteration, but only from the visible ones
    val currentChildIndex = index ?: ((iteration ?: 0) % recycler.childCount)

    val child = recycler.children[currentChildIndex]
    child.click()
    // wait until plant list is gone
    device.wait(Until.gone(plantListSelector), 5_000)
}
