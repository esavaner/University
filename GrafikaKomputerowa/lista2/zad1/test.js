var canvas = document.getElementById('canv');
var gl = canvas.getContext('webgl');
var program;

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

var Init = function() {
    program = cProgram(gl, vertCode, fragCode)
    var pos = gl.getAttribLocation(program, 'coordinates');
    var uni = gl.getUniformLocation(program, 'res');
    var col = gl.getUniformLocation(program, 'color');

    var vertices = [
        100, 100,
        800, 200,
        30, 300,
        700, 400,
        50, 500,
        500, 600,
    ]

    var vertBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, vertBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertices), gl.STATIC_DRAW);

    resizeCanvas(gl.canvas);
    gl.viewport(0, 0, gl.canvas.width, gl.canvas.height);

    gl.clearColor(0.2, 0.2, 0.2, 0.5);
    gl.clear(gl.COLOR_BUFFER_BIT);

    gl.useProgram(program);
    gl.enableVertexAttribArray(pos);
    gl.bindBuffer(gl.ARRAY_BUFFER, vertBuffer);

    gl.vertexAttribPointer(pos, 2, gl.FLOAT, false, 0, 0);
    gl.uniform2f(uni, gl.canvas.width, gl.canvas.height);
    gl.uniform4f(col, Math.random(), Math.random(), Math.random(), 1);

    gl.drawArrays(gl.TRIANGLES, 0, 6);
    printAll();
}

function draw(type) {
    gl.clearColor(0.2, 0.2, 0.2, 0.5);
    gl.clear(gl.COLOR_BUFFER_BIT);
    var col = gl.getUniformLocation(program, 'color');
    gl.uniform4f(col, Math.random(), Math.random(), Math.random(), 1);
    gl.drawArrays(gl[type], 0, 6);
    printAll();
}

function printAll() {
    const att = gl.getProgramParameter(program, gl.ACTIVE_ATTRIBUTES);
    for (let i = 0; i < att; ++i) {
        const info = gl.getActiveAttrib(program, i);
        console.log('Attributes, name:', info.name, 'type:', info.type, 'size:', info.size);
    }

    const uni = gl.getProgramParameter(program, gl.ACTIVE_UNIFORMS);
    for (let i = 0; i < uni; ++i) {
        const info = gl.getActiveUniform(program, i);
        console.log('Uniforms, name:', info.name, 'type:', info.type, 'size:', info.size);
    }
}