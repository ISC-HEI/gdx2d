// A very nice plasma shader, taken and adapted from http://www.bidouille.org/prog/plasma
// Nice job !

// precision mediump float; // Fix issue #18
#define PI 3.1415926535897932384626433832795

// What we get from Java, *once*
uniform float time;

/**
 * Received from vertex shader
 */
in vec2 vTexCoord;

// Scaling factor
const vec2 u_k = vec2(12, 15.0);

void main() {
    float v = 0.0;
    vec2 c = vTexCoord* u_k - u_k/2.0;
    v += sin((c.x+time));
    v += sin((c.y+time)/2.0);
    v += sin((c.x+c.y+time)/2.0);
    c += u_k/2.0 * vec2(sin(time/3.0), cos(time/2.0));
    v += sin(sqrt(c.x*c.x+c.y*c.y+1.0)+time);
    v = v/2.0;
    
    vec3 col = vec3(1, sin(PI*v), cos(PI*v));
    gl_FragColor = vec4(col*.5 + .5, 1);
}