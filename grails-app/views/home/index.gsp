<html>
<head>
    <meta name="layout" content="public" />
    <title>Home Page</title>
</head>
<body>

<div id="content" role="main">
    <section class="row colset-2-its">
        <h1>Welcome ${name}!</h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>

        <ul>
            <g:each in="${vehicleList}" var="vehicle">
                <li><g:link controller="vehicle" action="show" id="${vehicle.id}">${vehicle.name} - ${vehicle.year} ${vehicle.make.name} ${vehicle.model.name}</g:link></li>
            </g:each>
        </ul>
        <p>There are ${vehicleTotal} vehicles in the database</p>
        <hr>
        <g:form action="updateName" style="margin: 1rem auto; width:320px">
            <g:textField name="name" value="" />
            <g:submitButton name="Update name" />
        </g:form>

    </section>
</div>

</body>
</html>