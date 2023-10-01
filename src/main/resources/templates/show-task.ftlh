<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Main</title>
    <link rel="icon" type="image/x-icon" href="/img/bar.svg">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>

<nav class="navbar fixed-top bg-light navbar-expand-lg" style="background-color: #e3f2fd;">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">
            <img src="/img/house.svg" alt="Домой" width="30" height="30">
        </a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <#if currentUserName == "admin">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/todo/today-tasks">На сегодня</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/todo/tomorrow-tasks">На завтра</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/todo/next-week-tasks">Следующая неделя</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/todo/next-month-tasks">Следующий месяц</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/todo/all-act-tasks">Активные</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/todo/all-tasks">Все задачи</a>
                    </li>
                </#if>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/todo/user-act-tasks">Ваши активные задачи</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/todo/user-all-tasks">Все ваши задачи</a>
                </li>
                <!-- Форма поиска задач -->
                <form action="/todo/search" method="get" class="d-flex" role="search">
                    <input class="form-control me-2"
                           type="search" name="searchString"
                           placeholder="Поиск по задачам"
                           value="<#if searchString??>${searchString}</#if>"
                           aria-label="Search">
                    <button class="btn btn-outline-success btn-sm" type="submit">Поиск</button>
                </form>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        ${currentUserName}
                    </a>
                    <ul class="dropdown-menu">
                        <#if currentUserName == "admin">
                            <li class="nav-item">
                                <a class="dropdown-item" href="/admin">Админка</a>
                            </li>
                        </#if>
                        <li>
                            <a class="dropdown-item" href="/logout">Logout</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid" style="padding-top: 60px;">
    <h4>${title} (${taskNum})</h4>
    <a href="/todo/create" class="btn btn-primary" role="button">Create New Task</a>
    <table class="table table-striped table-bordered table-sm">
        <thead class="thead-light">
        <tr>
            <th scope="col">Задача</th>
            <th scope="col">Детали</th>
            <th scope="col">Город</th>
            <th scope="col" style="text-align: center">Дедлайн</th>
            <#if currentUserName == "admin">
            <th scope="col" style="text-align: center">Юзер</th>
            <th scope="col" style="text-align: center">Отложить на:</th>
            </#if>
            <th scope="col" style="text-align: center">Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list tasks as task>
            <tr>
                <td class="w-25 p-3">${task.task}</td>
                <td class="w-25 p-3 ">${task.comment}</td>
                <td class="w-25 p-3">${task.city}</td>
                <td style="width: 9%; text-align: center">${task.dueDate}</td>
                <#if currentUserName == "admin">
                    <td style="width: 9%; text-align: center">${task.accountId.username}</td>
                    <td style="width: 9%; text-align: center">
                        <a href="/todo/tomorrow/${task.taskId}"
                           class="btn btn-warning btn-sm"
                           role="button">Завтра</a>
                        <br>
                        <a href="/todo/next-week/${task.taskId}"
                           class="btn btn-warning btn-sm"
                           role="button">Неделю</a>
                        <br>
                        <a href="/todo/next-month/${task.taskId}"
                           class="btn btn-warning btn-sm"
                           role="button">Месяц</a>
                    </td>
                </#if>
                <td style="width: 9%; text-align: center">
                    <#if task.status == "a">
                        <a href="/todo/update/${task.taskId}"
                           class="btn btn-warning btn-sm"
                           role="button">Обновить</a>
                        <br>
                        <a href="/todo/close/${task.taskId}"
                           class="btn btn-success btn-sm "
                           role="button">Закрыть</a>
                        <br>
                    <#else>
                        <p><h6>Выполнено</h6></p>
                    </#if>
                    <#if currentUserName == "admin">
                        <a href="/todo/delete/${task.taskId}"
                           class="btn btn-danger btn-sm"
                           role="button">Удалить</a>
                    </#if>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
<script src="/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>

