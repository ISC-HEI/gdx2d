// A simple gradient that spans the entire resolution
uniform vec2 resolution;
varying vec2 vSurfacePosition;

void main() {
	vec2 position = (vSurfacePosition.xy + (gl_FragCoord.xy / resolution.xy));  
	gl_FragColor = vec4(0, position.x, position.y, 1.0);
}
