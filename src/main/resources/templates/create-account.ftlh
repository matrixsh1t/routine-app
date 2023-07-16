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
        Новый пользователь
    </h2>
    <div>
        <form action="/admin/account-register" method="post" name="addAccDto">
            <table class="table">
                <tbody>
                <thead>
                </thead>
                <tbody>
                <tr>
                    <th>Логин</th>
                    <td><input name="Username" class="form-control form-control-sm" type="text"/></td>
                </tr>
                <tr>
                    <th>Пароль</th>
                    <td><input name="Password" class="form-control form-control-sm" type="text"/></td>
                </tr>
                <tr>
                    <th>Email</th>
                    <td><input name="Email" class="form-control form-control-sm" type="text"/></td>
                </tr>
                <tr>
                    <th>Исполнитель</th>
                    <td><input name="Executor" class="form-control form-control-sm" type="text" placeholder="Ваше имя"/></td>
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
</body>