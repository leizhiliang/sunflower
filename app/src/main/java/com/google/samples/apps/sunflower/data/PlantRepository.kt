package com.google.samples.apps.sunflower.data

import javax.inject.Inject
import javax.inject.Singleton

/**
 *用于处理数据操作的存储库模块。
 *
 * 从 [PlantDao] 中的流中收集是主线程安全的。 Room 支持协程并将查询执行移出主线程。
 */
@Singleton
class PlantRepository @Inject constructor(private val plantDao: PlantDao) {

    fun getPlants() = plantDao.getPlants()

    fun getPlant(plantId: String) = plantDao.getPlant(plantId)

    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
        plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)

    companion object {

        @Volatile
        private var instance: PlantRepository? = null

        fun getInstance(plantDao: PlantDao) =
            instance ?: synchronized(this) {
                instance ?: PlantRepository(plantDao).also { instance = it }
            }
    }
}
