class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        "/timeline" {
            controller = "post"
            action = "timeline"
            id = "chuck_norris"
        }

        "/users/$id" {
            controller = "post"
            action = "timeline"
        }

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
