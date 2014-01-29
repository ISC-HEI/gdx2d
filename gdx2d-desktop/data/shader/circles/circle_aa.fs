// Draws a mouse controlled circle, which radius changes over time
// Its color is dependent on its position
// Pierre-André Mudry, 2014

uniform vec2 resolution;
uniform vec2 mouse;
uniform float radius;
uniform float time;

void main() {
	vec2 position = gl_FragCoord.xy / resolution.xy;
	float dist = distance(position, mouse.xy / resolution.xy);	
	float r = abs(sin(time) * radius) + 0.04;

	// Interpolate nicely color for having an anti-aliased circled
	gl_FragColor = mix(vec4(.70, .50, .99, 1.0), 
					   vec4(0, 0, 0, 1), 
					   smoothstep(r, r + 0.01, dist));  	
}
