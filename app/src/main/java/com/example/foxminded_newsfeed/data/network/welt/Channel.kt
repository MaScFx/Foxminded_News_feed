package com.example.foxminded_newsfeed.data.network.welt

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class Channel(
    @field:ElementList(inline = true, name = "item", required = false)
    var item: List<RssWeltItem>? = null
)