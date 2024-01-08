package com.example.foxminded_newsfeed.data.network.welt

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class RssWeltItem(
    @field:Element(name = "title", required = false) var title: String = "",

    @field:Element(name = "guid", required = false) var id: String ="",

    @field:Element(name = "link", required = false) var link: String? = null,

    @field:Element(name = "pubDate", required = false)
    var published: String? = null,

    @field:ElementList(name = "content",required = false, inline = true)
//    @Path("content")
//    @Text(required=false)
    var content: MutableList<Content?> = mutableListOf()
)

//@Root(name = "thumbnail", strict = false)
//data class Image(
//    @field:Attribute var url: String? = null
//)
@Root(name = "content", strict = false)
data class Content(
    @field:Attribute(required = false)
//    @Path("content")
//    @Text(required = false)
    var url:String?=null
)

//@Root(name = "content", strict = false)
////    @Path("content")
////    @Text(required=false)
//data class Content(
//    @field:Attribute (name= "url", required = false)
//    var url: String? = null
//)

//@Root(name = "content type", strict = false)
//data class Image(
//    @field:Attribute var url: String? = null
//)
