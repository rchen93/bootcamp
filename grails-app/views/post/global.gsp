<html>
    <head>
        <title>Global Timeline</title>
        <meta name="layout" content="main"/>
    </head>

    <body>
        <h1>Global Timeline</h1>

        <g:if test="${flash.message}">
            <div class="flash">
                ${flash.message}
            </div>
        </g:if>
        
        <g:if test="${session.user}">
        <div id="newPost">
            <h3>What is ${session.user.profile.fullName} hacking on right now?</h3>
        </div>
        </g:if>
        <g:paginate action="global" total="${postCount}" max="25"/>
    </body>
</html>