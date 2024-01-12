package com.example.foxminded_newsfeed.data.network.welt

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class Rss (
    @field:Element(name= "channel", required = false)
    var channel: Channel? = null
)

