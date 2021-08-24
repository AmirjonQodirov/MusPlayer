package com.musplayer.utils

import java.util.*

class Song(
    var uri: String = "",
    var title: String = "",
    var artist: String = "",
    var album: String = "",
    var imgUri: String = "",
    var directory: String = "",
    var lastUpdate: Date,

    var is_plying: Boolean = false,
    var time_play: Int = 0
) {}
