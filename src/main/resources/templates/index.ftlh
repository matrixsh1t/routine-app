<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Main</title>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/">Today's tasks</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/all">All tasks</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled">Disabled</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <h2>${title}</h2>
    <a href="/create" class="btn btn-primary" role="button">Create New Task</a>
    <table class="table">
        <thead >
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Task</th>
            <th scope="col">Comment</th>
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
                <td>${task.performDate}</td>
                <td><a href="/tomorrow/${task.taskId}" class="btn btn-warning" role="button">
                        Tomorrow</a></td>
                <td><a href="/next-week/${task.taskId}" class="btn btn-warning" role="button">
                        Next week</a></td>
                <td><a href="/next-month/${task.taskId}" class="btn btn-warning" role="button">
                        Next month</a></td>
                <td><a href="/update/${task.taskId}" class="btn btn-warning" role="button">
                        Update</a></td>
                <td><a href="/delete/${task.taskId}" class="btn btn-danger" role="button">
                        Delete</a></td>
                <td><a href="/close/${task.taskId}" class="btn btn-danger" role="button">
                        Close</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>

