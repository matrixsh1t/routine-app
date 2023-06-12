<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Main</title>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
</head>
<body>
<#--<header style="position: fixed; top: 0; width: 100%; z-index: 999;">-->
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Главная</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/todo">На сегодня</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/todo/tomorrows-tasks">На завтра</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/todo/next-weeks-tasks">Следующая неделя</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/todo/next-months-tasks">Следующий месяц</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/todo/all-active">Все активные</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/todo/all">Все задачи</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" aria-current="page" href="#">${currentUser}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/logout">Logout</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/admin">Админка</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<#--</header>-->
<div class="container">
    <h2>${title} (${taskNum})</h2>
    <a href="/todo/create" class="btn btn-primary" role="button">Create New Task</a>
    <table class="table">
        <thead >
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Task</th>
            <th scope="col">Comment</th>
            <th scope="col"></th>
            <th scope="col"></th>
            <th scope="col"></th>
            <th scope="col"></th>
            <th scope="col"></th>
            <th scope="col"></th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list tasks as task>
            <tr>
                <th scope="row">${task.taskId}</th>
                <td>${task.task}</td>
                <td>${task.comment}</td>
                <td>${task.responsible}</td>
                <td>${task.dueDate}</td>
                <td><a href="/todo/tomorrow/${task.taskId}" class="btn btn-warning" role="button">
                        Tomorrow</a></td>
                <td><a href="/todo/next-week/${task.taskId}" class="btn btn-warning" role="button">
                        Next week</a></td>
                <td><a href="/todo/next-month/${task.taskId}" class="btn btn-warning" role="button">
                        Next month</a></td>
                <td><a href="/todo/update/${task.taskId}" class="btn btn-warning" role="button">
                        Update</a></td>
                <#if currentUser == "admin">
                <td><a href="/todo/delete/${task.taskId}" class="btn btn-danger" role="button">
                        Delete</a></td>
                </#if>
                <td><a href="/todo/close/${task.taskId}" class="btn btn-danger" role="button">
                        Close</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>

