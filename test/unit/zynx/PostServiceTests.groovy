package zynx

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(PostService)
@Mock([User, Post])
class PostServiceTests {

    void testSavePostsAndAddToUser() {
        new User(userId: "chuck_norris", password: "password").save(failOnError: true)

        def newPost = service.createPost("chuck_norris", "First Post!")

        assert newPost.content == "First Post!"
        assert User.findByUserId("chuck_norris").posts.size() == 1
    }
}
