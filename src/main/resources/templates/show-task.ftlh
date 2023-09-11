<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Main</title>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
</head>
<body>

<nav class="navbar fixed-top bg-light navbar-expand-lg" style="background-color: #e3f2fd;">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Главная</a>
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
                <#if currentUserName == "admin">
                <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="/admin">Админка</a>
                </li>
                </#if>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/logout">Logout</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" aria-current="page" href="#">Пользователь: ${currentUserName}</a>
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
            <th scope="col" style="text-align: center">Отложить на:</th>
            </#if>
            <th scope="col" style="text-align: center">Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list tasks as task>
            <tr>
                <td class="w-25 p-3">${task.task}</td>
                <td class="w-25 p-3">${task.comment}</td>
                <td class="w-25 p-3">${task.city}</td>
                <td style="width: 9%; text-align: center">${task.dueDate}</td>
                <#if currentUserName == "admin">
                <td style="width: 9%; text-align: center">
                    <a href="/todo/tomorrow/${task.taskId}" class="btn btn-warning btn-sm" role="button">
                        Завтра</a><br>
                    <a href="/todo/next-week/${task.taskId}" class="btn btn-warning btn-sm" role="button">
                        Неделю</a><br>
                    <a href="/todo/next-month/${task.taskId}" class="btn btn-warning btn-sm" role="button">
                        Месяц</a></td>
                </#if>
                <td style="width: 9%; text-align: center">
                    <a href="/todo/update/${task.taskId}" class="btn btn-warning btn-sm" role="button">
                        Обновить</a><br>
                    <a href="/todo/close/${task.taskId}" class="btn btn-success btn-sm " role="button">
                        Закрыть</a><br>
                    <#if currentUserName == "admin">
                        <a href="/todo/delete/${task.taskId}" class="btn btn-danger btn-sm " role="button">
                        Удалить</a></td>
                    </#if>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>

