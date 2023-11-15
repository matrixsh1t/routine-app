<!DOCTYPE html>
<html lang="en">

<head>
    <title>Create account</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <link rel="icon" type="image/x-icon" href="/img/bar.svg">
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
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
                </tbody>
            </table>
            <button class="btn btn-primary" type="submit">Сохранить</button>
        </form>
    </div>
</div>
</body>