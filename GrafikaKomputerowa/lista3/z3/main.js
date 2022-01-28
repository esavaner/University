window.onload = main;

function main() {
    const canvas = document.getElementById('canvas');
    const gl = canvas.getContext('webgl');
    const input = document.querySelector('input');
    if (!gl) {
        return;
    }

    let pressedKeys = {};
    canvas.addEventListener("keydown", event => pressedKeys[event.key] = true);
    canvas.addEventListener("keyup", event => pressedKeys[event.key] = false);

    let engine = new Engine(gl);
    Utils.resizeCanvas(engine.gl.canvas);

    let renderT = true;

    input.addEventListener('keypress', e => {
        console.log("click")
        if (e.key === 'Enter') {
            console.log(input.value)
            const func = eval(input.value);
            engine.plot.generatePlot([-5, 5], [-5, 5], func, false);
            renderT = true;
        }
    });

    function render() {
        if (pressedKeys["ArrowUp"]) {
            engine.plot.rotation[0] -= 0.01;
            renderT = true;
        }
        if (pressedKeys["ArrowDown"]) {
            engine.plot.rotation[0] += 0.01;
            renderT = true;
        }
        if (pressedKeys["ArrowRight"]) {
            engine.plot.rotation[1] += 0.01;
            renderT = true;
        }
        if (pressedKeys["ArrowLeft"]) {
            engine.plot.rotation[1] -= 0.01;
            renderT = true;
        }
        if (pressedKeys["w"]) {
            engine.plot.translation[2] += 10;
            renderT = true;
        }
        if (pressedKeys["s"]) {
            engine.plot.translation[2] -= 10;
            renderT = true;
        }
        if (pressedKeys["d"]) {
            engine.plot.translation[0] -= 10;
            renderT = true;
        }
        if (pressedKeys["a"]) {
            engine.plot.translation[0] += 10;
            renderT = true;
        }
        if (pressedKeys["e"]) {
            engine.plot.translation[1] += 10;
            renderT = true;
        }
        if (pressedKeys["q"]) {
            engine.plot.translation[1] -= 10;
            renderT = true;
        }

        if (renderT) {
            engine.drawScene(false);
            renderT = false;
        }
        requestAnimationFrame(render);
    }

    render();
}