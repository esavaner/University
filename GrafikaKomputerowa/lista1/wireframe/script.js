const c = document.getElementById('canv');
const mspeed = 4;

const mesh1 = createMesh(createCube(50, 50, 50));
mesh1.position.x += 200;
mesh1.position.z += 200;
const mesh2 = createMesh(createCube(70, 70, 70));
mesh2.position.x -= 200;
mesh2.position.y -= 100;
const mesh3 = createMesh(createCube(20, 20, 20));
const mesh4 = createMesh(createLine(200, 200, 0, 200, 0, 0));

const scene = [mesh1, mesh2, mesh3, mesh4]

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

