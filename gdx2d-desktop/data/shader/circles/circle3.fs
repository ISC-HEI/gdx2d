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

	// Change the radius with time
	if(dist > (abs(sin(time) * radius) + 0.02)) 
		discard;	

	// Interpolate nicely color
	gl_FragColor = vec4(smoothstep(0.0,1.0,position.y), smoothstep(0.0,1.0,position.x), 1.0, 1.0);  	
}
