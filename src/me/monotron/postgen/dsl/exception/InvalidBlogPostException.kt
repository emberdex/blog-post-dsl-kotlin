package me.monotron.postgen.dsl.exception

class InvalidBlogPostException constructor(override val message: String = "") : Exception(message)