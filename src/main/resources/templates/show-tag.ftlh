<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Теги</title>
    <link rel="icon" type="image/x-icon" href="/img/bar.svg">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css">

    <style>
    .td-ellipsis {
        white-space: pre-line; /* Запрет переноса текста на новую строку */
        overflow: hidden;     /* Скрыть текст, выходящий за пределы ячейки */
        text-overflow: clip;  /* Убрать "..." при обрезании текста */
    }
</style>
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
    <h4>Список тегов</h4>
    <a href="/admin/tags/create"
       class="btn btn-primary"
       role="button"
       style="padding: 10px;">Создать тег</a>
    <table class="table table-striped table-bordered table-sm" style="margin-top: 10px;">
        <thead class="thead-light">
        <tr>
            <th scope="col">Теги</th>
            <th scope="col">Действия</th>
        </tr>
        </thead>
        <tbody>
            <#if tags?size == 0>
                <tr>
                    <td colspan="2">Нет элементов</td>
                </tr>
            <#else>
                <#list tags as tag>
                    <tr>
                        <td class="w-25 p-3">${tag.tagName}</td>
                        <td>
                            <a href="/admin/tags/delete/${tag.tagId}"
                               class="btn btn-danger btn-sm"
                               role="button">Удалить</a>
                        </td>
                    </tr>
                </#list>
            </#if>
        </tbody>
        </table>
</div>
<script src="/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>

