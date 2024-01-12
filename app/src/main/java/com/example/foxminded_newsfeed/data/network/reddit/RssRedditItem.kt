package com.example.foxminded_newsfeed.data.network.reddit

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "entry", strict = false)
data class RssRedditItem(
    @field:Element(name = "title", required = false) var title: String? = null,

    @field:Element(name = "id", required = false) var id: String? = null,

    @field:Element(name = "link", required = false) var link: Link? = null,

    @field:Element(name = "published", required = false) var published: String? = null,

    @field:Element(name = "thumbnail", required = false) var imgUrl: Image? = null
)

@Root(name = "link", strict = false)
data class Link(
    @field:Attribute var href: String? = null
)

@Root(name = "thumbnail", strict = false)
data class Image(
    @field:Attribute var url: String? = null
)
