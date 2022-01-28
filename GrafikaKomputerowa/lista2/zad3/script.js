var canvas = document.getElementById('canv');
var gl = canvas.getContext('webgl');
var program;
var pos;
var col;
var uni;
var buffer;
var started = false;
var aroundX = document.getElementById("around").clientWidth;
var aroundY = document.getElementById("around").clientHeight;

var width = 30;
var height = 200;
var color = [1, 1, 1, 1];
var translation = [(aroundY-height)/2, (aroundY-height)/2];
var ball = {
    width: 30,
    height: 30,
    dX: 4,
    dY: 4,
    x: (aroundX-height)/2 - 60,
    y: (aroundY-width)/2
}

if(!gl) {
    gl = canvas.getContext('experimental-webgl');
}

var vertCode = [
    'attribute vec4 coordinates;',
    'uniform vec2 res;',
    'void main(void) {',
        'vec2 zeroToOne = coordinates.xy / res;',
        'vec2 zeroToTwo = zeroToOne * 2.0;',
        'vec2 clipSpace = zeroToTwo - 1.0;',
        'gl_Position = vec4(clipSpace, 0.0, 1.0);',
        'gl_PointSize = 4.0;',
    '}'
].join('\n');

var fragCode = [
    'precision mediump float;',
    'uniform vec4 color;',
    'void main(void) {',
        'gl_FragColor = color;',
    '}'
].join('\n');

function cShader(gl, t, code) {
    var shader = gl.createShader(t);
    gl.shaderSource(shader, code);
    gl.compileShader(shader);

    return shader;
}

function cProgram(gl, vCode, fCode) {
    var vShader = cShader(gl, gl.VERTEX_SHADER, vCode);
    var fShader = cShader(gl, gl.FRAGMENT_SHADER, fCode);

    var program = gl.createProgram();
    gl.attachShader(program, vShader);
    gl.attachShader(program, fShader);
    gl.linkProgram(program);

    return program;
}

function resizeCanvas(canvas) {
    let displayWidth = canvas.clientWidth;
    let displayHeight = canvas.clientHeight;
  
    if (canvas.width !== displayWidth || canvas.height !== displayHeight) {
      canvas.width = displayWidth;
      canvas.height = displayHeight;
    }
}

function main() {
    program = cProgram(gl, vertCode, fragCode);
    pos = gl.getAttribLocation(program, 'coordinates');
    uni = gl.getUniformLocation(program, 'res');
    col = gl.getUniformLocation(program, 'color');

    buffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, buffer);

    var render = true;

    var pressed = {};
    window.addEventListener("keydown", e => pressed[e.key] = true);
    window.addEventListener("keyup", e => pressed[e.key] = false);

    //window.addEventListener("mouseup", e => begin());

    function trender() {
        if(ball.x < 0) {
            ball.x = (aroundX-height)/2 - 60,
            ball.y =  (aroundY-width)/2
            ball.dX = -ball.dX
        }
        if(ball.x + ball.width > canvas.width) {
            ball.x = (aroundX-height)/2 - 60,
            ball.y =  (aroundY-width)/2
            ball.dX = -ball.dX
        }
        if(ball.y + ball.height > canvas.height || ball.y < 0) {
            ball.dY = -ball.dY
        }
        if((ball.x < width && (ball.y < translation[0] + height && ball.y > translation[0])) || 
            (ball.x + ball.width > canvas.width - width && (ball.y < translation[1] + height && ball.y > translation[1]))) {
            ball.dX = -ball.dX
        }
        ball.x += ball.dX;
        ball.y += ball.dY;
        if (pressed["k"] && translation[1] - 3>= 0) {
            translation[1] -= 3;
        }
        if (pressed["i"] && translation[1] + 3 + height <= canvas.height) {
            translation[1] += 3;
        }
        if (pressed["s"] && translation[0] - 3 >= 0) {
            translation[0] -= 3;
        }
        if (pressed["w"] && translation[0] + 3 + height <= canvas.height) {
            translation[0] += 3;
        }
        drawScene();
        requestAnimationFrame(trender);
    }
    trender();
}


function drawScene() {
    resizeCanvas(gl.canvas);
    gl.viewport(0, 0, gl.canvas.width, gl.canvas.height);
    gl.clearColor(0.0, 0.4, 0.0, 0.9);
    gl.clear(gl.COLOR_BUFFER_BIT);
    gl.useProgram(program);
    gl.enableVertexAttribArray(pos);
    gl.bindBuffer(gl.ARRAY_BUFFER, buffer);
    setRectangle(gl, translation[0], translation[1], width, height);
    gl.vertexAttribPointer(pos, 2, gl.FLOAT, false, 0, 0);
    gl.uniform2f(uni, gl.canvas.width, gl.canvas.height);
    gl.uniform4fv(col, color);
    gl.drawArrays(gl.TRIANGLES, 0, 60);
}

function setRectangle(gl, h1, h2, width, height) {
    gl.bufferData(
        gl.ARRAY_BUFFER,
        new Float32Array([
            3, h1,
            width, h1,
            3, h1 + height,
            width, h1,
            3, h1 + height,
            width, h1 + height,

            canvas.width - width, h2,
            canvas.width - 3, h2,
            canvas.width - width, h2 + height,
            canvas.width - 3, h2,
            canvas.width - width, h2 + height,
            canvas.width - 3, h2 + height,

            ball.x, ball.y,
            ball.x, ball.y + ball.height,
            ball.x + ball.width, ball.y,
            ball.x, ball.y + ball.height,
            ball.x + ball.width, ball.y,
            ball.x + ball.width, ball.y + ball.height,

            canvas.width/2 - 5, 0,
            canvas.width/2 + 5, 0,
            canvas.width/2 - 5, canvas.height,
            canvas.width/2 + 5, 0,
            canvas.width/2 - 5, canvas.height,
            canvas.width/2 + 5, canvas.height,

            canvas.width/6, canvas.height/5,
            canvas.width/6 + 10, canvas.height/5,
            canvas.width/6, canvas.height*4/5,
            canvas.width/6 + 10, canvas.height/5,
            canvas.width/6, canvas.height*4/5,
            canvas.width/6 + 10, canvas.height*4/5,

            canvas.width*5/6, canvas.height/5,
            canvas.width*5/6 - 10, canvas.height/5,
            canvas.width*5/6, canvas.height*4/5,
            canvas.width*5/6 - 10, canvas.height/5,
            canvas.width*5/6, canvas.height*4/5,
            canvas.width*5/6 - 10, canvas.height*4/5,

            0, canvas.height/5,
            canvas.width/6 + 10, canvas.height/5,
            0, canvas.height/5 + 10,
            canvas.width/6 + 10, canvas.height/5,
            0, canvas.height/5 + 10,
            canvas.width/6 + 10, canvas.height/5 + 10,

            0, canvas.height*4/5,
            canvas.width/6 + 10, canvas.height*4/5,
            0, canvas.height*4/5 + 10,
            canvas.width/6 + 10, canvas.height*4/5,
            0, canvas.height*4/5 + 10,
            canvas.width/6 + 10, canvas.height*4/5 + 10,

            canvas.width*5/6 - 10, canvas.height*4/5,
            canvas.width, canvas.height*4/5,
            canvas.width*5/6 - 10, canvas.height*4/5 + 10,
            canvas.width, canvas.height*4/5,
            canvas.width*5/6 - 10, canvas.height*4/5 + 10,
            canvas.width, canvas.height*4/5 + 10,

            canvas.width*5/6, canvas.height/5,
            canvas.width, canvas.height/5,
            canvas.width*5/6, canvas.height/5 + 10,
            canvas.width, canvas.height/5,
            canvas.width*5/6, canvas.height/5 + 10,
            canvas.width, canvas.height/5 + 10,
        ]),
        gl.STATIC_DRAW
    );
}
  
main();