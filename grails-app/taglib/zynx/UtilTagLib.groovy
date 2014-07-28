package zynx

class UtilTagLib {
    static namespace = "hub"

    def certainBrowser = { attrs, body ->
        if (request.getHeader('User-Agent') =~ attrs.userAgent ) {
            out << body()
        }
    }
    
    def eachFollower = { attrs, body ->
        def followers = attrs.followers
        followers?.each { follower ->
            body(followUser: follower)
        }
    }
    
    def tinyThumbnail = { attrs ->
        def loginId = attrs.userId
        out << "<img src='"
        out << g.createLink(action: "tiny", controller: "image", id: userId)
        out << "' alt='${loginId}'"
    }
    
}