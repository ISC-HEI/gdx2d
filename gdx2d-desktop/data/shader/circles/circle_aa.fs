// An anti-aliased circled shader, made with the circle equation
// Pierre-André Mudry, 2014

uniform vec2 resolution;
uniform vec2 mouse;
uniform float radius;
uniform float time;

void main() {
	vec2 position = gl_FragCoord.xy / resolution.xy;
	float dist = distance(position, mouse.xy / resolution.xy); // Distance from center
	float r = abs(sin(time) * radius) + 0.04; // Change the radius with time

	// Interpolate nicely color for having an anti-aliased circled
	gl_FragColor = mix(vec4(.70, .50, .99, 1.0), 
					   vec4(0, 0, 0, 1), 
					   smoothstep(r, r + 0.01, dist));  	
}
