package ventura.ferrer.josep.pere.proyectofinalandroid.database

import androidx.room.*

@Entity(tableName = "LatestNew")
data class LatestNewEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "latest_new_id") val topicId: String,
    @ColumnInfo(name = "latest_new_topic_title") val title: String,
    @ColumnInfo(name = "latest_new_topic_slug") val slug: String,
    @ColumnInfo(name = "latest_new_created_at") val date: String,
    @ColumnInfo(name = "latest_new_post_number") val posts: Int,
    @ColumnInfo(name = "latest_new_score") val score: Double
)

@Entity(tableName = "Topics")
data class TopicsNewEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "topic_id") val topicId: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "posts") val posts: Int,
    @ColumnInfo(name = "views") val views: Int
)

@Dao
interface LatestNewDao {
    @Query("SELECT * FROM LatestNew")
    fun getLatestNews(): List<LatestNewEntity>

    @Query("SELECT * FROM LatestNew WHERE latest_new_id LIKE :id")
    fun getLatestNewsById(id: String): LatestNewEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(latestNewsList: List<LatestNewEntity>): List<Long>

    @Delete
    fun delete(LatestNew: LatestNewEntity)
}

@Dao
interface TopicsNewDao {
    @Query("SELECT * FROM Topics")
    fun getTopics(): List<TopicsNewEntity>

    @Query("SELECT * FROM Topics WHERE topic_id LIKE :id")
    fun getTopicById(id: String): TopicsNewEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(TopicsList: List<TopicsNewEntity>): List<Long>
}

@Database(entities = [LatestNewEntity::class, TopicsNewEntity::class], version = 4)
abstract class EhHoDatabase : RoomDatabase() {
    abstract fun latestNewDao(): LatestNewDao
    abstract  fun topicsNewDao(): TopicsNewDao
}



