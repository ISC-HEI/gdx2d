// An anti-aliased circled shader, made with the circle equation
// Pierre-André Mudry, 2014

uniform vec2 resolution;
uniform vec2 mouse;
uniform float radius;
uniform float time;
const int antialias_distance = 2; // In pixels, the distance from the border of the circle to fade to transparent

void main() {	
	float dist = distance(gl_FragCoord.xy, mouse.xy); // Distance from center
	float r = abs(sin(time) * radius) + 20; // Change the radius with time

	// Interpolate nicely color for having an anti-aliased circle
	// The smoothstep function which is applied uses Hermite interpolation
	// see for instance http://www.geeks3d.com/20130705/shader-library-circle-disc-fake-sphere-in-glsl-opengl-glslhacker/2/	
	gl_FragColor = mix(vec4(.70, .50, .99, 1.0), 
					   vec4(0, 0, 0, 1), 
					   smoothstep(r, r + antialias_distance, dist));
					   
	
}
