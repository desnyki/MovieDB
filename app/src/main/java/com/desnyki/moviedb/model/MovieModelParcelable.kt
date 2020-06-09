package com.desnyki.moviedb.model

import android.os.Parcel
import android.os.Parcelable

data class MovieModelParcelable(
    val popularity : Double,
    val vote_count : Int,
    val video : Boolean,
    val poster_path : String,
    val id : Int,
    val adult : Boolean,
    val backdrop_path : String,
    val original_language : String,
    val original_title : String,
    val genre_ids : List<Int>,
    val title : String,
    val vote_average : Double,
    val overview : String,
    val release_date : String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        ArrayList(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(popularity)
        parcel.writeInt(vote_count)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeString(poster_path)
        parcel.writeInt(id)
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeString(backdrop_path)
        parcel.writeString(original_language)
        parcel.writeString(original_title)
        parcel.writeString(title)
        parcel.writeDouble(vote_average)
        parcel.writeString(overview)
        parcel.writeString(release_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieModelParcelable> {
        override fun createFromParcel(parcel: Parcel): MovieModelParcelable {
            return MovieModelParcelable(parcel)
        }

        override fun newArray(size: Int): Array<MovieModelParcelable?> {
            return arrayOfNulls(size)
        }
    }
}