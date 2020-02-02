package me.monotron.postgen

import me.monotron.postgen.dsl.blogPost
import java.time.LocalDateTime

class Post {
    companion object {
        val post = blogPost {
            postHeader {
                title {
                    + "Post title here"
                }

                date {
                    + LocalDateTime.now().toString()
                }
            }

            content {

            }
        }
    }
}

class Main {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            // TODO: Implement a better way of exporting markdown than "copy and paste stdout"
            println(Post.post.toString())
        }
    }
}