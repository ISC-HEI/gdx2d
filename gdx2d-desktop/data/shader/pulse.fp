// What we get from Java, *once*
uniform vec2 resolution;
uniform float time;

/**
 * Produced by the fragment shader
 */
out vec4 o_fragColor; // Each fragment color

void main( void ) {
	vec2 p = (gl_FragCoord.xy / resolution.xy ) - 0.5;
	float s = sin(time*2.0);
	float dy = 1. / (55. * abs(sin(p.y)*s) );
	gl_FragColor = vec4( dy * 0.1 * dy, 0.5 * dy, dy, 1.0 );
}

