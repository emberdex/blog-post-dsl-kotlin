package me.monotron.postgen.dsl

/**
 * MarkdownComponent - the root of any markdown document. Can contain any type of component.
 */
@ComponentMarker
abstract class MarkdownComponent : Component {
    val children = arrayListOf<Component>()

    protected fun <T : Component> initialiseComponent(component: T, init: T.() -> Unit): T {
        component.init()
        children.add(component)
        return component
    }

    override fun render(builder: StringBuilder) {
        for (child in children) {
            child.render(builder)
            builder.append("\n\n")
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder)
        return builder.toString()
    }
}