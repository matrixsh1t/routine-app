<!doctype html>
<head>
    <title>Freemarker Example</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
          rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js">
    </script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js">
    </script>
</head>
<body>
<div class="container">
    <h2>
        Изменить задачу
    </h2>
    <div>
        <a href="/todo" class="btn btn-primary" role="button">Назад</a>
        <form action="/todo/update/${updateTaskDto.taskId}" method="post" name="updateTaskDto">
            <table class="table">
                    <tr>
                        <th>ID</th>
                        <td>
                            <div name="id">${updateTaskDto.taskId}</div>
                        </td>
                    </tr>
                    <tr>
                        <th>Дата создания</th>
                        <td>
                            <div name="createDate">${updateTaskDto.createDate}</div>
                        </td>
                    </tr>
                <tr>
                <tr>
                    <th>Задача</th>
                    <td><input name="Task" class="form-control form-control-sm" type="text" placeholder="Новая задача" value="<#if updateTaskDto.task??>${updateTaskDto.task}</#if>"/></td>
                </tr>
                <tr>
                    <th>Комментарий</th>
                    <td><input name="Comment" class="form-control form-control-sm" type="text" placeholder="Комментарии к задаче" value="<#if updateTaskDto.comment??>${updateTaskDto.comment}</#if>"/></td>
                </tr>
                <tr>
                    <th>Ответственный</th>
                    <td><div>
                        <#list responsibles as button>
                        <button class="btn btn-success btn-sm" onclick="insertButtonValue('${button}')">${button}</button>
                    </#list>
                        </div>
                    <input name="Responsible" id="responsible" class="form-control form-control-sm" type="text" placeholder="Исполнитель" value="<#if updateTaskDto.responsible??>${updateTaskDto.responsible}</#if>"/></td>
                </tr>
                <tr>
                    <th>Дата выполения</th>
                    <td><button id="DateToTomorrow" >На Завтра</button>
                    <input id="DueDate" name="DueDate" class="form-control form-control-sm" type="text" placeholder="Дата выполнения" value="<#if updateTaskDto.dueDate??>${updateTaskDto.dueDate}</#if>"/></td>
                </tr>
                <tr>
                    <th>Эккаунт</th>
                    <td><div name="userName">${updateTaskDto.userName}</div></td>
                </tr>
                <tr>
                    <td>
                    <td/>
                </tr>
                </tbody>
            </table>
            <button class="btn btn-primary" type="submit">Сохранить</button>
        </form>
    </div>
</div>
<script>
    const dateField = document.getElementById("DueDate");

    function setTomorrowDate() {
        const currentDate = new Date();
        const tomorrowDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 1);
        const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
        const formattedDate = tomorrowDate.toLocaleDateString('en-US', options);
        dateField.value = formattedDate;
        event.preventDefault();
    }

    const button = document.getElementById("DateToTomorrow");
    button.addEventListener("click", setTomorrowDate);

    <!-- Change the responsible name in the input field in accordance with button name -->
    function insertButtonValue(value) {
        document.getElementById('responsible').value = value;
        event.preventDefault();
    }
</script>
</body>
</html>