function toPoint([x, y, z]) {
    return new Vec(x, y, z);
}

function toPolygon(shape) {
    return shape.map(toPoint);
}

function createMesh(model) {
    return new Mesh(model.map(toPolygon));
}

function offset(point, position) {
    point.x += position.x;
    point.y += position.y;
    point.z += position.z;
}

class Mesh {
    constructor(polygons) {
        this.polygons = polygons;
        this.position = new Vec();
        this.rotation = new Vec();
    }

    *[Symbol.iterator] () {
        for (const polygon of this.polygons) {
            yield polygon.map(point => ({...point}));
        }
    }

    transform(point, camera) {
        rotate(point, this.rotation, camera);
        offset(point, this.position);
    }
}

function rotate(point, rotation, camera) {
    const tan = new Vec(
        Math.tan(rotation.x),
        Math.tan(rotation.y),
        Math.tan(rotation.z)
    );

    let temp1, temp2;
    let d = Math.sqrt(Math.pow(point.x - camera.pos.x,2) + Math.pow(point.y - camera.pos.y,2) + Math.pow(point.z - camera.pos.z,2));

    point.x += d * tan.x;
    point.y += d * tan.y;
    point.z += d * tan.z;
    /*
    temp1 = cos.x * point.y + sin.x * point.z;
    temp2 = -sin.x * point.y + cos.x * point.z;
    point.y = temp1;
    point.z = temp2;

    temp1 = cos.y * point.x + sin.y * point.z;
    temp2 = -sin.y * point.x + cos.y * point.z;
    point.x = temp1;
    point.z = temp2;

    temp1 = cos.z * point.x + sin.z * point.y;
    temp2 = -sin.z * point.x + cos.z * point.y;
    point.x = temp1;
    point.y = temp2;
    */
}