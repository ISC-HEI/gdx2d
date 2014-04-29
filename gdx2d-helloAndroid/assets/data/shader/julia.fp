uniform sampler2D texture0;
uniform vec2 center;
uniform vec2 offset;
uniform float scale;

const int iter = 1000;
varying vec2 vTexCoord;

void main() {
    vec2 z;
    z.x = 2.0 * (vTexCoord.x - 0.5) * scale + offset.x;
    z.y = 2.0 * (vTexCoord.y - 0.5) * scale + offset.y;

    int i;
    for(i=0; i<iter; i++) {
        float x = (z.x * z.x - z.y * z.y) + center.x;
        float y = (z.y * z.x + z.x * z.y) + center.y;

        if((x * x + y * y) > 4.0) break;
        z.x = x;
        z.y = y;
    }

    gl_FragColor = texture2D(texture0, vec2((i == iter ? 0.0 : float(i)) / 100.0), 0);
}
	