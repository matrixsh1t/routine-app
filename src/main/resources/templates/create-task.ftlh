<!doctype html>
<head>
    <title>Freemarker Example</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body>
<div class="container">
    <h2>
        Новая задача
    </h2>
    <div>
        <form action="/todo/create" method="post" name="addTaskDto">
            <table class="table">
                <tbody>
                <thead>
                </thead>
                <tbody>
                <tr>
                    <th>Задача</th>
                    <td><input name="Task" class="form-control form-control-sm" type="text" placeholder="Новая задача" value="<#if addTaskDto.task??>${addTaskDto.task}</#if>"/></td>
                </tr>
                <tr>
                    <th>Комментарий</th>
                    <td><input name="Comment" class="form-control form-control-sm" type="text" placeholder="Комментарии к задаче" value="<#if addTaskDto.comment??>${addTaskDto.comment}</#if>"/></td>
                </tr>
                <tr>
                    <th>Ответственный</th>
                    <td><input name="Responsible" class="form-control form-control-sm" type="text" placeholder="Исполнитель" value="<#if addTaskDto.responsible??>${addTaskDto.responsible}</#if>"/></td>
                </tr>
                <tr>
                    <th>Дедлайн</th>
                    <td><input name="DueDate" class="form-control form-control-sm" type="text" pattern="\d{1}|\d{2}|\d{6}" required placeholder="Дата или неделя" value="<#if addTaskDto.dueDate??>${addTaskDto.dueDate}</#if>"/></td>
                </tr>

                </tbody>
            </table>
            <button class="btn btn-primary" type="submit">Save</button>
            <tr>
                <td>
                <td/>
            </tr></form>
    </div>
</div>
</body>
</html>