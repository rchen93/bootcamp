package zynx

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PostController)
@Mock([User, Post])
class PostControllerTests {

    void testTimelineGivenId() {
       User chuck = new User(userId: "chuck_norris", password: "password")
       chuck.addToPosts(new Post(content: "A first post"))
       chuck.addToPosts(new Post(content: "A second post"))
       chuck.save(failOnError: true)

       params.id = chuck.userId

       def model = this.controller.timeline()
       assert model.user.userId == "chuck_norris"
       assert model.user.posts.size() == 2
    }

    void testErrorWhenNonExistentUser() {
    	params.id = "this-user-id-does-not-exist"
    	this.controller.timeline()
    	assert response.status == 404
    }

    void testAddPostToTimeline() {
    	User chuck = new User(
                userId: "chuck_norris",
                password: "password").save(failOnError: true)

        params.id = chuck.userId

        params.content = "Chuck Norris can unit test entire applications with a single assert."

        def model = this.controller.addPost()

        assert flash.message == "Successfully created Post"
        assert response.redirectedUrl == "/post/timeline/${chuck.userId}"
        assert Post.countByUser(chuck) == 1
    }

    void testInvalidNewPostToTimeline() {
    	User chuck = new User(userId: "chuck_norris", password: "password").save(failOnError: true)

        params.id = chuck.userId

        params.content = null

        def model = this.controller.addPost()

        assert flash.message == "Invalid or empty post"
        assert response.redirectedUrl == "/post/timeline/${chuck.userId}"
        assert Post.countByUser(chuck) == 0
    }

    void testHome() {
        params.id = "joe_cool"
                                                                   
        this.controller.home()
                                                                  
        assert response.redirectedUrl == '/post/timeline/joe_cool'

        response.reset()

        params.id = null

        this.controller.home()

        assert response.redirectedUrl == '/post/timeline/chuck_norris'
                                                                   
    }
}
