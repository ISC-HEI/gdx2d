// Draws a circle that can be moved with the mouse. The radius is also
// controllable.
// Pierre-André Mudry, 2014

uniform vec2 resolution;
uniform vec2 mouse;
uniform float radius;

void main() {
	vec2 position = gl_FragCoord.xy / resolution.xy;
	float dist = distance(position, mouse.xy / resolution.xy);

	if(dist > radius) 
		discard;	

	gl_FragColor = vec4(0, 1, 1, 1.0);  	
}
