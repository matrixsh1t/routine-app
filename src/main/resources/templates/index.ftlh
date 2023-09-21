<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main page</title>
    <link rel="icon" type="image/x-icon" href="/img/bar.svg">
</head>
<body>
    <h1>This is a main page</h1>

    <#if currentUser == "admin">
        <div><a href="/todo/today-tasks">Todo App</a></div>
        <div><a href="/admin">Админка</a></div>
    <#else>
        <div><a href="/todo/user-act-tasks">Todo App</a></div>
    </#if>
    <div><a href="/logout">Выйти</a></div>
</body>
</html>