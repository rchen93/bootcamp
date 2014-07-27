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
}
