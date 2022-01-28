function toCenter(point, canvas) {
    point.x += canvas.width/2;
    point.y += canvas.height/2;
}

function drawPolygon(polygon, context, camera) {
    polygon.forEach(point => {
        toCenter(point, context.canvas);
    });
    const first = polygon[0];
    context.strokeStyle = "#fff";
    context.beginPath();
    context.moveTo(first.x, first.y);
    polygon.forEach(point => {
        //if(point.z < context.canvas.width && point.x > 0 && point.y < context.canvas.height && point.y > 0 && camera.pos.z > 70) {
            context.lineTo(point.x, point.y);
        //}
    });
    context.lineTo(first.x, first.y);
    context.stroke();
}

function drawMesh(mesh, camera, context) {
    context.strokeStyle = mesh.color;

    mesh.polygons.forEach(polygon => {
        const projectedPolygon = polygon.map(point => ({...point}));
        projectedPolygon.forEach(point => {
            mesh.transform(point, camera);
            camera.project(point);
        });
        drawPolygon(projectedPolygon, context, camera);
    });
}

function createLine(x, y, z, x0, y0, z0) {
    return [
        [
            [x0, y0, z0],
            [x, y, z]
        ]
    ]
}

function createCube(x, y, z) {
    return [
        [
            [-x, -y, -z],
            [ x, -y, -z],
            [ x,  y, -z],
            [-x,  y, -z]
        ],
        [
            [-x, -y, z],
            [ x, -y, z],
            [ x,  y, z],
            [-x,  y, z]
        ],
        [
            [-x, -y, z],
            [-x, -y, -z]
        ],
        [
            [x, -y, z],
            [x, -y, -z]
        ],
        [
            [x, y, z],
            [x, y, -z]
        ],
        [
            [-x, y, z],
            [-x, y, -z]
        ]
    ];
}
