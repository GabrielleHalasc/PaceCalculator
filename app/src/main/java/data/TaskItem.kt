package data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskItem (
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
)

