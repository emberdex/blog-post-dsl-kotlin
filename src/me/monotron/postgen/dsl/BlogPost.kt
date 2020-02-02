package me.monotron.postgen.dsl

import me.monotron.postgen.dsl.exception.InvalidBlogPostException

abstract class ContentComponent : MarkdownComponent() {
    operator fun String.unaryPlus() {
        children.add(TextComponent(this))
    }

    fun getText(): String {
        val builder = StringBuilder()

        for (i in children.filterIsInstance<TextComponent>()) {
            builder.append(i.text)
        }

        return builder.toString()
    }
}

class BlogPost : ContentComponent() {
    fun postHeader(init: PostHeader.() -> Unit) = initialiseComponent(PostHeader(), init)
    fun content(init: PostContent.() -> Unit) = initialiseComponent(PostContent(), init)
}

class PostHeader : ContentComponent() {
    fun title(init: PostTitle.() -> Unit) = initialiseComponent(PostTitle(), init)
    fun date(init: PostDate.() -> Unit) = initialiseComponent(PostDate(), init)

    override fun render(builder: StringBuilder) {
        // Check this component's children to see if we have a post date _and_ a title.
        // If not, this is not a valid header and we should stop.
        val date: PostDate? = this.children.filterIsInstance<PostDate>().elementAtOrNull(0)
        val title: PostTitle? = this.children.filterIsInstance<PostTitle>().elementAtOrNull(0)

        if(date == null || title == null) {
            throw InvalidBlogPostException("Invalid header specification. Header should contain a PostDate and a PostTitle.")
        }

        builder.append("---\n")
        builder.append("layout: post\n")
        builder.append("title: \"${title.getText()}\"\n")
        builder.append("date: ${date.getText()}\n")
        builder.append("---")
    }
}

class PostTitle : ContentComponent()
class PostDate : ContentComponent()
class PostContent : PostContentComponent()

fun blogPost(init: BlogPost.() -> Unit) : BlogPost {
    val blogPost = BlogPost()
    blogPost.init()
    return blogPost
}