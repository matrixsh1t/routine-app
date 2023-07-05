<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main page</title>
</head>
<body>
    <h1>This is a main page</h1>
    <#if currentUser == "admin">
        <div><a href="/todo">Todo App</a></div>
        <div><a href="/admin">Админка</a></div></#if>
    <div><a href="#">Tasks App</a></div>
    <div><a href="/logout">Выйти</a></div>
</body>
</html>