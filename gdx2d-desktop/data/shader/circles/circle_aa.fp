// An anti-aliased circled shader, made with the circle equation
// Pierre-André Mudry, 2014

#version 130
#pragma debug(on)
#pragma optimize(on)

#ifdef GL_ES
	precision mediump float;
#endif

uniform vec2 resolution;
uniform vec2 position;
uniform vec3 color;
uniform float radius;

out vec4 o_fragColor; // Each fragment color
const float antialias_distance = 2.0; // In pixels, the distance from the border of the circle to fade to transparent

void main() {	
	float dist = distance(gl_FragCoord.xy, position.xy); // Distance from center

	// Interpolate nicely color for having an anti-aliased circle
	// The smoothstep function which is applied uses Hermite interpolation
	// see for instance http://www.geeks3d.com/20130705/shader-library-circle-disc-fake-sphere-in-glsl-opengl-glslhacker/2/	
	o_fragColor = mix(vec4(color, 1.0),
					   vec4(0, 0, 0, 0), 
					   smoothstep(radius, radius + antialias_distance, dist));
}
