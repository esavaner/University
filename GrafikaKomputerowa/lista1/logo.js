let c = document.getElementById("canv");
let ctx = c.getContext("2d");
let angle = 90;
let x = 500;
let y = 600;
let z = 0;
let draw = true;

function rotRight(value) {
    angle = (angle - value) % 360;
}

function rotLeft(value) {
    angle = (angle + value) % 360;
}

function forward(z) {
    var newx = x + z * Math.cos(angle * Math.PI / 180);
    var newy = y - z * Math.sin(angle * Math.PI / 180);
    if(draw) {
        ctx.moveTo(x, y);
        ctx.lineTo(newx, newy);
        ctx.stroke();
    } else
        ctx.moveTo(newx, newy);
    
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
            draw = false;
            break;
        case "down":
            draw = true;
            break;
        case "hilbert":
            hilbert(value);
            break;
    }
}

function start() {
    document.getElementById('txta').value.split("\n").forEach(execute);
}

function cleartxt() {
    ctx.clearRect(0, 0, 1000, 600);
    angle = 90;
    x = 500;
    y = 600;
    z = 0;
    draw = true;
    ctx.moveTo(x, y);
    document.getElementById('txta').value = "";
}

    
function hilbert(order) {
    cleartxt();

    function incX(angle) {
      return Math.round(Math.cos(angle)*min/(Math.pow(order+1, 2.2)));
    }
    function incY(angle) {
      return Math.round(Math.sin(angle)*min/(Math.pow(order+1, 2.2)))*-1;
    }
    
    c.width = 1000;
    c.height = 600;
    var min = Math.min(c.width, c.height);
    
    
    var x,y;
    var angle = 0;
    
    var result = "A";
    
    var rules = {
      A: "-BF+AFA+FB-", 
      B: "+AF-BFB-FA+"  
    };
    
    for(var i = 0; i < order; ++i) {
      result = replaceAll(result, rules);
    }
  
    function replaceAll(str, mapObj){
      var re = new RegExp(Object.keys(mapObj).join("|"), "gi");
  
      return str.replace(re, function(matched){
          return mapObj[matched];
      });
    }
    
    var numberOfF = (result.match(/F/g) || []).length;
    var currentFIndex = 0;
    
    x = Math.round(c.width / (order + 1));
    y = Math.round(c.height - c.height / (order + 1));
    
    
    var index = 0;
    function draw() {
      if(index === result.length) {
        return;
      }
      if(result[index] === "F") {
        //var color = currentFIndex / numberOfF * 360;
        //ctx.strokeStyle = "hsl(" + color + ", 100%, 50%)";
        ctx.beginPath();
        ctx.moveTo(x, y);
        x += incX(angle);
        y += incY(angle);
        ctx.lineTo(x, y);
        ctx.stroke();
        ++currentFIndex;
      } else if(result[index] === "+") {
        angle -= Math.PI/2;
      } else if(result[index] === "-") {
        angle += Math.PI/2;
      }    
      ++index;
      if(index % 4 === 0) {
        requestAnimationFrame(draw);
      } else {
        draw();
      }
    }
    draw();
  }