<!doctype html>
<head>
    <title>Update task</title>
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
        <form id = "myForm" action="/todo/update/${updateTaskDto.taskId}" method="post" name="updateTaskDto">
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
                    <td><input name="task" class="form-control form-control-sm" type="text" placeholder="Задача" value="<#if updateTaskDto.task??>${updateTaskDto.task}</#if>"/></td>
                </tr>
                <tr>
                    <th>Комментарий</th>
                    <td><input name="comment" class="form-control form-control-sm" type="text" placeholder="Комментарии к задаче" value="<#if updateTaskDto.comment??>${updateTaskDto.comment}</#if>"/></td>
                </tr>
                <tr>
                    <th>Город</th>
                    <td>
                        <div>
                        <input name="city" class="form-control form-control-sm" type="text" required placeholder="Город" value="<#if updateTaskDto.city??>${updateTaskDto.city}</#if>"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>Ответственный</th>
                    <td>
                        <div>
                            <input name="userName" id="nameInput" class="form-control form-control-sm" type="text" required value="${updateTaskDto.accountId.username}"/>
                        </div>
                        <div id="buttonContainer">
                        <#list responsibles as name>
                            <button class="nameButton" type="button">${name}</button>
                        </#list>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>Дедлайн</th>
                    <td>
                        <p>
                            <label><input type="radio" name="weekOrDate" value="date" checked>Дата</label>
                            <label><input type="radio" name="weekOrDate" value="week">Неделя</label>
                        </p>
                        <div id="dateInput" style="width: 20%">
                            <input name="dueDate" class="form-control form-control-sm" type="date" id="date" value="<#if updateTaskDto.dueDate??>${updateTaskDto.dueDate}</#if>"/>
                        </div>
                        <div id="weekInput" style="display:none; width: 20%">
                            <input name="dueWeek" class="form-control form-control-sm" type="week" id="week" value="<#if updateTaskDto.dueDate??>${updateTaskDto.dueDate}</#if>"/>
                        </div>
                    </td>
                <tr><th></th><td></td></tr>
                </tbody>
            </table>
            <button class="btn btn-primary" type="submit">Сохранить</button>
        </form>
    </div>
</div>
<script>
        // функция установки ответсвенного в поле по нажатию на кнопку
        document.addEventListener("DOMContentLoaded", function() {
            // Получаем ссылки на элементы DOM
            const form = document.getElementById("myForm");
            const nameInput = document.getElementById("nameInput");
            const buttonContainer = document.getElementById("buttonContainer");

            // Обработчик события клика на кнопки
            buttonContainer.addEventListener("click", function(event) {
                if (event.target.classList.contains("nameButton")) {
                    // Вставляем текст кнопки в поле input при клике
                    nameInput.value = event.target.textContent;
                    event.preventDefault(); // Предотвращаем отправку формы
                }
            });
        });

        // выбираем дату или неделю из виджетов, и отдаем в контроллер
        const weekInput = document.getElementById("weekInput");
        const dateInput = document.getElementById("dateInput");
        const radios = document.getElementsByName("weekOrDate");
        for(let i = 0; i < radios.length; i++) {
            radios[i].addEventListener("change", function(event) {
                if(event.target.value === "week") {
                    weekInput.style.display = "block";
                    dateInput.style.display = "none";
                    document.getElementById("date").value = "";
                } else if (event.target.value === "date") {
                    dateInput.style.display = "block";
                    weekInput.style.display = "none";
                    document.getElementById("week").value = "";
                }
            });
        }
</script>
</body>
</html>