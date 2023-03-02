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
    <div class="row">
        <div class="col-9"></div>
        <#list tasks as t>
            <div class="list-group">
                <a href="#" class="list-group-item list-group-item-action">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">${t.task}</h5>
                        <small class="text-muted">${t.dateCreate}</small>
                    </div>
                    <p class="mb-1">${t.comment}</p>
                    <small class="text-muted">And some muted small print.</small>
                </a>
            </div>
        </#list>
    </div>
</div>
</div>
</body>
</html>

