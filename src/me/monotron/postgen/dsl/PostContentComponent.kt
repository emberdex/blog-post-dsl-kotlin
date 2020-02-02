package me.monotron.postgen.dsl

import me.monotron.postgen.dsl.exception.InvalidBlogPostException

enum class HeadingLevel(val level: Int) {
    LEVEL_1(1),
    LEVEL_2(2),
    LEVEL_3(3),
    LEVEL_4(4),
    LEVEL_5(5),
    LEVEL_6(6)
}

abstract class PostContentComponent : ContentComponent() {
    fun paragraph(init: Paragraph.() -> Unit) = initialiseComponent(Paragraph(), init)
    fun heading(headingLevel: HeadingLevel, init: Heading.() -> Unit) = initialiseComponent(Heading(headingLevel), init)
    fun image(href: String, altText: String, titleText: String = "", init: Image.() -> Unit = {}) = initialiseComponent(Image(href, altText, titleText), init)
    fun hyperlink(href: String, linkText: String, tooltipText: String = "", init: Hyperlink.() -> Unit = {}) = initialiseComponent(Hyperlink(href, linkText, tooltipText), init)
    fun codeBlock(language: String, init: CodeBlock.() -> Unit) = initialiseComponent(CodeBlock(language), init)
}

class Paragraph : ContentComponent()

class Heading(val headingLevel: HeadingLevel) : ContentComponent() {
    override fun render(builder: StringBuilder) {
        if(this.children.isEmpty()) {
            throw InvalidBlogPostException("Attempted to render a heading with no text.")
        }

        for (i in 1..headingLevel.level) {
            builder.append("#")
        }

        builder.append(" ")

        for (text in children.filterIsInstance<TextComponent>()) {
            text.render(builder)
        }
    }
}

class Image(val href: String,
            val altText: String,
            val titleText: String = "") : ContentComponent() {
    override fun render(builder: StringBuilder) {
        builder.append("![$altText]($href")

        if(titleText.isNotBlank()) {
            builder.append(" $titleText")
        }

        builder.append(")")
    }
}

class Hyperlink(val href: String,
                val linkText: String,
                val tooltipText: String = "") : ContentComponent() {
    override fun render(builder: StringBuilder) {
        builder.append("[$linkText]($href")

        if(tooltipText.isNotBlank()) {
            builder.append(" \"$tooltipText\"")
        }

        builder.append(")")
    }
}

class CodeBlock(val language: String = "") : ContentComponent() {
    override fun render(builder: StringBuilder) {
        builder.append("```$language\n")
        builder.append(this.getText())
        builder.append("\n```")
    }
}