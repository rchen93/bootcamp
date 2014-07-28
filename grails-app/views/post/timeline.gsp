<html>
    <head>
        <title>
            Timeline for ${ user.profile ? user.profile.fullName : user.userId }
        </title>
        <meta name="layout" content="main"/>
        <g:javascript library="jquery"/>
        <g:if test="${user.profile?.skin}">
            <g:external dir="css" file="${user.profile.skin}.css"/>
        </g:if>
        <g:javascript>
            function clearPost(e) {
                $('#postContent').val('');
            }
            function showSpinner(visible) {
                if (visible) $('#spinner').show();
                        else $('#spinner').hide();
            }
        </g:javascript>
    </head>
    <body>
        <h1>Timeline for ${ user.profile ? user.profile.fullName : user.userId }</h1>

        <g:if test="${flash.message}">
            <div class="flash">
                ${flash.message}
            </div>
        </g:if>
        
        <div id="newPost">
            <h3>
                What is ${user.profile.fullName} hacking on right now?
            </h3>
            <p>
                <g:form>
                    <g:textArea id="postContent" name="content" rows="3" cols="50"/><br/>
                    <g:submitToRemote value="Post"
                         url="[controller: 'post', action: 'addPostAjax']"
                         update="allPosts"
                         onSuccess="clearPost(data)"
                         onLoading="showSpinner(true)"
                         onComplete="showSpinner(false)"/>

                     <g:img id="spinner" style="display: none" uri="/images/spinner.gif"/>
                </g:form>
            </p>
        </div>
        
        <div id="allPosts">
            <g:render template="postEntry" collection="${user.posts}" var="post"/>
        </div>
    </body>
</html>