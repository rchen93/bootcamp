class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		/*
		  "/timeline/chuck_norris" {
            controller = "post"
            action = "timeline"
            id = "chuck_norris"
        }

        "/timeline" {
            controller = "post"
            action = "personal"
        }

        "/users/$id" {
            controller = "post"
            action = "timeline"
        }
        */

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
