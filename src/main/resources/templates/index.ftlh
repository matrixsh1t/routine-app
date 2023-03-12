<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Main</title>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h2>User CRUD operation with Freemarker Template</h2>
    <a href="/create" class="btn btn-primary" role="button">Create New Task</a>
    <table class="table">
        <thead >
        <tr>
<#--            <th scope="col">Id</th>-->
            <th scope="col">Task</th>
            <th scope="col">Comment</th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list tasks as task>
            <tr>
<#--                <th scope="row">${task.taskId}</th>-->
                <td>${task.task}</td>
                <td>${task.comment}</td>
                <td><a href="/update/${task.taskId}" class="btn btn-warning" role="button">
                        Update</a></td>
                <td><a href="/delete/${task.taskId}" class="btn btn-danger" role="button">
                        Delete</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>

