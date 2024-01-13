package com.example.foxminded_newsfeed.data.network.welt

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class RssWeltItem(
    @field:Element(name = "title", required = false) var title: String = "",

    @field:Element(name = "guid", required = false) var id: String ="",

    @field:Element(name = "link", required = false) var link: String = "",

    @field:Element(name = "pubDate", required = false)
    var published: String? = null,

    @field:ElementList(name = "content",required = false, inline = true)
    var content: MutableList<Content?> = mutableListOf()
)

@Root(name = "content", strict = false)
data class Content(
    @field:Attribute(required = false)
    var url:String?=null
)