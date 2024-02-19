package com.dumitrachecristian.petapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dumitrachecristian.petapp.model.AnimalDto
import com.dumitrachecristian.petapp.model.Breeds
import com.dumitrachecristian.petapp.model.Photo

@Entity(
    tableName = "PET_ENTITY",
    indices = [ Index(value = ["ID"], unique = true) ],
)
data class PetEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "ID")
    var id: Int = 0,

    @ColumnInfo(name = "TYPE")
    val type: String,

    @ColumnInfo(name = "URL")
    val url: String,

    @ColumnInfo(name = "PHOTO_URL")
    val photoUrl: String,

    @ColumnInfo(name = "SPECIES")
    val species: String,

    @ColumnInfo(name = "PRIMARY_BREED")
    val primaryBreed: String,

    @ColumnInfo(name = "SECONDARY_BREED")
    val secondaryBreed: String,

    @ColumnInfo(name = "MIXED")
    val mixed: Boolean,

    @ColumnInfo(name = "UNKNOWN_BREED")
    val unknownBreed: Boolean,

    @ColumnInfo(name = "AGE")
    val age: String,

    @ColumnInfo(name = "GENDER")
    val gender: String,

    @ColumnInfo(name = "NAME")
    val name: String,

    @ColumnInfo(name = "DESCRIPTION")
    val description: String,

    @ColumnInfo(name = "STATUS")
    val status: String,

    @ColumnInfo(name = "FAVORITE")
    val isFavorite: Boolean
)

fun AnimalDto.toPetEntity(): PetEntity {
    return PetEntity(
        id = id,
        type = type ?: "",
        species = species ?: "",
        primaryBreed = breeds.primary ?: "",
        secondaryBreed = breeds.secondary ?: "",
        mixed = breeds.mixed ?: false,
        unknownBreed = breeds.unknown ?: false,
        age = age ?: "",
        gender = gender ?: "",
        name = name ?: "",
        description = description ?: "",
        status = status ?: "",
        isFavorite = isFavorite,
        photoUrl = if (photos.isNullOrEmpty()) {
            ""
        } else {
            photos[0].medium
        },
        url = url,
    )
}

fun PetEntity.toPet(): AnimalDto {
    return AnimalDto(
        id = id,
        type = type,
        species = species,
        breeds = Breeds(
            primary = primaryBreed,
            secondary = secondaryBreed,
            mixed = mixed,
            unknown = unknownBreed
        ), age = age, gender = gender, name = name,
        description = description, status = status,
        contact = null,
        links = null,
        photos = listOf(Photo(medium = photoUrl, small = "", large = "", full = "")),
        size = null,
        isFavorite = isFavorite,
        url = url,
    )
}