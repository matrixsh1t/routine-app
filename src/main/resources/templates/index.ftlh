<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Главная</title>
    <link rel="icon" type="image/x-icon" href="/img/bar.svg">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #eee;
            margin: 0;
            padding: 0;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        h4 {
            text-align: right;
            color: #333;
        }
        div {
            margin: 10px;
            padding: 10px;
            background-color: #eee;
            border: 1px solid #ddd;
            border-radius: 5px;
            text-align: center;
        }
        a {
            display: block;
            text-decoration: none;
            color: black;
        }
        a:hover {
            color: black;
            font-weight: 700;
        }
        .wrapper {
            width: 30%;
            margin: 0 auto;
        }
    </style>
</head>
<body>
    <h1>Главная страница</h1>
    <h4>Version: 1.6.2</h4>

    </span></div>
    <div class="wrapper">
        <#if currentUser == "admin">
            <div><a href="/todo/today-tasks">Задачи</a></div>
            <div><a href="/admin">Админка</a></div>
        <#else>
            <div><a href="/todo/user-act-tasks">Задачи</a></div>
        </#if>
        <div><a href="/logout">Выйти</a></div>
    </div>
</body>
</html>