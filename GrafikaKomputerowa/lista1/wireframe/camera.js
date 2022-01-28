function perspective(point, distance) {
    const fov = point.z + distance;
    if(fov > 0) {
        point.x /= fov;
        point.y /= fov;
    }
}

function zoom(point, factor) {
    const scale = Math.pow(factor, 2);
    point.x *= scale;
    point.y *= scale;
}

class Camera { 
    constructor() {
        this.pos = new Vec(0, 0, 300);
        this.zoom = 20;

    }
    project(point) {
        perspective(point, this.pos.z);
        zoom(point, this.zoom);
    }
}

class Vec {
    constructor(x = 0, y = 0, z = 0) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
