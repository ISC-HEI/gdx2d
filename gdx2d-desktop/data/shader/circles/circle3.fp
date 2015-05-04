// Draws a mouse controlled circle, which radius changes over time
// Its color is dependent on its position
// Pierre-André Mudry, 2014

#version 300 es
#pragma debug(on)
#pragma optimize(on)

precision mediump float;

// Information given from the Java side.
uniform vec2 resolution;
uniform vec2 mouse;
uniform float radius;
uniform float time;

out vec4 o_fragColor; // Each fragment color

const float default_radius = 30.0;

void main() {	
	float dist = distance(gl_FragCoord.xy, mouse.xy);
	vec2 position = gl_FragCoord.xy / resolution.xy;

	float r = radius <= 0.1 ? default_radius : radius;

	// Change the radius with time
	if(dist > (abs(sin(4.0*time) * r) + 30.0)) 
		discard;

	// Nicely interpolate the color of the circle
	o_fragColor = vec4(smoothstep(0.0,1.0,position.y), smoothstep(0.0,1.0,position.x), 1.0, 1.0);
}
