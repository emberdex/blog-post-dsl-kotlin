package me.monotron.postgen.dsl

@DslMarker
annotation class ComponentMarker

interface Component {
    fun render(builder: StringBuilder)
}

class TextComponent(val text: String) : Component {
    override fun render(builder: StringBuilder) {
        builder.append(text)
    }
}