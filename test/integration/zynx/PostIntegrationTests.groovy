package zynx

import static org.junit.Assert.*
import org.junit.*

class PostIntegrationTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testFirstPost() {

        def user = new User(userId: 'joe', password: 'secret').save()
        def post1 = new Post(content: "First post... W00t!")
        user.addToPosts(post1)
        def post2 = new Post(content: "Second post...")
        user.addToPosts(post2)
        def post3 = new Post(content: "Third post...")
        user.addToPosts(post3)
        assert 3 == User.get(user.id).posts.size()

    }

    void testAccessingPosts() {

        def user = new User(userId: 'joe', password: 'secret').save()
        user.addToPosts(new Post(content: "First"))
        user.addToPosts(new Post(content: "Second"))
        user.addToPosts(new Post(content: "Third"))

        def foundUser = User.get(user.id)
        def postNames = foundUser.posts.collect { it.content }
        assert ['First', 'Second', 'Third'] == postNames.sort()

    }

    void testPostWithTags() {

        def user = new User(userId: 'joe', password: 'secret').save()

        def tagGroovy = new Tag(name: 'groovy')
        def tagGrails = new Tag(name: 'grails')
        user.addToTags(tagGroovy)
        user.addToTags(tagGrails)

        def tagNames = user.tags*.name
        assert ['grails', 'groovy'] == tagNames.sort()

        def groovyPost = new Post(content: "A groovy post")
        user.addToPosts(groovyPost)
        groovyPost.addToTags(tagGroovy)
        assert 1 == groovyPost.tags.size()

        def bothPost = new Post(content: "A groovy and grails post")
        user.addToPosts(bothPost)
        bothPost.addToTags(tagGroovy)
        bothPost.addToTags(tagGrails)
        assert 2 == bothPost.tags.size()

    }
}
