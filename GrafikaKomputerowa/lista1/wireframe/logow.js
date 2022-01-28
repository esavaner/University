const c = document.getElementById('canv');
let ctx = c.getContext("2d");
const mspeed = 4;
let angle = 90;
let x = 0;
let y = 0;
let z = 0;
let scene = []

function rotRight(value) {
    angle = (angle - value) % 360;
}

function rotLeft(value) {
    angle = (angle + value) % 360;
}

function up(d) {
    newz = z + d;
    const mesh1 = createMesh(createLine(x, y, z, x, y, newz));
    scene.push(mesh1);
    z = newz
}

function forward(d) {
    var newx = x + d * Math.cos(angle * Math.PI / 180);
    var newy = y - d * Math.sin(angle * Math.PI / 180);
    const mesh1 = createMesh(createLine(x, y, z, newx, newy, z));
    scene.push(mesh1);
    x = newx;
    y = newy;
}

function execute(c) {
    split = c.split(" ");
    command = split[0];
    value = parseFloat(split[1]);
    switch(command) {
        case "forward":
            forward(value);
            break;
        case "backward":
            forward(-value);
            break;
        case "left":
            rotLeft(value);
            break;
        case "right":
            rotRight(value);
            break;
        case "up":
            up(value);
            break;
        case "down":
            up(-value);
            break;
    }
}

function start() {
    document.getElementById('txta').value.split("\n").forEach(execute);
}

function cleartxt() {
    ctx.clearRect(0, 0, 1000, 500);
    angle = 90;
    x = 500;
    y = 500;
    z = 0;
    draw = true;
    ctx.moveTo(x, y);
    document.getElementById('txta').value = "";
}


const camera = new Camera();

const render = createWireframeRenderer(c);

function createWireframeRenderer(canvas) {
    const ctx = canvas.getContext('2d');

    return function render(scene, camera) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        scene.forEach(mesh => {
            drawMesh(mesh, camera, ctx);
        });
    }
}

window.addEventListener("keydown", e => {
    scene.forEach(mesh => {
    switch (e.key) {
        case "w":
            mesh.position.y += mspeed;
            break;
        case "s":
            mesh.position.y -= mspeed;
            break;
        case "a":
            mesh.position.x += mspeed;
            break;
        case "d":
            mesh.position.x -= mspeed;
            break;
        case "ArrowUp":
            camera.pos.y += 1;
            mesh.rotation.y += 0.01;
            break;
        case "ArrowDown":
            camera.pos.y -= 1;
            mesh.rotation.y -= 0.01;
            break;
        case "ArrowLeft":
            mesh.rotation.x += 0.01;
            break;
        case "ArrowRight":
            mesh.rotation.x -= 0.01;
            break;
        case "r":
            camera.pos.z -= 1;
            break;
        case "f":
            camera.pos.z += 1;
            break;
    }});
});

function animate() {
    render(scene, camera);
    requestAnimationFrame(animate);
}

animate();

