var canvas = document.getElementById('canv');
var gl = canvas.getContext('webgl');
var program;
var col;
var depth;
var pos;
var mat;

if(!gl) {
    gl = canvas.getContext('experimental-webgl');
}

var vertCode = [
    'attribute vec2 coordinates;',
    'uniform mat3 matrix;',
    'uniform float d;',
    'void main(void) {',
        'gl_Position = vec4((matrix*vec3(coordinates, 1)).xy, (d-1.0)/14.0, 1);',
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

    if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
        alert(
          "An error occurred compiling the shaders: " + gl.getShaderInfoLog(shader)
        );
        gl.deleteShader(shader);
        return null;
      }

    return shader;
}

function cProgram(gl, vCode, fCode) {
    var vShader = cShader(gl, gl.VERTEX_SHADER, vCode);
    var fShader = cShader(gl, gl.FRAGMENT_SHADER, fCode);

    var program = gl.createProgram();
    gl.attachShader(program, vShader);
    gl.attachShader(program, fShader);
    gl.linkProgram(program);

    if (!gl.getProgramParameter(program, gl.LINK_STATUS)) {
        alert(
          "Unable to initialize the shader program: " +
            gl.getProgramInfoLog(program)
        );
        return null;
      }

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

function cBuffer(gl, points) {
    const vertBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, vertBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(points), gl.STATIC_DRAW);
    return vertBuffer;
}

var toDraw = [];
var current;
var render = true;

function start() {
    var lvl = parseInt(document.getElementById("lvl").value);
    var pressed = {};

    window.addEventListener("keydown", e => pressed[e.key] = true);
    window.addEventListener("keyup", e => pressed[e.key] = false);
    resizeCanvas(gl.canvas);
    document.getElementById("btn").addEventListener("click", e => {
        lvl = parseInt(document.getElementById("lvl").value);
        var points = generatePoints(lvl, gl.canvas.width, gl.canvas.height);
        var o = {
            buffer: cBuffer(gl, points),
            lvl: Math.pow(2, lvl),
            uni: {
                color: [Math.random(), Math.random(), Math.random(), 1],
                depth: -0.5
            },
            translation: [0, 0]
        };
        toDraw.push(o);
        current = o;
        var length = toDraw.length-1;
        addMenu(toDraw[length], length, lvl);
        render = true;
    })

    program = cProgram(gl, vertCode, fragCode);
    pos = gl.getAttribLocation(program, 'coordinates');
    col = gl.getUniformLocation(program, 'color');
    mat = gl.getUniformLocation(program, 'matrix');
    depth = gl.getUniformLocation(program, 'd');

    var points = generatePoints(lvl, gl.canvas.width, gl.canvas.height);
    var o = {
        buffer: cBuffer(gl, points),
        lvl: Math.pow(2, lvl),
        uni: {
            color: [Math.random(), Math.random(), Math.random(), 1],
            depth: -1.0
        },
        translation: [0, 0]
    };
    toDraw.push(o);
    current = toDraw[0];
    addMenu(toDraw[0], 0, lvl);

    function trender() {
        if (pressed["d"]) {
            current.translation[0] += 1;
            render = true;
        }
        if (pressed["a"]) {
            current.translation[0] -= 1;
            render = true;
        }
        if (pressed["s"]) {
            current.translation[1] += 1;
            render = true;
        }
        if (pressed["w"]) {
            current.translation[1] -= 1;
            render = true;
        }
        if (render) {
            render = false;
            drawScene(toDraw);
        }
        requestAnimationFrame(trender);
    }

    trender();
}

function addMenu(object, id, lvl) {
    const container = document.querySelector('nav');
    container.id = `menu${id}`;

    const menu = document.createElement('div');
    menu.className = 'menu';

    const info = document.createElement('span');
    info.textContent = `${id}, level: ${lvl}`;

    const rgbInput = document.createElement('input');
    rgbInput.className = 'c';
    rgbInput.value = object.uni.color.join(', ');

    menu.addEventListener('click', () => {
        current = toDraw[id];
        document.getElementById('current').innerText = `Selected: ${id}`;
    });

    rgbInput.addEventListener('input', () => {
        let rgb = rgbInput.value.split(',').map(n => Number(n));
        if (rgb.length === 4 && rgb.every(num => !isNaN(num) && isFinite(num) && num >= 0 && num <= 1)) {
            toDraw[id].uni.color = rgb;
            render = true;
        }
    });

    menu.append(info, rgbInput);
    container.append(menu);
}

function drawScene(toDraw) {
    resizeCanvas(gl.canvas);
    gl.viewport(0, 0, gl.canvas.width, gl.canvas.height);

    gl.clearColor(0.0, 0.0, 0.0, 0.0);
    gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

    gl.enable(gl.DEPTH_TEST);
    gl.depthFunc(gl.LEQUAL);
    gl.useProgram(program);

    toDraw.forEach(o => {
        gl.enableVertexAttribArray(pos);
        gl.bindBuffer(gl.ARRAY_BUFFER, o.buffer);

        let size = 2;
        let type = gl.FLOAT;
        let normalize = false;
        let stride = 0; 
        let offset = 0; 
        gl.vertexAttribPointer(pos, size, type, normalize, stride, offset);

        let matrix = m3.projection(gl.canvas.clientWidth, gl.canvas.clientHeight);
        matrix = m3.translate(matrix, o.translation[0], o.translation[1]);

        gl.uniformMatrix3fv(mat, false, matrix);

        gl.uniform4fv(col, o.uni.color);

        gl.uniform1f(depth, o.uni.depth);

        gl.drawArrays(gl.LINE_STRIP, 0, o.lvl * o.lvl);
    });
}

window.onload = start;

function translateCoordinates(degree, x, y, width, height) {
    return [
        (x / degree) * width,
        (y / degree) * height
    ];
}

function last2bits(x) {
    return (x & 3);
}

function hindex2xy(hindex, N) {
    let positions = [[0, 0], [0, 1], [1, 1], [1, 0]];

    let tmp = positions[last2bits(hindex)];
    hindex = (hindex >>> 2);

    let x = tmp[0];
    let y = tmp[1];

    for (let n = 4; n <= N; n *= 2) {
        let n2 = n / 2;

        switch (last2bits(hindex)) {
            case 0: {
                tmp = x;
                x = y;
                y = tmp;
                break;
            }
            case 1: {
                y = y + n2;
                break;
            }
            case 2: {
                x = x + n2;
                y = y + n2;
                break;
            }
            case 3: {
                tmp = y;
                y = (n2 - 1) - x;
                x = (n2 - 1) - tmp;
                x = x + n2;
                break;
            }
        }
        hindex = (hindex >>> 2);
    }
    return [x, y];
}

function generatePoints(inputDegree, width, height) {
    const degree = Math.pow(2, inputDegree);
    let points = [];

    for (let i = 0; i < degree * degree; i++) {
        const point = hindex2xy(i, degree);
        const coords = translateCoordinates(degree, point[0], point[1], width, height);

        points.push(coords[0], coords[1]);
    }

    return points;
}

const m3 = {
    projection: function(width, height) {
        return [
            2 / width, 0, 0,
            0, -2 / height, 0,
            -1, 1, 1
        ];
    },

    identity: function() {
        return [
            1, 0, 0,
            0, 1, 0,
            0, 0, 1,
        ];
    },

    translation: function(tx, ty) {
        return [
            1, 0, 0,
            0, 1, 0,
            tx, ty, 1,
        ];
    },

    rotation: function(angleInRadians) {
        var c = Math.cos(angleInRadians);
        var s = Math.sin(angleInRadians);
        return [
            c,-s, 0,
            s, c, 0,
            0, 0, 1,
        ];
    },

    scaling: function(sx, sy) {
        return [
            sx, 0, 0,
            0, sy, 0,
            0, 0, 1,
        ];
    },

    multiply: function(a, b) {
        var a00 = a[0 * 3 + 0];
        var a01 = a[0 * 3 + 1];
        var a02 = a[0 * 3 + 2];
        var a10 = a[1 * 3 + 0];
        var a11 = a[1 * 3 + 1];
        var a12 = a[1 * 3 + 2];
        var a20 = a[2 * 3 + 0];
        var a21 = a[2 * 3 + 1];
        var a22 = a[2 * 3 + 2];
        var b00 = b[0 * 3 + 0];
        var b01 = b[0 * 3 + 1];
        var b02 = b[0 * 3 + 2];
        var b10 = b[1 * 3 + 0];
        var b11 = b[1 * 3 + 1];
        var b12 = b[1 * 3 + 2];
        var b20 = b[2 * 3 + 0];
        var b21 = b[2 * 3 + 1];
        var b22 = b[2 * 3 + 2];
        return [
            b00 * a00 + b01 * a10 + b02 * a20,
            b00 * a01 + b01 * a11 + b02 * a21,
            b00 * a02 + b01 * a12 + b02 * a22,
            b10 * a00 + b11 * a10 + b12 * a20,
            b10 * a01 + b11 * a11 + b12 * a21,
            b10 * a02 + b11 * a12 + b12 * a22,
            b20 * a00 + b21 * a10 + b22 * a20,
            b20 * a01 + b21 * a11 + b22 * a21,
            b20 * a02 + b21 * a12 + b22 * a22,
        ];
    },

    translate: function(m, tx, ty) {
        return m3.multiply(m, m3.translation(tx, ty));
    },

    rotate: function(m, angleInRadians) {
        return m3.multiply(m, m3.rotation(angleInRadians));
    },

    scale: function(m, sx, sy) {
        return m3.multiply(m, m3.scaling(sx, sy));
    },
};
