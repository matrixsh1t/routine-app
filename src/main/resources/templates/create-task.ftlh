<!DOCTYPE html>
<html lang="en">
<head>
    <title>Creat task</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2>
        Новая задача
    </h2>
    <div>
        <a href="/todo" class="btn btn-primary btn-sm" role="button">Назад</a>
        <div style="margin-top: 5px">
        </div>
        <form action="/todo/create" method="post" name="addTaskDto">
            <table class="table">
                <tbody>
                <tr>
                    <th>Задача</th>
                    <td><input name="Task" class="form-control form-control-sm" type="text" required placeholder="Новая задача" value="<#if addTaskDto.task??>${addTaskDto.task}</#if>"/></td>
                </tr>
                <tr>
                    <th>Комментарий</th>
                    <td><input name="Comment" class="form-control form-control-sm" type="text" placeholder="Комментарии к задаче" value="<#if addTaskDto.comment??>${addTaskDto.comment}</#if>"/></td>
                </tr>
                <tr>
                    <th>Ответственный</th>
                    <td><input name="Responsible" class="form-control form-control-sm" type="text" required placeholder="Исполнитель" value="<#if addTaskDto.responsible??>${addTaskDto.responsible}</#if>"/></td>
                </tr>
                <tr>
                    <th>Дедлайн</th>
                    <td>
                        <p>
                            <label><input type="radio" name="weekOrDate" value="date" checked>Дата</label>
                            <label><input type="radio" name="weekOrDate" value="week">Неделя</label>
                        </p>
                        <div id="dateInput" style="width: 20%">
                            <input name="DueDate" class="form-control form-control-sm" type="date" id="date" value="<#if addTaskDto.dueDate??>${addTaskDto.dueDate}</#if>"/>
                        </div>
                        <div id="weekInput" style="display:none; width: 20%">
                            <input name="DueDate" class="form-control form-control-sm" type="week" id="week" value="<#if addTaskDto.dueDate??>${addTaskDto.dueDate}</#if>"/>
                        </div>
                    </td>

                <tr><th></th><td></td></tr>
                </tbody>
            </table>
            <button class="btn btn-primary btn-sm" type="submit">Save</button>
            <script>
                const weekInput = document.getElementById("weekInput");
                const dateInput = document.getElementById("dateInput");
                const radios = document.getElementsByName("weekOrDate");
                for(let i = 0; i < radios.length; i++) {
                    radios[i].addEventListener("change", function(event) {
                        if(event.target.value === "week") {
                            weekInput.style.display = "block";
                            dateInput.style.display = "none";
                        } else if (event.target.value === "date") {
                            dateInput.style.display = "block";
                            weekInput.style.display = "none";
                        }
                    });
                }
            </script>
        </form>
    </div>
</div>
</body>
</html>