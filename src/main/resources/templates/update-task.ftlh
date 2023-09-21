<!doctype html>
<head>
    <title>Update task</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <link rel="icon" type="image/x-icon" href="/img/bar.svg">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
    </script>
</head>
<body>
<div class="container">
    <h2>Изменить задачу</h2>
    <div>
        <#if updateTaskDto.accountId.username == "admin">
            <a href="/todo/today-tasks" class="btn btn-primary btn-sm" role="button">Назад</a>
        <#else>
            <a href="/todo/user-act-tasks" class="btn btn-primary btn-sm" role="button">Назад</a>
        </#if>

        <form id = "myForm" action="/todo/update/${updateTaskDto.taskId}" method="post" name="updateTaskDto">
            <table class="table">
                <tr></tr>
                <tr>
                    <th>Дата создания</th>
                    <td>
                        <div name="createDate">${updateTaskDto.createDate}</div>
                    </td>
                </tr>
                <tr>
                    <th>Задача</th>
                    <td>
                        <input name="task"
                               class="form-control form-control-sm"
                               type="text" placeholder="Задача"
                               value="<#if updateTaskDto.task??>${updateTaskDto.task}</#if>"/>
                    </td>
                </tr>
                <tr>
                    <th>Комментарий</th>
                    <td>
                        <input name="comment"
                               class="form-control form-control-sm"
                               type="text" placeholder="Комментарии к задаче"
                               value="<#if updateTaskDto.comment??>${updateTaskDto.comment}</#if>"/>
                    </td>
                </tr>
                <tr>
                    <th>Город</th>
                    <td>
                        <div>
                            <input name="City"
                                   id="city"
                                   class="form-control form-control-sm"
                                   type="text"
                                   placeholder="Город"
                                   readonly
                                   value="<#if updateTaskDto.city??>${updateTaskDto.city}</#if>"/>
                        </div>
                        <div id="cityButtonContainer">
                            <#list cities as city>
                                <button class="cityButton btn btn-outline-secondary"
                                        style="--bs-btn-padding-y: .05rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .87rem;"
                                        type="button">${city}</button>
                            </#list>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>Ответственный</th>
                    <td>
                        <div>
                            <input name="userName"
                                   id="nameInput"
                                   class="form-control form-control-sm"
                                   type="text"
                                   required
                                   value="${updateTaskDto.accountId.username}"/>
                        </div>
                        <div id="buttonContainer">
                            <#list responsibles as name>
                                <button class="nameButton btn btn-outline-secondary"
                                        style="--bs-btn-padding-y: .05rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .87rem;"
                                        type="button">${name}</button>
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
                </tbody>
            </table>
            <button class="btn btn-success" type="submit">Сохранить</button>
        </form>
    </div>
</div>
<script>
        // функция выбора города в поле по нажатию на кнопку
        document.addEventListener("DOMContentLoaded", function() {
            // Получаем ссылки на элементы DOM
            const form = document.getElementById("myForm");
            const cityInput = document.getElementById("city");
            const cityButtonContainer = document.getElementById("cityButtonContainer");

            // Обработчик события клика на кнопки
            cityButtonContainer.addEventListener("click", function(event) {
                if (event.target.classList.contains("cityButton")) {
                    // Вставляем текст кнопки в поле input при клике
                    cityInput.value = event.target.textContent;
                    event.preventDefault(); // Предотвращаем отправку формы
                }
            });
        });

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