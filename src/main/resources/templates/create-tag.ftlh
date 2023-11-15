<!DOCTYPE html>
<html lang="en">

<head>
    <title>Создать тег</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <link rel="icon" type="image/x-icon" href="/img/bar.svg">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>
        Новый тег
    </h2>
    <div>
        <a href="/admin/tags/all-tags" class="btn btn-primary btn-sm" role="button">Назад</a>
</div>
    <span>
        <form action="/admin/tags/create" method="post" name="addTagDto">
            <table class="table">
                <thead>
                </thead>
                <tbody>
                <tr>
                    <th>Существующие теги</th>
                    <td>
                        <#list tagList as tag>
                            <span>#${tag} </span>
                        </#list>
                    </td>
                </tr>
                <tr>
                    <th>Новый тег</th>
                    <td><input name="tag" class="form-control form-control-sm" type="text"/></td>
                </tr>
                </tbody>
            </table>
            <button class="btn btn-primary" type="submit">Сохранить</button>
        </form>
    </div>
</div>
</body>