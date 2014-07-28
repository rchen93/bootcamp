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
}
